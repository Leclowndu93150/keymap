package com.leclowndu93150.keymap.keys.extrakeybind;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leclowndu93150.keymap.Keymap;
import com.leclowndu93150.keymap.config.KeymapConfig;
import com.leclowndu93150.keymap.cross.Services;
import com.leclowndu93150.keymap.keys.sources.keymap.KeymapSource;
import com.leclowndu93150.keymap.keys.sources.keymap.KeymapSources;
import com.leclowndu93150.keymap.keys.wrappers.keys.KeyHolder;
import com.leclowndu93150.keymap.keys.wrappers.keys.VanillaKeymap;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

public class KeymapRegistry {
    public static final Type TYPE_BIND_MAP_KEY = new TypeToken<Map<String, KeyComboData>>() {}.getType();

    public static final List<Integer> MODIFIER_KEYS = Arrays.asList(
            InputConstants.KEY_LALT,
            InputConstants.KEY_RALT,
            InputConstants.KEY_LSHIFT,
            InputConstants.KEY_RSHIFT,
            InputConstants.KEY_LCONTROL,
            InputConstants.KEY_RCONTROL);

    public static List<Integer> MODIFIER_KEYS() { return MODIFIER_KEYS; }

    protected static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    protected static final BiMap<KeyMapping, KeyComboData> bindMap = HashBiMap.create();
    protected static Map<String, KeyComboData> bindMapKey = new HashMap<>();
    protected static File cfgFile = null;
    protected static boolean loaded = false;

    private KeymapRegistry() {}

    protected static synchronized File cfgFile() {
        return cfgFile != null ? cfgFile : Services.PLATFORM.config("keymap-keys.json");
    }

    public static BiMap<KeyMapping, KeyComboData> bindMap() {
        return ImmutableBiMap.copyOf(bindMap);
    }

    public static Map<String, KeyComboData> bindMapKey() {
        return Collections.unmodifiableMap(bindMapKey);
    }

    public static synchronized void load() {
        load(false);
    }

    public static synchronized void load(boolean force) {
        if (loaded && !force) return;
        try (FileReader reader = new FileReader(cfgFile())) {
            Map<String, KeyComboData> read = gson.fromJson(reader, TYPE_BIND_MAP_KEY);
            if (read != null) bindMapKey = read;
            else bindMapKey = new HashMap<>();
            Set<String> keys = bindMapKey.keySet();

            for (KeymapSource source : KeymapSources.sources()) {
                if (!source.canUseSource()) continue;

                List<KeyHolder> keyHolders = source.getKeyHolders();
                if (keyHolders.isEmpty() || !(keyHolders.get(0) instanceof VanillaKeymap)) continue;

                List<VanillaKeymap> vk = new ArrayList<>();
                for (KeyHolder kh : keyHolders) {
                    if (kh instanceof VanillaKeymap v) vk.add(v);
                }

                for (VanillaKeymap v : vk) {
                    if (keys.contains(v.map().getName())) {
                        add(v.map(), bindMapKey.get(v.map().getName()));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            Keymap.logger().info("No keymap complex-key save found");
        } catch (Exception e) {
            Keymap.logger().error("!! Can't read keymap's complex-key save !!", e);
        } finally {
            loaded = true;
        }
    }

    public static synchronized void save() {
        try (FileWriter writer = new FileWriter(cfgFile())) {
            gson.toJson(bindMapKey(), writer);
        } catch (Exception e) {
            Keymap.logger().error("!! Can't save key config !!", e);
        }
    }

    public static void add(KeyMapping kb, KeyComboData kd) {
        if (bindMap.containsKey(kb)) {
            String msg = "Attempted to add an already existing keybind";

            if (KeymapConfig.instance().crashOnProblematicError()) throw new RuntimeException(msg);
            else Keymap.logger().error(msg);
        }

        bindMap.put(kb, kd);
        bindMapKey.put(kb.getName(), kd);
        Keymap.logger().info("Registered complex keybinding [{}]", kd);
        save();
    }

    public static void put(KeyMapping kb, KeyComboData kd) {
        remove(kd);
        if (!bindMap.containsKey(kb)) {
            add(kb, kd);
            return;
        }
        bindMapKey.put(kb.getName(), kd);
        bindMap.put(kb, kd);
        Keymap.logger().info("Updated complex keybinding [{}]", kd);
        save();
    }

    public static void remove(KeyComboData kd) {
        if (bindMap.inverse().containsKey(kd)) remove(bindMap.inverse().get(kd));
    }

    public static void remove(KeyMapping kb) {
        if (!bindMap.containsKey(kb)) {
            Keymap.logger().warn("Remove ignored, Keymapping [{}] not registered!", kb.getName());
            return;
        }
        KeyComboData kd = bindMap.get(kb);
        bindMap.remove(kb);
        bindMapKey.remove(kb.getName());
        Keymap.logger().info("Removed complex keybinding [{}]", kd);
        save();
    }

    public static void resetAll() {
        bindMap.clear();
        bindMapKey.clear();
        save();
    }

    public static boolean contains(KeyMapping kb) {
        return bindMap.containsKey(kb);
    }

    public static boolean containsKey(int k) {
        for (Map.Entry<KeyMapping, KeyComboData> entry : bindMap.entrySet()) {
            if (entry.getValue().keyCode == k) return true;
        }

        return false;
    }

    public static List<KeyMapping> getMappings(int k) {
        List<KeyMapping> m = new ArrayList<>();

        for (Map.Entry<KeyMapping, KeyComboData> entry : bindMap.entrySet()) {
            if (entry.getValue().keyCode == k) m.add(entry.getKey());
        }

        return m;
    }
}
