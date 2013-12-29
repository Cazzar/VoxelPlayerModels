package net.cazzar.mods.voxelplayers.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.cazzar.mods.voxelplayers.VoxelPlayers;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import voxelplayermodels.Body;

import java.io.*;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class PacketHandler implements IPacketHandler {
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload payload, Player player) {
        if (payload.channel.equals("VoxelModels")) {
            DataInputStream packet = new DataInputStream(new ByteArrayInputStream(payload.data));

            if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
                try {
                    String username = packet.readUTF();
                    short len = packet.readShort();
//                    inputStream.read()
                    byte[] data = new byte[len];
                    for (int i = 0; i < len; i++) {
                        data[i] = packet.readByte();
                    }

                    ObjectInputStream obj = new ObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(data)));

                    VoxelPlayers.instance.proxy.playerBodies.put(username, (Body) obj.readObject());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            else {
                //we are on the server and the player wants the data
                try {
                    String message = packet.readUTF();
                    if (message.equals("PLAYERDATA")) {
                        for (Map.Entry<String, Body> entry : VoxelPlayers.proxy.playerBodies.entrySet()) {
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            DataOutputStream data = new DataOutputStream(bos);

                            data.writeUTF(entry.getKey());

                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            ObjectOutputStream obj = new ObjectOutputStream(new GZIPOutputStream(bytes));
                            obj.writeObject(entry.getValue());

                            byte[] body = bytes.toByteArray();
                            data.writeShort(body.length);

                            for (byte b : body) data.writeByte(b);

                            Packet250CustomPayload reply = new Packet250CustomPayload();
                            reply.channel = "VoxelModels";
                            reply.data = bos.toByteArray();
                            reply.length = bos.size();

                            PacketDispatcher.sendPacketToPlayer(reply, player);
                        }

                        try {
                            String username = packet.readUTF();
                            short len = packet.readShort();

                            byte[] data = new byte[len];
                            for (int i = 0; i < len; i++) {
                                data[i] = packet.readByte();
                            }

                            ObjectInputStream obj = new ObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(data)));

                            VoxelPlayers.instance.proxy.playerBodies.put(username, (Body) obj.readObject());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
