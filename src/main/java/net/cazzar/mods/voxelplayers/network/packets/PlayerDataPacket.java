package net.cazzar.mods.voxelplayers.network.packets;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import io.netty.buffer.ByteBuf;
import voxelplayermodels.Body;

import java.io.*;
import java.util.zip.GZIPInputStream;

import static net.cazzar.mods.voxelplayers.VoxelPlayers.proxy;

public class PlayerDataPacket implements IVoxelPacket {
    Body body;
    String name;

    @SuppressWarnings("UnusedDeclaration")
    public PlayerDataPacket() {
        //We need this.
    }

    public PlayerDataPacket(String name) {
        this.name = name;
        this.body = proxy.playerBodies.get(name);
    }

    public PlayerDataPacket(String name, Body body) {
        this.name = name;
        this.body = body;
    }

    @Override
    public void readBytes(ByteBuf bytes) {
        System.out.println(String.format("Recieved: %s", getClass().getCanonicalName()));
        short len = bytes.readShort();
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) chars[i] = bytes.readChar();
        name = String.valueOf(chars);

        len = bytes.readShort();
        byte[] compressedBody = new byte[len];

        for (short i = 0; i < len; i++) compressedBody[i] = bytes.readByte();

        try {
            ObjectInputStream obj = new ObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(compressedBody)));
            body = (Body) obj.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (proxy.playerBodies.containsKey(name))
            proxy.playerBodies.remove(name);

        proxy.playerBodies.put(name, body);

        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            proxy.getServerChannel().attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
            proxy.getServerChannel().writeOutbound(new PlayerDataPacket(name, body));
        }
    }

    @Override
    public void writeBytes(ByteBuf bytes) {
        ByteArrayOutputStream packetData = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(packetData);

        try {
            out.writeUTF(name);
            ByteArrayOutputStream obj = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(obj);
            objStream.writeObject(body);

            out.writeShort(obj.size());

            out.write(obj.toByteArray());

            bytes.writeBytes(packetData.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
