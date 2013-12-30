package net.cazzar.mods.voxelplayers.network.packets;

import cpw.mods.fml.common.network.FMLOutboundHandler;
import io.netty.buffer.ByteBuf;
import net.cazzar.mods.voxelplayers.VoxelPlayers;

public class VoxelLoginPacket implements IVoxelPacket {
    String name;

    public VoxelLoginPacket(String name) {
        this.name = name;
    }

    @SuppressWarnings("UnusedDeclaration")
    public VoxelLoginPacket() {
        name = "";
    }

    @Override
    public void readBytes(ByteBuf bytes) {
        short len = bytes.readShort();
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) chars[i] = bytes.readChar();
        name = String.valueOf(chars);

        if (VoxelPlayers.proxy.playerBodies.containsKey(name)) {
            VoxelPlayers.proxy.getClientChannel().attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
            VoxelPlayers.proxy.getClientChannel().writeOutbound(new ClientDataPacket(name, ClientDataPacket.Messages.REQUEST));
        }
    }

    @Override
    public void writeBytes(ByteBuf bytes) {
        bytes.writeInt(name.length());
        for (char c : name.toCharArray()) bytes.writeChar(c);
    }
}
