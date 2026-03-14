package com.invadermonky.fancyoutlines.config;

import com.invadermonky.fancyoutlines.FancyOutlines;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = FancyOutlines.MOD_ID)
public class ConfigHandlerBH {
    @Config.RangeInt(min = 0x0, max = 0xffffff)
    @Config.Name("Global Highlight Color")
    @Config.Comment("The global highlight RGBA color override. Format: #[red][green][blue][alpha] -> #00000000")
    public static String globalColor = "#00000066";

    @Config.RangeInt(min = 0, max = 1000)
    @Config.Name("Global Highlight Width")
    @Config.Comment("The global rendered highlight line width.")
    public static int globalLineWidth = 2;

    @Config.Name("Highlight Overrides")
    @Config.Comment({
            "Block specific highlight overrides.",
            "  Format: modid:blockid:[meta]=linewidth;color",
            "    modid - the mod id",
            "    blockid - the block id",
            "    meta - (optional) the block metadata",
            "    linewidth - the rendered line width, default MC value is 2",
            "    color - the hex RGBA color code",
            "  Examples:",
            "    minecraft:stone=20;#ff000066",
            "    minecraft:stone:0=20;#ff000066"
    })
    public static String[] highlightOverrides = new String[] {};

    @Config.Name("No Harvest Highlights")
    public static NoHarvestCategory noHarvestCategory = new NoHarvestCategory();

    public static class NoHarvestCategory {
        @Config.Name("Enable No Harvest Outlines")
        @Config.Comment("Enables the no harvest outline overrides that appear when a player cannot harvest a highlighted block.")
        public boolean enableNoHarvestOutlines = false;

        @Config.Name("No Harvest Global Color")
        @Config.Comment("The highlight RGBA color override for blocks that cannot be harvested. Format: #[red][green][blue][alpha] -> #00000000")
        public String noHarvestColor = "#ff000066";

        @Config.Name("No Harvest Global Line Width")
        @Config.Comment("The rendered highlight line width for blocks that cannot be harvested.")
        public int noHarvestLineWidth = 2;

        @Config.Name("No Harvest Highlight Overrides")
        @Config.Comment({
                "Block specific highlight overrides when a player cannot harvest the highlighted block.",
                "  Format: modid:blockid:[meta]=linewidth;color",
                "    modid - the mod id",
                "    blockid - the block id",
                "    meta - (optional) the block metadata",
                "    linewidth - the rendered line width, default MC value is 2",
                "    color - the hex RGBA color code",
                "  Examples:",
                "    minecraft:stone=20;#ff000066",
                "    minecraft:stone:0=20;#ff000066"
        })
        public String[] noHarvestHighlightOverrides = new String[] {};

    }

    @Mod.EventBusSubscriber(modid = FancyOutlines.MOD_ID)
    public static class ConfigChangeListener {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(FancyOutlines.MOD_ID)) {
                ConfigManager.sync(FancyOutlines.MOD_ID, Config.Type.INSTANCE);
                ConfigTags.syncConfig();
            }
        }
    }
}
