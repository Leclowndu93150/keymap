package com.leclowndu93150.keymap.keys.layout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.leclowndu93150.keymap.Keymap;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyLayout {
    protected static KeyLayout layoutDefault;
    protected static KeyLayout layoutCurrent;
    protected static HashMap<String, KeyLayout> layouts = new HashMap<>();

    protected KeyMeta meta;
    protected Keys keys;

    public KeyLayout() {}

    public KeyLayout(KeyMeta meta, Keys keys) {
        this.meta = meta;
        this.keys = keys;
    }

    public static KeyLayout layoutDefault() { return layoutDefault; }
    public static KeyLayout layoutCurrent() { return layoutCurrent; }
    public static HashMap<String, KeyLayout> layouts() { return layouts; }

    public KeyMeta meta() { return meta; }
    public Keys keys() { return keys; }
    public KeyLayout meta(KeyMeta m) { this.meta = m; return this; }
    public KeyLayout keys(Keys k) { this.keys = k; return this; }

    public static void registerLayout(KeyLayout layout) {
        layouts.put(layout.meta.code, layout);
        updateMouseKeys(layout.keys.mouse());
        updateMouseKeys(layout.keys.basic());
        updateMouseKeys(layout.keys.numpad());
        updateMouseKeys(layout.keys.extra());
    }

    protected static void updateMouseKeys(List<KeyRow> rows) {
        for (KeyRow row : rows) {
            if (row.row == null) continue;
            for (KeyData key : row.row) {
                if (key.code() < 10) {
                    key.mouse(true);
                }
            }
        }
    }

    public static void loadKeys() throws IOException {
        layouts.clear();

        var resourceManager = Minecraft.getInstance().getResourceManager();
        FileToIdConverter converter = FileToIdConverter.json("keymap_layouts");
        Map<ResourceLocation, Resource> keymapLayouts = converter.listMatchingResources(resourceManager);

        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();

        for (final var entry : keymapLayouts.entrySet()) {
            try {
                Keymap.logger().info("Load layout {}", entry.getKey());
                registerLayout(gson.fromJson(entry.getValue().openAsReader(), KeyLayout.class));
            } catch (JsonSyntaxException | JsonIOException | IOException e) {
                Keymap.logger().error("Failed to load keymap layout {}", entry.getKey(), e);
            }
        }
    }

    public static KeyLayout getCurrentLayout() {
        return layoutCurrent;
    }

    public static KeyLayout getLayoutWithCode(String code) {
        if (layouts.containsKey(code)) {
            return layouts.get(code);
        } else {
            Keymap.logger().warn("Cannot find layout for [{}], defaulting to en_us", code);
            return layouts.get("en_us");
        }
    }
}
