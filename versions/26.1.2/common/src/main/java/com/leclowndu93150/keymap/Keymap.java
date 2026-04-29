package com.leclowndu93150.keymap;

import com.leclowndu93150.keymap.client.gui.screen.KeymapScreen;
import com.leclowndu93150.keymap.config.KeymapConfig;
import com.leclowndu93150.keymap.cross.Services;
import com.leclowndu93150.keymap.keys.layout.KeyLayout;
import com.leclowndu93150.keymap.keys.sources.category.CategorySources;
import com.leclowndu93150.keymap.keys.sources.keymap.KeymapSources;
import com.leclowndu93150.keymap.objects.Credits;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Keymap {
    public static final String MOD_ID = "keymap";
    public static final String SERVER_WARN =
            "Keymap is being ran on a DedicatedServer environment, even though it can only work on Client side environment";

    protected static final String MOD_NAME = "keymap";
    public static final Logger logger = LogManager.getLogger();

    protected static KeyMapping kmOpenMapper;

    private Keymap() {}

    public static String getModName() { return MOD_NAME; }
    public static Logger getLogger() { return logger; }
    public static Logger logger() { return logger; }
    public static KeyMapping getKmOpenMapper() { return kmOpenMapper; }
    public static KeyMapping kmOpenMapper() { return kmOpenMapper; }

    public static void init() {
        KeymapConfig.load();
        logger.info("Keymap loaded, loader={}, dev={}", Services.PLATFORM.loader(), Services.PLATFORM.dev());

        kmOpenMapper = Services.KEYBIND.create(
                InputConstants.Type.KEYSYM, InputConstants.KEY_GRAVE, "keymap.keyOpenKeymap", "keymap.keyCat");

        KeymapSources.collect();
        CategorySources.collect();

        Services.TICK.registerEndClientTick(client -> {
            while (kmOpenMapper.consumeClick()) {
                client.setScreen(new KeymapScreen(null));
            }
        });

        try {
            Credits c = Credits.instance();
            if (c != null) logger.warn(c.toString());
        } catch (Throwable ignored) {
        }
    }
}
