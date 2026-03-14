package com.invadermonky.fancyoutlines.config;

import com.invadermonky.fancyoutlines.FancyOutlines;
import com.invadermonky.fancyoutlines.utils.HighlighterHolder;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigTags {
    private static HighlighterHolder GLOBAL_HIGHLIGHTER;
    private static HighlighterHolder NO_HARVEST_HIGHLIGHTER;
    private static List<HighlighterHolder> BLOCK_HIGHLIGHTS = new ArrayList<>();

    @Nullable
    public static HighlighterHolder getBlockHighlight(EntityPlayer player, IBlockState state, BlockPos pos) {
        if(ConfigHandlerBH.noHarvestEnable && !canPlayerHarvest(player, state, pos)) {
            return NO_HARVEST_HIGHLIGHTER;
        }
        return BLOCK_HIGHLIGHTS.stream().filter(highlighter -> highlighter.matches(state)).findAny().orElse(GLOBAL_HIGHLIGHTER);
    }

    public static boolean canPlayerHarvest(EntityPlayer player, IBlockState state, BlockPos pos) {
        return ForgeHooks.canHarvestBlock(state.getBlock(), player, player.world, pos);
    }

    public static void syncConfig() {
        GLOBAL_HIGHLIGHTER = HighlighterHolder.getDefaultHolder();
        NO_HARVEST_HIGHLIGHTER = HighlighterHolder.getNoHarvestHolder();
        BLOCK_HIGHLIGHTS.clear();

        Pattern pattern = Pattern.compile("^([^:]+:[^:\\s]+):?(\\d+)?=(\\d+);(#[0-9a-fA-F]+|\\d+)$");
        for(String str : ConfigHandlerBH.highlightOverrides) {
            try {
                Matcher matcher = pattern.matcher(str);
                if (matcher.find()) {
                    ResourceLocation loc = new ResourceLocation(matcher.group(1));
                    Block block = ForgeRegistries.BLOCKS.getValue(loc);
                    if(block != null && block != Blocks.AIR) {
                        int meta = 16;
                        if(matcher.group(2) != null) {
                            meta = Integer.parseInt(matcher.group(2));
                        }
                        double size = MathHelper.clamp(Integer.parseInt(matcher.group(3)), 0, 1000);
                        String color = matcher.group(4);
                        BLOCK_HIGHLIGHTS.add(new HighlighterHolder(block, meta, (float) size, color));
                    } else {
                        throw new IllegalArgumentException("Unable to find registered block: " + loc);
                    }
                } else {
                    throw new IllegalArgumentException("Invalid configuration string: " + str);
                }
            } catch (IllegalArgumentException e) {
                FancyOutlines.LOGGER.error(e.getMessage());
            } catch (Exception e) {
                FancyOutlines.LOGGER.error("An error occurred while parsing Fancy Outlines' configuration.", e);
            }
        }
    }
}
