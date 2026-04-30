package com.leclowndu93150.keymap.fabric;

import com.leclowndu93150.keymap.Keymap;
import net.fabricmc.api.ClientModInitializer;

public class KeymapFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Keymap.init();
    }
}
