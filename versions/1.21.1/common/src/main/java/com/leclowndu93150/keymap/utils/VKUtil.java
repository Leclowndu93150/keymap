package com.leclowndu93150.keymap.utils;

import com.leclowndu93150.keymap.client.gui.widgets.VirtualKeyboardWidget;
import com.leclowndu93150.keymap.keys.layout.KeyLayout;
import com.leclowndu93150.mc.widgets.EWidget;

import java.util.List;

public class VKUtil {
    private VKUtil() {}

    public static List<VirtualKeyboardWidget> genLayout(KeyLayout layout, int x, int y) {
        return genLayout(layout, x, y, null, null);
    }

    public static List<VirtualKeyboardWidget> genLayout(
            KeyLayout layout,
            int x,
            int y,
            EWidget.SimpleWidgetAction<VirtualKeyboardWidget> onClick,
            VirtualKeyboardWidget.SpecialVKKeyClicked onSpecialClick) {
        VirtualKeyboardWidget vkBasic = new VirtualKeyboardWidget(layout.keys().basic(), x, y, 0, 0);
        VirtualKeyboardWidget vkExtra =
                new VirtualKeyboardWidget(layout.keys().extra(), vkBasic.left(), vkBasic.bottom() + 4, 0, 0);
        VirtualKeyboardWidget vkMouse =
                new VirtualKeyboardWidget(layout.keys().mouse(), vkExtra.left(), vkExtra.bottom() + 2, 0, 0);
        VirtualKeyboardWidget vkNumpad =
                new VirtualKeyboardWidget(layout.keys().numpad(), vkMouse.right() + 4, vkBasic.bottom() + 4, 0, 0);

        List<VirtualKeyboardWidget> vks = List.of(vkBasic, vkExtra, vkMouse, vkNumpad);

        for (VirtualKeyboardWidget vk : vks) {
            vk.onKeyClicked(onClick);
            vk.onSpecialKeyClicked(onSpecialClick);
        }

        return vks;
    }
}
