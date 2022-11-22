package com.konnitiha.MCDiffusionMod.item;

import com.konnitiha.MCDiffusionMod.entity.ImagePaintingEntity;
import com.konnitiha.MCDiffusionMod.main.MCDiffusionEntities;
import com.konnitiha.MCDiffusionMod.main.MCDiffusionMod;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HangingEntityItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nonnull;

public class ImagePaintingItem extends HangingEntityItem {

    public static final String REGISTRY_NAME = "image_painting_item";

    public ImagePaintingItem() {
        super(MCDiffusionEntities.IMAGE_PAINTING_ENTITY_TYPE, new Item.Properties().tab(MCDiffusionMod.MC_DIFFUSION_TAB));
    }

    public ImagePaintingItem(EntityType<? extends HangingEntity> entityType, Properties properties) {
        super(entityType, properties);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Direction direction = ctx.getClickedFace();
        BlockPos pos = ctx.getClickedPos().relative(direction);
        Player player = ctx.getPlayer();
        ItemStack stack = ctx.getItemInHand();
        Level level = ctx.getLevel();

        if(direction.getAxis().isVertical() || player != null && !player.mayUseItemAt(pos, direction, stack)) {
            return InteractionResult.FAIL;
        } else {
            ImagePaintingEntity imagePaintingEntity = new ImagePaintingEntity(
                    level,
                    pos,
                    direction
            );

            CompoundTag tag = stack.getTag();
            if(tag != null) {
                EntityType.updateCustomEntityTag(level, player, imagePaintingEntity, tag);
            }

            if(imagePaintingEntity.survives()) {
                if(!level.isClientSide()) {
                    imagePaintingEntity.playPlacementSound();
                    level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
                    level.addFreshEntity(imagePaintingEntity);
                }

                stack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                if(!level.isClientSide() && player != null) {
                    player.sendMessage(new TranslatableComponent(Util.makeDescriptionId(
                            "message",
                            new ResourceLocation(
                                    MCDiffusionMod.MOD_ID,
                                    "painting_error"
                            )
                    )), Util.NIL_UUID);
                }
                return InteractionResult.CONSUME;
            }
         }
    }
}
