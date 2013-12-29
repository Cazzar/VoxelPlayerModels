package net.cazzar.mods.voxelplayers.proxy;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.cazzar.mods.voxelplayers.network.PacketHandler;
import voxelplayermodels.Body;

import java.util.Map;

public class CommonProxy {
    public Map<String, Body> playerBodies = Maps.newHashMap();

    public void initNetworking() {
        NetworkRegistry.instance().registerChannel(new PacketHandler(), "VoxelModels");
    }

    public void initOther() {
    }

    public void init() {
    }
}
