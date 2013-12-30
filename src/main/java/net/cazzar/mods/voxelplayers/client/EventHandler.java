package net.cazzar.mods.voxelplayers.client;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraftforge.client.event.RenderPlayerEvent;

import java.util.Map;

public class EventHandler {
    Map<String, ModelPlayer> models = Maps.newHashMap();

    @SubscribeEvent
    public void render(RenderPlayerEvent.Pre e) {
        ModelPlayer model;
        if (!models.containsKey(e.entityPlayer.func_146103_bH().getName())) {
            model = new ModelPlayer();
            model.setUpCustom(e.entityPlayer.func_146103_bH().getName());
            models.put(e.entityPlayer.func_146103_bH().getName(), model);
        } else {
            model = models.get(e.entityPlayer.func_146103_bH().getName());
        }

        ObfuscationReflectionHelper.setPrivateValue(RendererLivingEntity.class, e.renderer, model, "mainModel");
        ObfuscationReflectionHelper.setPrivateValue(RenderPlayer.class, e.renderer, model, "modelBipedMain");
    }
}
