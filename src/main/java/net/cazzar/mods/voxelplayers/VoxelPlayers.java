package net.cazzar.mods.voxelplayers;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.cazzar.mods.voxelplayers.proxy.CommonProxy;

@Mod(modid = "VoxelPlayerModels", name = "Custom Player Models")
//@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {"VoxelModels"}, packetHandler = PacketHandler.class)
public class VoxelPlayers {
    @Mod.Instance
    public static VoxelPlayers instance;

    @SidedProxy(clientSide = "net.cazzar.mods.voxelplayers.proxy.ClientProxy", serverSide = "net.cazzar.mods.voxelplayers.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        event.getModMetadata().version = getVersion();
        proxy.initNetworking();
        proxy.initOther();
    }

    public String getVersion() {
        String ver = getClass().getPackage().getImplementationVersion();
        return ver == null || ver.isEmpty() ? "UNKNOWN" : ver;
    }
}
