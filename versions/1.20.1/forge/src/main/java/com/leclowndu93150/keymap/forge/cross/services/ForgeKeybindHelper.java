package com.leclowndu93150.keymap.forge.cross.services;

import com.leclowndu93150.keymap.cross.services.IKeybindHelper;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

import java.util.ArrayList;
import java.util.List;

public class ForgeKeybindHelper implements IKeybindHelper {
    public static final List<KeyMapping> PENDING = new ArrayList<>();

    @Override
    public KeyMapping create(InputConstants.Type type, int code, String name, String category) {
        KeyMapping km = new KeyMapping(name, type, code, category);
        PENDING.add(km);
        return km;
    }
}
