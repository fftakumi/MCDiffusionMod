package com.konnitiha.MCDiffusionMod.main;

import com.konnitiha.MCDiffusionMod.entity.EntityTest;
import com.konnitiha.MCDiffusionMod.entity.ImagePaintingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MCDiffusionEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MCDiffusionMod.MOD_ID);


    public static final EntityType<EntityTest> ENTITY_TEST_ENTITY_TYPE = EntityType.Builder
            .<EntityTest>of(EntityTest::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(10)
            .updateInterval(Integer.MAX_VALUE)
            .build(new ResourceLocation(MCDiffusionMod.MOD_ID, "entity_test").toString());
    public static final RegistryObject<EntityType<EntityTest>> ENTITY_TEST = ENTITIES.register(
            "entity_test",
            () -> ENTITY_TEST_ENTITY_TYPE
    );

    public static final EntityType<ImagePaintingEntity> IMAGE_PAINTING_ENTITY_TYPE = EntityType.Builder
            .<ImagePaintingEntity>of(ImagePaintingEntity::new, MobCategory.MISC)
            .sized(0.5F,0.5F)
            .clientTrackingRange(10)
            .updateInterval(Integer.MAX_VALUE)
            .build(new ResourceLocation(MCDiffusionMod.MOD_ID, ImagePaintingEntity.REGISTRY_NAME).toString()
            );

    public static final RegistryObject<EntityType<ImagePaintingEntity>> IMAGE_PAINTING_ENTITY = ENTITIES.register(
            ImagePaintingEntity.REGISTRY_NAME,
            () -> IMAGE_PAINTING_ENTITY_TYPE
    );
}
