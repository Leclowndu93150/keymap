package com.leclowndu93150.keymap.neoforge.cross.services;

import com.leclowndu93150.keymap.cross.services.IKeybindHelper;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public class NeoForgeKeybindHelper implements IKeybindHelper {
    public static final List<KeyMapping> PENDING = new ArrayList<>();

    @Override
    public KeyMapping create(InputConstants.Type type, int code, String name, String category) {
        KeyMapping.Category cat;
        try {
            cat = KeyMapping.Category.register(Identifier.parse(category));
        } catch (Throwable ignored) {
            cat = KeyMapping.Category.MISC;
        }
        KeyMapping km = new KeyMapping(name, type, code, cat);
        PENDING.add(km);
        return km;
    }
}
