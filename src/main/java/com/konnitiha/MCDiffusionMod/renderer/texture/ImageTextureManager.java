package com.konnitiha.MCDiffusionMod.renderer.texture;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public class ImageTextureManager extends TextureAtlasHolder {
    public ImageTextureManager(TextureManager p_118887_, ResourceLocation p_118888_, String p_118889_) {
        super(p_118887_, p_118888_, p_118889_);
    }

    @Override
    protected Stream<ResourceLocation> getResourcesToLoad() {
        return null;
    }
}
