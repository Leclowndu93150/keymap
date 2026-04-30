package com.leclowndu93150.keymap.keys.sources.keymap;

import com.leclowndu93150.keymap.keys.wrappers.keys.KeyHolder;

import java.util.List;

public interface KeymapSource {
    List<KeyHolder> getKeyHolders();
    boolean canUseSource();
}
