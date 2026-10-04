package com.leclowndu93150.keymap.cross;

import com.leclowndu93150.keymap.cross.services.IKeybindHelper;
import com.leclowndu93150.keymap.cross.services.IPlatformHelper;
import com.leclowndu93150.keymap.cross.services.ITickHelper;

import java.util.ServiceLoader;

public class Services {
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IKeybindHelper KEYBIND = PLATFORM.keybindHelper();
    public static final ITickHelper TICK = PLATFORM.tickHelper();

    private Services() {}

    public static <T> T load(Class<T> c) {
        return ServiceLoader.load(c)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Can't load service for %s", c.getName())));
    }
}
