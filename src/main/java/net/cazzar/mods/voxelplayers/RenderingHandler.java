package net.cazzar.mods.voxelplayers;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.ForgeSubscribe;

import java.util.Map;

public class RenderingHandler {
    Map<String, ModelPlayer> models = Maps.newHashMap();
    @ForgeSubscribe
    public void render(RenderPlayerEvent.Pre e) {
        ModelPlayer model;
        if (!models.containsKey(e.entityPlayer.username)) {
            model = new ModelPlayer();
            model.setUpCustom(e.entityPlayer.username);
            models.put(e.entityPlayer.username, model);
        }
        else {
            model = models.get(e.entityPlayer.username);
        }

        ObfuscationReflectionHelper.setPrivateValue(RendererLivingEntity.class, e.renderer, model, "mainModel");
        ObfuscationReflectionHelper.setPrivateValue(RenderPlayer.class, e.renderer, model, "modelBipedMain");
    }
}
