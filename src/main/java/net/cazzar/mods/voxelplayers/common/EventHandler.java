package net.cazzar.mods.voxelplayers.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.cazzar.mods.voxelplayers.network.packets.VoxelLoginPacket;

import static cpw.mods.fml.common.network.FMLOutboundHandler.FML_MESSAGETARGET;
import static cpw.mods.fml.common.network.FMLOutboundHandler.FML_MESSAGETARGETARGS;
import static cpw.mods.fml.common.network.FMLOutboundHandler.OutboundTarget.ALL;
import static cpw.mods.fml.common.network.FMLOutboundHandler.OutboundTarget.PLAYER;
import static net.cazzar.mods.voxelplayers.VoxelPlayers.proxy;

public class EventHandler {
    @SubscribeEvent
    public void join(PlayerEvent.PlayerLoggedInEvent e) {
        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            proxy.getServerChannel().attr(FML_MESSAGETARGET).set(PLAYER);
            proxy.getServerChannel().attr(FML_MESSAGETARGETARGS).set(e.player);
            proxy.getServerChannel().writeOutbound(new VoxelLoginPacket(e.player.func_146103_bH().getName()));

            proxy.getServerChannel().attr(FML_MESSAGETARGET).set(ALL);
        }
    }
}
