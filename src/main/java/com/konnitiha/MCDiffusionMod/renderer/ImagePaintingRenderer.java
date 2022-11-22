package com.konnitiha.MCDiffusionMod.renderer;

import com.konnitiha.MCDiffusionMod.entity.ImagePaintingEntity;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.stb.STBImage;

@OnlyIn(Dist.CLIENT)
public class ImagePaintingRenderer extends EntityRenderer<ImagePaintingEntity> {
    public ImagePaintingRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    public void render(ImagePaintingEntity imagePaintingEntity, float p_115553_, float p_115554_, PoseStack poseStack, MultiBufferSource multiBufferSource, int p_115557_) {
        poseStack.pushPose();
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - p_115553_));
        float f = 0.0625F;

        ResourceLocation rs = imagePaintingEntity.imageLocation;

        poseStack.scale(0.0625F, 0.0625F, 0.0625F);
        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.entitySolid(this.getTextureLocation(imagePaintingEntity)));
        this.renderPainting(poseStack, vertexconsumer, imagePaintingEntity, imagePaintingEntity.getWidth(), imagePaintingEntity.getHeight());
        poseStack.popPose();
        //System.out.println();

        super.render(imagePaintingEntity, p_115553_, p_115554_, poseStack, multiBufferSource, p_115557_);
    }

    public ResourceLocation getTextureLocation(ImagePaintingEntity imagePaintingEntity) {
        return imagePaintingEntity.imageLocation;
    }

    private void renderPainting(PoseStack poseStack, VertexConsumer vertexConsumer, ImagePaintingEntity imagePaintingEntity, int width, int height) {
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        float centerX = (float)(-width) / 2.0F;
        float centerY = (float)(-height) / 2.0F;
        // 16 = pixel/block
        int pixel_block = 32;
        int i = width / pixel_block;
        int j = height / pixel_block;
        // 128 = pixel num

        for(int k = 0; k < i; ++k) {
            for(int l = 0; l < j; ++l) {
                float rightX = centerX + (float)((k + 1) * 16);
                float leftX = centerX + (float)(k * 16);
                float downY = centerY + (float)((l + 1) * 16);
                float upY = centerY + (float)(l * 16);
                int paintBlockX = imagePaintingEntity.getBlockX();
                int paintBlockY = Mth.floor(imagePaintingEntity.getY() + (double)((downY + upY) / 2.0F / 16.0F));
                int paintBlockZ = imagePaintingEntity.getBlockZ();
                Direction direction = imagePaintingEntity.getDirection();
                if (direction == Direction.NORTH) {
                    paintBlockX = Mth.floor(imagePaintingEntity.getX() + (double)((rightX + leftX) / 2.0F / 16.0F));
                }

                if (direction == Direction.WEST) {
                    paintBlockZ = Mth.floor(imagePaintingEntity.getZ() - (double)((rightX + leftX) / 2.0F / 16.0F));
                }

                if (direction == Direction.SOUTH) {
                    paintBlockX = Mth.floor(imagePaintingEntity.getX() - (double)((rightX + leftX) / 2.0F / 16.0F));
                }

                if (direction == Direction.EAST) {
                    paintBlockZ = Mth.floor(imagePaintingEntity.getZ() + (double)((rightX + leftX) / 2.0F / 16.0F));
                }

                int l1 = LevelRenderer.getLightColor(imagePaintingEntity.level, new BlockPos(paintBlockX, paintBlockY, paintBlockZ));
                float u0 = (((float) (i-k))/ (float) i);
                float u1 = ((float)(i-k-1)/ (float) i);
                float v0 = (((float) (j-l))/(float) j);
                float v1 = ((float) (j-l-1)/(float) j);
                this.vertex(matrix4f, matrix3f, vertexConsumer, rightX, upY, u1, v0, -0.5F, 0, 0, -1, l1);
                this.vertex(matrix4f, matrix3f, vertexConsumer, leftX, upY, u0, v0, -0.5F, 0, 0, -1, l1);
                this.vertex(matrix4f, matrix3f, vertexConsumer, leftX, downY, u0, v1, -0.5F, 0, 0, -1, l1);
                this.vertex(matrix4f, matrix3f, vertexConsumer, rightX, downY, u1, v1, -0.5F, 0, 0, -1, l1);
            }
        }
    }

    private void vertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float x, float y, float u, float v, float z, int x1, int y1, int z1, int p_115548_) {
        vertexConsumer.vertex(matrix4f, x, y, z).color(255, 255, 255, 255).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_115548_).normal(matrix3f, (float)x1, (float)y1, (float)z1).endVertex();
    }
}
