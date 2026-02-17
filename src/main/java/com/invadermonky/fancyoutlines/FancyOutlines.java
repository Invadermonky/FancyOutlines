package com.invadermonky.fancyoutlines;

import com.invadermonky.fancyoutlines.config.ConfigTags;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = FancyOutlines.MOD_ID,
        name = FancyOutlines.MOD_NAME,
        version = FancyOutlines.MOD_VERSION,
        dependencies = FancyOutlines.DEPENDENCIES,
        clientSideOnly = true
)
public class FancyOutlines {
    public static final String MOD_ID = Tags.MOD_ID;
    public static final String MOD_NAME = Tags.MOD_NAME;
    public static final String MOD_VERSION = Tags.VERSION;
    public static final String DEPENDENCIES = "required-after:mixinbooter";

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ConfigTags.syncConfig();
    }
}
