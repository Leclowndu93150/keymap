package com.leclowndu93150.mc.widgets2;

import com.leclowndu93150.mc.widgets.utils.Rect;
import net.minecraft.client.gui.GuiGraphics;

public class U {
    public enum Mouse {
        LEFT, RIGHT, MIDDLE
    }

    public static void hl(GuiGraphics graphics, int l, int r, int t, int c) {
        if (l > r) { int x = l; l = r; r = x; }
        graphics.fill(l, t, r, t + 1, c);
    }

    public static void vl(GuiGraphics graphics, int t, int b, int l, int c) {
        if (t > b) { int x = t; t = b; b = x; }
        graphics.fill(l, t, l + 1, b, c);
    }

    public static void outline(GuiGraphics graphics, int l, int t, int r, int b, int c) {
        hl(graphics, l, r + 1, t, c);
        hl(graphics, l, r + 1, b, c);
        vl(graphics, t + 1, b, l, c);
        vl(graphics, t + 1, b, r, c);
    }

    public static void outline(GuiGraphics graphics, Rect r, int c) {
        outline(graphics, r.left(), r.top(), r.right(), r.bottom(), c);
    }

    public static void bg(GuiGraphics graphics, int l, int t, int r, int b, int c) {
        graphics.fill(l, t, r, b, c);
    }

    public static void bg(GuiGraphics graphics, Rect r, int c) {
        bg(graphics, r.left(), r.top(), r.right(), r.bottom(), c);
    }

    public static void bbg(GuiGraphics graphics, Rect r, int bg, int fg) {
        bg(graphics, r, bg);
        outline(graphics, r, fg);
    }

    public static void bbg(GuiGraphics graphics, Rect r, ColorOption bg, ColorOption fg, WidgetState s) {
        bbg(graphics, r, bg.fromState(s), fg.fromState(s));
    }
}
