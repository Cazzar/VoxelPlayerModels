package net.cazzar.mods.voxelplayers.proxy;

import net.cazzar.mods.voxelplayers.VoxelPlayers;
import net.cazzar.mods.voxelplayers.client.EventHandler;
import net.cazzar.mods.voxelplayers.client.Serializer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import voxelplayermodels.Body;

@SuppressWarnings("UnusedDeclaration")
public class ClientProxy extends CommonProxy {
    @Override
    public void init() {
        super.init();
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    @Override
    public Body getBody(String name) {
        if (name.equals(Minecraft.getMinecraft().thePlayer.func_146103_bH().getName())) {
            Body myBody = VoxelPlayers.proxy.playerBodies.get(name);

            if (myBody == null && name.equals(Minecraft.getMinecraft().thePlayer.func_146103_bH().getName())) {
                myBody = Serializer.deserializeBody(System.getenv("APPDATA") + "\\.minecraft\\models\\" + name + ".body");
                playerBodies.put(name, myBody);
            }
        }

        return super.getBody(name);
    }
}
