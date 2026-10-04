package com.leclowndu93150.keymap.keys.sources.category;

import com.leclowndu93150.keymap.Keymap;

import java.util.ArrayList;
import java.util.List;

public class CategorySources {
    protected static List<CategorySource> sources = new ArrayList<>();
    protected static boolean collected = false;

    public static List<CategorySource> sources() { return sources; }
    public static boolean collected() { return collected; }

    private CategorySources() {}

    public static void collect() {
        if (collected) {
            Keymap.logger().warn("CategoryRegistry collect() already called!");
            sources.clear();
        }

        register(new AllCategorySource());
        register(new VanillaCategorySource());

        collected = true;
    }

    public static void register(CategorySource source) {
        Keymap.logger().info("Registered CategorySource: {}", source.getClass().getName());
        if (sources.contains(source)) return;
        sources.add(source);
    }
}
