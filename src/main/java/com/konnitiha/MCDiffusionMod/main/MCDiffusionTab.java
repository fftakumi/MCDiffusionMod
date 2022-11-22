package com.konnitiha.MCDiffusionMod.main;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MCDiffusionTab extends CreativeModeTab {

    public MCDiffusionTab() {
        super("mcdiffusion_tab");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(Items.DIAMOND);
    }
}
