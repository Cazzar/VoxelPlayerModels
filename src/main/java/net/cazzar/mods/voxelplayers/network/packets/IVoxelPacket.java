package net.cazzar.mods.voxelplayers.network.packets;

import io.netty.buffer.ByteBuf;

public interface IVoxelPacket {
    public void readBytes(ByteBuf bytes);

    public void writeBytes(ByteBuf bytes);
}
