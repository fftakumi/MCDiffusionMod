package com.konnitiha.MCDiffusionMod.entity;

import com.konnitiha.MCDiffusionMod.main.MCDiffusionEntities;
import com.konnitiha.MCDiffusionMod.main.MCDiffusionMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import javax.swing.*;

public class EntityTest extends HangingEntity {
    public Motive motive = Motive.KEBAB;

    public static final ResourceLocation DEFAULT_LOCATION = new ResourceLocation(MCDiffusionMod.MOD_ID, "textures/painting/test.png");
    private ResourceLocation imageLocation = new ResourceLocation(MCDiffusionMod.MOD_ID, "textures/painting/test.png");;
    // 登録するときにこの引数のコンストラクターが必要

    public String str = "aaa";
    private ImageIcon icon;
    public EntityTest(EntityType<? extends HangingEntity> entityType, Level level) {
        super(entityType, level);
    }

    public EntityTest(Level level, BlockPos pos, Direction direction, ResourceLocation resourceLocation) {
        super(MCDiffusionEntities.ENTITY_TEST.get(), level ,pos);
        this.imageLocation = resourceLocation;
        //this.icon = new ImageIcon("/Users/takumi/IntelliJIDEProjects/MODs/MCDiffusion-1-18-1/src/main/resources/assets/mcdiffusionmod/textures/painting/cihiro_org.png");
        this.setDirection(direction);
        this.motive = Motive.SKULL_AND_ROSES;
        this.str = "iii";
    }

    public EntityTest(Level level, BlockPos pos, Direction direction) {
        this(level, pos, direction, new ResourceLocation(MCDiffusionMod.MOD_ID, "textures/painting/cihiro_org.png"));
        this.setDirection(direction);
    }

    public ResourceLocation getImageLocation() {
        return this.imageLocation;
    }

    public void setImageLocation(ResourceLocation resourceLocation) {
        this.imageLocation = resourceLocation;
    }


    public void addAdditionalSaveData(CompoundTag p_31935_) {
        p_31935_.putString("Resource", this.imageLocation.toString());
        p_31935_.putByte("Facing", (byte)this.direction.get2DDataValue());
        super.addAdditionalSaveData(p_31935_);
    }

    public void readAdditionalSaveData(CompoundTag p_31927_) {
        this.imageLocation = new ResourceLocation(p_31927_.getString("Resource"));
        this.direction = Direction.from2DDataValue(p_31927_.getByte("Facing"));
        super.readAdditionalSaveData(p_31927_);
        this.setDirection(this.direction);
    }

    public int getWidth() {
        return 64;
    }

    public int getHeight() {
        return 64;
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

    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    public ItemStack getPickResult() {
        return new ItemStack(Items.PAINTING);
    }
}
