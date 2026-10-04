package com.leclowndu93150.keymap.fabric.cross.services;

import com.leclowndu93150.keymap.cross.services.IKeybindHelper;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public class FabricKeybindHelper implements IKeybindHelper {
    @Override
    public KeyMapping create(InputConstants.Type type, int code, String name, String category) {
        KeyMapping.Category cat;
        try {
            cat = KeyMapping.Category.register(Identifier.parse(category));
        } catch (Throwable ignored) {
            cat = KeyMapping.Category.MISC;
        }
        KeyMapping km = new KeyMapping(name, type, code, cat);
        KeyMappingHelper.registerKeyMapping(km);
        return km;
    }
}
