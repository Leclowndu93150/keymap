package com.leclowndu93150.keymap.keys;

import com.mojang.blaze3d.platform.InputConstants;

public final class KeyCodes {
    public static final int MOUSE_OFFSET = 1000;
    public static final int UNBOUND = InputConstants.UNKNOWN.getValue();

    private KeyCodes() {}

    public static boolean isMouse(int code) {
        return code >= MOUSE_OFFSET;
    }

    public static int mouse(int button) {
        return MOUSE_OFFSET + button;
    }

    public static int of(InputConstants.Key key) {
        return key.getType() == InputConstants.Type.MOUSE ? mouse(key.getValue()) : key.getValue();
    }

    public static InputConstants.Key toKey(int code, boolean mouse) {
        if (mouse) return InputConstants.Type.MOUSE.getOrCreate(code - MOUSE_OFFSET);
        return InputConstants.Type.KEYBOARD.getOrCreate(code);
    }
}
