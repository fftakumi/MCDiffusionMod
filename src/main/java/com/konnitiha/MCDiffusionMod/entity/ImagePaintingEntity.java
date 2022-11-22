package com.konnitiha.MCDiffusionMod.entity;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import com.konnitiha.MCDiffusionMod.client.Packet.ClientboundAddScreenPacket;
import com.konnitiha.MCDiffusionMod.main.MCDiffusionEntities;
import com.konnitiha.MCDiffusionMod.main.MCDiffusionMod;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import javax.swing.*;

public class ImagePaintingEntity extends HangingEntity {
    public static final String REGISTRY_NAME = "image_painting_entity";

    private NativeImage image;

    private int width = 16;
    private int height = 16;

    public ResourceLocation imageLocation = new ResourceLocation(MCDiffusionMod.MOD_ID,"textures/painting/test.png");

    public ImagePaintingEntity(EntityType<? extends ImagePaintingEntity> p_31904_, Level p_31905_) {
        super(p_31904_, p_31905_);
    }

    public ImagePaintingEntity(Level p_31907_, BlockPos p_31908_, Direction p_31909_) {
        super(MCDiffusionEntities.IMAGE_PAINTING_ENTITY_TYPE, p_31907_, p_31908_);
        this.imageLocation = new ResourceLocation(MCDiffusionMod.MOD_ID,"textures/painting/cihiro_org.png");
        this.setDirection(p_31909_);
    }

    public ImagePaintingEntity(Level level, BlockPos pos, Direction direction, ResourceLocation resourceLocation) {
        this(level, pos, direction);
        this.imageLocation = resourceLocation;
        RenderType renderType = RenderType.entitySolid(resourceLocation);
        try {
            FileInputStream file = new FileInputStream("../src/main/resources/assets/mcdiffusionmod/textures/painting/cihiro_org.png");
            this.image = NativeImage.read(file);
            this.width = this.image.getWidth();
            this.height = this.image.getHeight();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setDirection(direction);
    }

    public void addAdditionalSaveData(CompoundTag p_31935_) {
        p_31935_.putString("ImageLocation", this.imageLocation.toString());
        p_31935_.putByte("Facing", (byte)this.direction.get2DDataValue());
        super.addAdditionalSaveData(p_31935_);
    }

    public void readAdditionalSaveData(CompoundTag p_31927_) {
        this.imageLocation = new ResourceLocation(p_31927_.getString("ImageLocation"));
        this.direction = Direction.from2DDataValue(p_31927_.getByte("Facing"));
        super.readAdditionalSaveData(p_31927_);
        this.setDirection(this.direction);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public void dropItem(@Nullable Entity p_31925_) {
        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            this.playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);
            if (p_31925_ instanceof Player) {
                Player player = (Player)p_31925_;
                if (player.getAbilities().instabuild) {
                    return;
                }
            }

            this.spawnAtLocation(Items.PAINTING);
        }
    }

    public void playPlacementSound() {
        this.playSound(SoundEvents.PAINTING_PLACE, 1.0F, 1.0F);
    }

    public void moveTo(double p_31929_, double p_31930_, double p_31931_, float p_31932_, float p_31933_) {
        this.setPos(p_31929_, p_31930_, p_31931_);
    }

    public void lerpTo(double p_31917_, double p_31918_, double p_31919_, float p_31920_, float p_31921_, int p_31922_, boolean p_31923_) {
        BlockPos blockpos = this.pos.offset(p_31917_ - this.getX(), p_31918_ - this.getY(), p_31919_ - this.getZ());
        this.setPos((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ());
    }


    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddScreenPacket(this);
        //return NetworkHooks.getEntitySpawningPacket(this);
    }

    public ItemStack getPickResult() {
        return new ItemStack(Items.PAINTING);
    }


}
