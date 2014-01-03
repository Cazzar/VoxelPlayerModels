package net.cazzar.mods.voxelplayers.network.packets;

import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.server.MinecraftServer;

import static net.cazzar.mods.voxelplayers.VoxelPlayers.proxy;

public class ClientDataPacket implements IVoxelPacket {
    public static enum Messages {
        REQUEST
    }

    String sender;
    Messages message;

    @SuppressWarnings("UnusedDeclaration")
    public ClientDataPacket() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public ClientDataPacket(String sender, Messages message) {
        this.sender = sender;
        this.message = message;
    }

    @Override
    public void readBytes(ByteBuf bytes) {
//        System.out.println(String.format("Recieved %s", getClass().getCanonicalName()));
        message = Messages.values()[bytes.readInt()];
        short len = bytes.readShort();
        char[] str = new char[len];

        for (short i = 0; i < len; i++) str[i] = bytes.readChar();

        sender = new String(str);

        switch (message) {
            case REQUEST:
                for (String player : proxy.playerBodies.keySet()) {
                    proxy.channel.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
                    proxy.channel.get(Side.SERVER)
                            .attr(FMLOutboundHandler.FML_MESSAGETARGETARGS)
                            .set(MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(sender));

                    proxy.channel.get(Side.SERVER).writeOutbound(new PlayerDataPacket(player));
                }
                break;
        }
    }

    @Override
    public void writeBytes(ByteBuf bytes) {
        //Message
        bytes.writeInt(message.ordinal());

        //sender
        bytes.writeShort(sender.length());
        for (char c : sender.toCharArray()) bytes.writeChar(c);
    }
}
