package com.github.einjerjar.mc.keymapforge.cross.services;

import com.github.einjerjar.mc.keymap.cross.services.IKeybindHelper;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class ForgeKeybindHelper implements IKeybindHelper {

    public static final List<KeyMapping> pendingKeybinds = new ArrayList<>();

    @Override
    public KeyMapping create(InputConstants.Type type, int code, String name, String category) {
        KeyMapping keyMapping = new KeyMapping(name, type, code, category);

        Minecraft mc = Minecraft.getInstance();
        if (mc != null && mc.options != null) {
            mc.options.keyMappings = ArrayUtils.add(mc.options.keyMappings, keyMapping);
        } else {
            pendingKeybinds.add(keyMapping);
        }

        return keyMapping;
    }

    public static void registerPendingKeybinds() {
        if (!pendingKeybinds.isEmpty()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc != null && mc.options != null) {
                for (KeyMapping keyMapping : pendingKeybinds) {
                    mc.options.keyMappings = ArrayUtils.add(mc.options.keyMappings, keyMapping);
                }
                pendingKeybinds.clear();
            }
        }
    }
}