package com.leclowndu93150.keymap.fabric.cross.services;

import com.leclowndu93150.keymap.cross.services.IKeybindHelper;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

public class FabricKeybindHelper implements IKeybindHelper {
    @Override
    public KeyMapping create(InputConstants.Type type, int code, String name, String category) {
        KeyMapping km = new KeyMapping(name, type, code, category);
        KeyBindingHelper.registerKeyBinding(km);
        return km;
    }
}
