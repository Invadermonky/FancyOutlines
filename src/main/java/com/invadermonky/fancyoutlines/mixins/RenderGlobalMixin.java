package com.invadermonky.fancyoutlines.mixins;

import com.invadermonky.fancyoutlines.config.ConfigTags;
import com.invadermonky.fancyoutlines.utils.HighlighterHolder;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@SideOnly(Side.CLIENT)
@Mixin(RenderGlobal.class)
public class RenderGlobalMixin {
    @Shadow private WorldClient world;

    @Redirect(
            method = "drawSelectionBox",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/RenderGlobal;drawSelectionBoundingBox(Lnet/minecraft/util/math/AxisAlignedBB;FFFF)V"
            )
    )
    private void drawHighlightedSelectionBox(AxisAlignedBB box, float red, float green, float blue, float alpha, @Local(ordinal = 0, argsOnly = true) EntityPlayer player, @Local(ordinal = 0, argsOnly = true) float partialTicks, @Local(ordinal = 0) BlockPos pos, @Local(ordinal = 0) IBlockState state) {
        HighlighterHolder holder = ConfigTags.getBlockHighlight(state);
        if(holder != null) {
            GlStateManager.glLineWidth(holder.size);
            red = holder.red;
            green = holder.green;
            blue = holder.blue;
            alpha = holder.alpha;
        }
        RenderGlobal.drawSelectionBoundingBox(box, red, green, blue, alpha);
    }
}
