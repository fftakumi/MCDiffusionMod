package com.konnitiha.MCDiffusionMod.item;

import com.konnitiha.MCDiffusionMod.common.HTTPConnection;
import com.konnitiha.MCDiffusionMod.main.MCDiffusionMod;
import com.mojang.blaze3d.pipeline.MainTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.system.CallbackI;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class ItemTest extends Item {


    public ItemTest() {
        super(new Properties().tab(MCDiffusionMod.MC_DIFFUSION_TAB));
    }

    public ItemTest(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Direction direction = ctx.getClickedFace();
        BlockPos pos = ctx.getClickedPos().relative(direction);
        Player player = ctx.getPlayer();
        ItemStack stack = ctx.getItemInHand();
        Level level = ctx.getLevel();
        Minecraft minecraft = Minecraft.getInstance();

        HTTPConnection httpConnection = new HTTPConnection();
        try {
            httpConnection.sendGet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return InteractionResult.SUCCESS;

    }

    @OnlyIn(Dist.CLIENT)
    public String saveScreenshot(Minecraft minecraft) throws IOException {
        NativeImage image = Screenshot.takeScreenshot(minecraft.getMainRenderTarget());
        File file1 = minecraft.gameDirectory.getCanonicalFile();
        File file2 = new File(file1.getParentFile(), "src/main/resources/assets/" + MCDiffusionMod.MOD_ID + "/textures/painting");
        File file3 = new File(file2, "screenshot-"+ String.valueOf(image.hashCode()) +".png");
        image.writeToFile(file3);
        return file3.getName();
    }
}
