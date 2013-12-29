package net.cazzar.mods.voxelplayers.proxy;

import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;
import net.cazzar.mods.voxelplayers.ModelPlayer;
import net.cazzar.mods.voxelplayers.RenderingHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.MinecraftForge;
import voxelplayermodels.Body;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPOutputStream;

public class ClientProxy extends CommonProxy {
    @Override
    public void init() {
        MinecraftForge.EVENT_BUS.register(new RenderingHandler());
        GameRegistry.registerPlayerTracker(new IPlayerTracker() {
            @Override
            public void onPlayerLogin(EntityPlayer player) {
                if (player != Minecraft.getMinecraft().thePlayer) return;

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                DataOutputStream data = new DataOutputStream(bos);

                try {
                    data.writeUTF("PLAYERDATA");

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    ObjectOutputStream obj = new ObjectOutputStream(new GZIPOutputStream(bytes));
                    Body body;

                    if (playerBodies.containsKey(player.username)) {
                        ModelPlayer pl = new ModelPlayer();
                        pl.setUpCustom(player.username);
                    }

                    if (!playerBodies.containsKey(player.username))
                        return;

                    body = playerBodies.get(player.username);

                    obj.writeObject(body);

                    byte[] bodydata = bytes.toByteArray();
                    data.writeShort(bodydata.length);

                    for (byte b : bodydata) data.writeByte(b);

                    Packet250CustomPayload packet = new Packet250CustomPayload();
                    packet.channel = "VoxelModels";
                    packet.data = bos.toByteArray();
                    packet.length = packet.data.length;

                    PacketDispatcher.sendPacketToServer(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPlayerLogout(EntityPlayer player) {
            }

            @Override
            public void onPlayerChangedDimension(EntityPlayer player) {
            }

            @Override
            public void onPlayerRespawn(EntityPlayer player) {
            }
        });

    }
}
