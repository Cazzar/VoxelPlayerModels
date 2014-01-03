package net.cazzar.mods.voxelplayers.client;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import net.cazzar.mods.voxelplayers.network.packets.ClientDataPacket;
import net.cazzar.mods.voxelplayers.network.packets.PlayerDataPacket;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraftforge.client.event.RenderPlayerEvent;

import java.util.Map;

import static cpw.mods.fml.common.network.FMLOutboundHandler.FML_MESSAGETARGET;
import static cpw.mods.fml.common.network.FMLOutboundHandler.OutboundTarget.TOSERVER;
import static net.cazzar.mods.voxelplayers.VoxelPlayers.proxy;
import static net.minecraft.client.Minecraft.getMinecraft;

public class EventHandler {
    Map<String, ModelPlayer> models = Maps.newHashMap();

    @SubscribeEvent
    public void render(RenderPlayerEvent.Pre e) {
        ModelPlayer model;
        if (!models.containsKey(e.entityPlayer.func_146103_bH().getName())) {
            model = new ModelPlayer();
            model.setUpCustom(e.entityPlayer.func_146103_bH().getName());
            models.put(e.entityPlayer.func_146103_bH().getName(), model);
        } else {
            model = models.get(e.entityPlayer.func_146103_bH().getName());
        }

        ObfuscationReflectionHelper.setPrivateValue(RendererLivingEntity.class, e.renderer, model, "mainModel", "field_77045_g");
        ObfuscationReflectionHelper.setPrivateValue(RenderPlayer.class, e.renderer, model, "modelBipedMain", "field_77109_a");
    }

    @SubscribeEvent
    public void clientConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        proxy.getClientChannel().attr(FML_MESSAGETARGET).set(TOSERVER);
        String player = getMinecraft().thePlayer.func_146103_bH().getName();
        proxy.getClientChannel().writeOutbound(new PlayerDataPacket(player));
        proxy.getClientChannel().writeOutbound(new ClientDataPacket(player, ClientDataPacket.Messages.REQUEST));
    }
}
