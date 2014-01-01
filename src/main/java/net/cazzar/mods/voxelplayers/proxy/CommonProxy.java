package net.cazzar.mods.voxelplayers.proxy;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import io.netty.channel.embedded.EmbeddedChannel;
import net.cazzar.mods.voxelplayers.common.EventHandler;
import net.cazzar.mods.voxelplayers.network.ChannelHandler;
import net.minecraftforge.common.MinecraftForge;
import voxelplayermodels.Body;

import java.util.EnumMap;
import java.util.Map;

public class CommonProxy {
    public Map<String, Body> playerBodies = Maps.newHashMap();
    public EnumMap<Side, FMLEmbeddedChannel> channel;


    public void initNetworking() {
//        NetworkRegistry.INSTANCE..registerChannel(new PacketHandler(), "VoxelModels");
        channel = NetworkRegistry.INSTANCE.newChannel("VoxelModels", new ChannelHandler());
    }

    public void initOther() {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        FMLCommonHandler.instance().bus().register(new EventHandler());
    }

    public void init() {
    }

    public Body getBody(String name) {
        return playerBodies.get(name);
    }

    public EmbeddedChannel getServerChannel() {
        return channel.get(Side.SERVER);
    }

    public EmbeddedChannel getClientChannel() {
        return channel.get(Side.CLIENT);
    }
}
