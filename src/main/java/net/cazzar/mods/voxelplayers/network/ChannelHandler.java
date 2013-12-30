package net.cazzar.mods.voxelplayers.network;

import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.cazzar.mods.voxelplayers.network.packets.ClientDataPacket;
import net.cazzar.mods.voxelplayers.network.packets.IVoxelPacket;
import net.cazzar.mods.voxelplayers.network.packets.PlayerDataPacket;
import net.cazzar.mods.voxelplayers.network.packets.VoxelLoginPacket;

public class ChannelHandler extends FMLIndexedMessageToMessageCodec<IVoxelPacket> {
    public enum Packets {
        LOGIN,
        PLAYER_DATA,
        CLIENT_DATA
    }

    public ChannelHandler() {
        addDiscriminator(Packets.LOGIN.ordinal(), VoxelLoginPacket.class);
        addDiscriminator(Packets.PLAYER_DATA.ordinal(), PlayerDataPacket.class);
        addDiscriminator(Packets.CLIENT_DATA.ordinal(), ClientDataPacket.class);
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, IVoxelPacket packet, ByteBuf data) throws Exception {
        packet.writeBytes(data);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf data, IVoxelPacket packet) {
        packet.readBytes(data);
    }
}
