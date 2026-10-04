package com.leclowndu93150.keymap.keys.sources;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.leclowndu93150.keymap.Keymap;
import com.leclowndu93150.keymap.config.KeymapConfig;
import com.leclowndu93150.keymap.keys.sources.keymap.KeymapSource;
import com.leclowndu93150.keymap.keys.sources.keymap.KeymapSources;
import com.leclowndu93150.keymap.keys.wrappers.keys.KeyHolder;

import java.util.Map;

public class KeymappingNotifier {
    protected static final Multimap<Integer, KeyHolder> keys = ArrayListMultimap.create();
    protected static final Multimap<Integer, KeybindingRegistrySubscriber> subscribers = ArrayListMultimap.create();

    public static Multimap<Integer, KeyHolder> keys() {
        return ImmutableMultimap.copyOf(keys);
    }

    public static Multimap<Integer, KeybindingRegistrySubscriber> subscribers() {
        return ImmutableMultimap.copyOf(subscribers);
    }

    public static boolean containsHolder(KeyHolder holder) {
        return keys.containsValue(holder);
    }

    public static boolean containsSubscriber(KeybindingRegistrySubscriber subscriber) {
        return subscribers.containsValue(subscriber);
    }

    public static void clearSubscribers() {
        subscribers.clear();
    }

    public static void loadKeys() {
        keys.clear();
        if (!KeymapSources.collected()) KeymapSources.collect();
        for (KeymapSource s : KeymapSources.sources()) {
            if (!s.canUseSource()) continue;
            for (KeyHolder holder : s.getKeyHolders()) {
                keys.put(holder.getSingleCode(), holder);
            }
        }
    }

    public static void load() {
        clearSubscribers();
        loadKeys();
    }

    public static void addKey(int code, KeyHolder holder) {
        if (containsHolder(holder)) {
            Keymap.logger().error("!! ADD_KEY IGNORED : REMOVE EXISTING HOLDER VALUE FIRST !!");
            return;
        }
        keys.put(code, holder);
    }

    public static void removeKey(int code, KeyHolder holder) {
        keys.remove(code, holder);
    }

    public static void notifyAllSubscriber() {
        notifyAllSubscriber(false);
    }

    public static void notifyAllSubscriber(boolean selected) {
        for (Map.Entry<Integer, KeybindingRegistrySubscriber> e : subscribers.entries()) {
            e.getValue().keybindingRegistryUpdated(selected);
        }
    }

    public static void notifySubscriber(int code, boolean selected) {
        if (!subscribers().containsKey(code)) return;

        for (KeybindingRegistrySubscriber subscriber : subscribers().get(code)) {
            subscriber.keybindingRegistryUpdated(selected);
        }
    }

    public static void subscribe(Integer key, KeybindingRegistrySubscriber subscriber) {
        subscribers.put(key, subscriber);
    }

    public static void unsubscribe(Integer key, KeybindingRegistrySubscriber subscriber) {
        subscribers.remove(key, subscriber);
    }

    public static Integer keyOf(KeyHolder holder) {
        if (!containsHolder(holder)) return -99;
        for (Map.Entry<Integer, KeyHolder> entry : keys.entries()) {
            if (entry.getValue().equals(holder)) return entry.getKey();
        }
        return -99;
    }

    public static void updateKey(Integer lastCode, Integer newCode, KeyHolder holder) {
        removeKey(lastCode, holder);

        if (containsHolder(holder)) {
            String msg = String.format(
                    "KeyHolder was not removed by the last removeKey call! [lastCode=%d, newCode=%d, keyOf=%d, holder=%s]",
                    lastCode, newCode, keyOf(holder), holder.getTranslatableName());
            if (KeymapConfig.instance().crashOnProblematicError()) {
                throw (new RuntimeException(msg));
            } else {
                Keymap.logger().fatal(msg);
            }
        }

        addKey(newCode, holder);

        for (KeybindingRegistrySubscriber s : subscribers().get(lastCode)) {
            s.keybindingRegistryUpdated(false);
        }
        for (KeybindingRegistrySubscriber s : subscribers().get(newCode)) {
            s.keybindingRegistryUpdated(false);
        }
    }

    public interface KeybindingRegistrySubscriber {
        void keybindingRegistryUpdated(boolean selected);
    }
}
