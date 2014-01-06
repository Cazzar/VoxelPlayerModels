package net.cazzar.mods.voxelplayers.network.packets;

import io.netty.buffer.ByteBuf;

import static cpw.mods.fml.common.network.FMLOutboundHandler.FML_MESSAGETARGET;
import static cpw.mods.fml.common.network.FMLOutboundHandler.OutboundTarget.TOSERVER;
import static net.cazzar.mods.voxelplayers.VoxelPlayers.proxy;
import static net.cazzar.mods.voxelplayers.network.packets.ClientDataPacket.Messages.REQUEST;

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
        int len = bytes.readInt();
        System.out.println(len);
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) chars[i] = bytes.readChar();
        name = String.valueOf(chars);

        proxy.getClientChannel().attr(FML_MESSAGETARGET).set(TOSERVER);
        if (proxy.getBody(name) != null) {
            proxy.getClientChannel().writeOutbound(new PlayerDataPacket(name));
        }
        proxy.getClientChannel().writeOutbound(new ClientDataPacket(name, REQUEST));
    }

    @Override
    public void writeBytes(ByteBuf bytes) {
        bytes.writeInt(name.length());
        for (char c : name.toCharArray()) bytes.writeChar(c);
    }
}
