package com.konnitiha.MCDiffusionMod.main;

import com.konnitiha.MCDiffusionMod.item.ImagePaintingItem;
import com.konnitiha.MCDiffusionMod.item.ItemTest;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MCDiffusionItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MCDiffusionMod.MOD_ID);

    public static final RegistryObject<ItemTest> TEST_ITEM= ITEMS.register(
            "item_test",
            ItemTest::new
    );

    public static final RegistryObject<ImagePaintingItem> SCREEN_ITEM = ITEMS.register(
            ImagePaintingItem.REGISTRY_NAME,
            ImagePaintingItem::new
    );
}
