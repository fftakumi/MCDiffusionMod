package com.konnitiha.MCDiffusionMod.renderer;

import com.konnitiha.MCDiffusionMod.entity.EntityTest;
import com.konnitiha.MCDiffusionMod.main.MCDiffusionMod;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.PaintingTextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class RendererTest extends EntityRenderer<EntityTest> {
    public RendererTest(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityTest entityTest) {
        return entityTest.getImageLocation();
    }

    public void render(@NotNull EntityTest painting, float p_115553_, float p_115554_, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int p_115557_) {
        poseStack.pushPose();
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - p_115553_));
        float f = 0.0625F;

        ResourceLocation rs = painting.getImageLocation();
        Motive motive = painting.motive;

        poseStack.scale(0.0625F, 0.0625F, 0.0625F);
        NativeImage image = new NativeImage(100, 100, true);
        Minecraft minecraft = Minecraft.getInstance();

        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.entitySolid(this.getTextureLocation(painting)));
        this.renderPainting(poseStack, vertexconsumer, painting, 128, 128);
        poseStack.popPose();
        //System.out.println();

        super.render(painting, p_115553_, p_115554_, poseStack, multiBufferSource, p_115557_);

    }

    private void renderPainting(PoseStack poseStack, VertexConsumer vertexConsumer, EntityTest painting, int width, int height) {
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        float centerX = (float)(-width) / 2.0F;
        float centerY = (float)(-height) / 2.0F;
        // 16 = pixel/block
        int i = width / 16;
        int j = height / 16;
        // 128 = pixel num

        for(int k = 0; k < i; ++k) {
            for(int l = 0; l < j; ++l) {
                float rightX = centerX + (float)((k + 1) * 16);
                float leftX = centerX + (float)(k * 16);
                float downY = centerY + (float)((l + 1) * 16);
                float upY = centerY + (float)(l * 16);
                int paintBlockX = painting.getBlockX();
                int paintBlockY = Mth.floor(painting.getY() + (double)((downY + upY) / 2.0F / 16.0F));
                int paintBlockZ = painting.getBlockZ();
                Direction direction = painting.getDirection();
                if (direction == Direction.NORTH) {
                    paintBlockX = Mth.floor(painting.getX() + (double)((rightX + leftX) / 2.0F / 16.0F));
                }

                if (direction == Direction.WEST) {
                    paintBlockZ = Mth.floor(painting.getZ() - (double)((rightX + leftX) / 2.0F / 16.0F));
                }

                if (direction == Direction.SOUTH) {
                    paintBlockX = Mth.floor(painting.getX() - (double)((rightX + leftX) / 2.0F / 16.0F));
                }

                if (direction == Direction.EAST) {
                    paintBlockZ = Mth.floor(painting.getZ() + (double)((rightX + leftX) / 2.0F / 16.0F));
                }

                int l1 = LevelRenderer.getLightColor(painting.level, new BlockPos(paintBlockX, paintBlockY, paintBlockZ));
                float f19 = (((float) (i-k))/ (float) i);
                float f20 = ((float)(i-k-1)/ (float) i);
                float f21 = (((float) (j-l))/(float) j);
                float f22 = ((float) (j-l-1)/(float) j);
                this.vertex(matrix4f, matrix3f, vertexConsumer, rightX, upY, f20, f21, -0.5F, 0, 0, -1, l1);
                this.vertex(matrix4f, matrix3f, vertexConsumer, leftX, upY, f19, f21, -0.5F, 0, 0, -1, l1);
                this.vertex(matrix4f, matrix3f, vertexConsumer, leftX, downY, f19, f22, -0.5F, 0, 0, -1, l1);
                this.vertex(matrix4f, matrix3f, vertexConsumer, rightX, downY, f20, f22, -0.5F, 0, 0, -1, l1);
            }
        }
    }

    private void vertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float p_115540_, float p_115541_, float p_115542_, float p_115543_, float p_115544_, int p_115545_, int p_115546_, int p_115547_, int p_115548_) {
        vertexConsumer.vertex(matrix4f, p_115540_, p_115541_, p_115544_).color(255, 255, 255, 255).uv(p_115542_, p_115543_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_115548_).normal(matrix3f, (float)p_115545_, (float)p_115546_, (float)p_115547_).endVertex();
    }
}
