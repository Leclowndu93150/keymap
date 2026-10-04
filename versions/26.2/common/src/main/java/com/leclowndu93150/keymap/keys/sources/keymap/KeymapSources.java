package com.leclowndu93150.keymap.keys.sources.keymap;

import com.leclowndu93150.keymap.Keymap;

import java.util.ArrayList;
import java.util.List;

public class KeymapSources {
    protected static List<KeymapSource> sources = new ArrayList<>();
    protected static boolean collected = false;

    public static List<KeymapSource> sources() { return sources; }
    public static boolean collected() { return collected; }

    private KeymapSources() {}

    public static void collect() {
        if (collected) {
            Keymap.logger().warn("KeymapRegistry collect() already called!");
            sources.clear();
        }

        register(new VanillaKeymapSource());

        collected = true;
    }

    public static void register(KeymapSource source) {
        Keymap.logger().debug("Registered KeymapSource: {}", source.getClass().getName());
        if (sources.contains(source)) return;
        sources.add(source);
    }
}
