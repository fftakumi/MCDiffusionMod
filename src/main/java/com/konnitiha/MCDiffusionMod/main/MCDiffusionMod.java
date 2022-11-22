package com.konnitiha.MCDiffusionMod.main;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("mcdiffusionmod")
public class MCDiffusionMod {
    public static final String MOD_ID = "mcdiffusionmod";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final CreativeModeTab MC_DIFFUSION_TAB = new MCDiffusionTab();

    public MCDiffusionMod() {
        final var bus = FMLJavaModLoadingContext.get().getModEventBus();

        MCDiffusionItems.ITEMS.register(bus);
        MCDiffusionEntities.ENTITIES.register(bus);
    }
}
