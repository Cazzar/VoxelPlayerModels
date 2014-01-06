package net.cazzar.mods.voxelplayers;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.cazzar.mods.voxelplayers.proxy.CommonProxy;
import net.cazzar.mods.voxelplayers.updates.UpdateChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "VoxelPlayerModels", name = "Custom Player Models")
//@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {"VoxelModels"}, packetHandler = PacketHandler.class)
public class VoxelPlayers {
    @Mod.Instance
    public static VoxelPlayers instance;

    public Logger logger;

    @SidedProxy(clientSide = "net.cazzar.mods.voxelplayers.proxy.ClientProxy", serverSide = "net.cazzar.mods.voxelplayers.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();

        UpdateChecker checker = UpdateChecker.instance("VoxelPlayerModels");
        if (checker.getStatus() == UpdateChecker.Status.UPDATE_MAJOR) {
            logger.warn("New major update to Voxel Player Models V" + checker.getUpdateInfo().version + " for Minecraft " + checker.getUpdateInfo().minecraft);
            for (String s : checker.getUpdateInfo().changes)
                logger.warn(s);
        } else if (checker.getStatus() == UpdateChecker.Status.UPDATE_MINOR || checker.getStatus() == UpdateChecker.Status.UPDATE_REVISION) {
            logger.info("New update to Voxel Player Models V" + checker.getUpdateInfo().version + " for Minecraft: " + checker.getUpdateInfo().minecraft);
            for (String s : checker.getUpdateInfo().changes)
                logger.info(s);
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = LogManager.getLogger(getClass());
        event.getModMetadata().version = getVersion();
        proxy.initNetworking();
        proxy.initOther();
        UpdateChecker.instance("VoxelPlayerModels").run();
    }

    public String getVersion() {
        String ver = getClass().getPackage().getImplementationVersion();
        return ver == null || ver.isEmpty() ? "UNKNOWN" : ver;
    }
}
