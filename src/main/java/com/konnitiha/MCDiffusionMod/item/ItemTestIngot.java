package com.konnitiha.MCDiffusionMod.item;

import com.konnitiha.MCDiffusionMod.main.MCDiffusionMod;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class ItemTestIngot extends Item {
    public ItemTestIngot() {
        super(new Properties().tab(MCDiffusionMod.MC_DIFFUSION_TAB).fireResistant());
        this.setRegistryName("test_ingot");
    }

}
