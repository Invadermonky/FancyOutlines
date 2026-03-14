package com.invadermonky.fancyoutlines.utils;

import com.invadermonky.fancyoutlines.config.ConfigHandlerBH;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import java.awt.*;

public class HighlighterHolder {
    public final Block block;
    public final int meta;
    public final Color color;
    public final float size;
    public final float red;
    public final float green;
    public final float blue;
    public final float alpha;

    public HighlighterHolder(Block block, int meta, float size, String colorCode) throws NumberFormatException, IndexOutOfBoundsException {
        this.block = block;
        this.meta = meta;
        this.size = size;
        this.color = hex2Rgb(colorCode);
        this.red = (float) this.color.getRed() / 255;
        this.green = (float) this.color.getGreen() / 255;
        this.blue = (float) this.color.getBlue() / 255;
        this.alpha = (float) this.color.getAlpha() / 255;
    }

    public HighlighterHolder(Block block, float size, String colorCode) throws NumberFormatException, IndexOutOfBoundsException {
        this(block, 16, size, colorCode);
    }

    public static HighlighterHolder getDefaultHolder() throws NumberFormatException, IndexOutOfBoundsException {
        return new HighlighterHolder(null, (float) ConfigHandlerBH.globalLineWidth, ConfigHandlerBH.globalColor);
    }

    public static HighlighterHolder getNoHarvestHolder() throws NumberFormatException, IndexOutOfBoundsException {
        return new HighlighterHolder(null, ConfigHandlerBH.noHarvestLineWidth, ConfigHandlerBH.noHarvestColor);
    }

    public static Color hex2Rgb(String colorStr) throws NumberFormatException, IndexOutOfBoundsException{
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ),
                Integer.valueOf( colorStr.substring( 7, 9 ), 16 )
        );
    }

    public boolean matches(IBlockState state) {
        return this.block == null || this.block == state.getBlock() && (this.meta == 16 || this.meta == state.getBlock().getMetaFromState(state));
    }
}
