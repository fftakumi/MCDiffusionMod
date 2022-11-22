package com.konnitiha.MCDiffusionMod.client;

import com.konnitiha.MCDiffusionMod.main.MCDiffusionEntities;
import com.konnitiha.MCDiffusionMod.main.MCDiffusionMod;
import com.konnitiha.MCDiffusionMod.renderer.RendererTest;
import com.konnitiha.MCDiffusionMod.renderer.ImagePaintingRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MCDiffusionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEvent {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MCDiffusionEntities.IMAGE_PAINTING_ENTITY_TYPE, ImagePaintingRenderer::new);
        event.registerEntityRenderer(MCDiffusionEntities.ENTITY_TEST_ENTITY_TYPE, RendererTest::new);
    }
}
