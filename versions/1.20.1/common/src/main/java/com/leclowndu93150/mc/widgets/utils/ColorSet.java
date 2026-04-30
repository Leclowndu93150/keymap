package com.leclowndu93150.mc.widgets.utils;

import java.util.Objects;

public final class ColorSet {
    private final int text;
    private final int bg;
    private final int border;

    public ColorSet(int text, int bg, int border) {
        this.text = text;
        this.bg = bg;
        this.border = border;
    }

    public ColorSet(int color, ColorType colorType) {
        int base = color & 0x00_ffffff;
        int cText = 0xff_000000 | base;
        int cBorder = 0xff_000000 | base;
        int cBg = 0x55_000000 | base;
        switch (colorType) {
            case HOVER -> cBg = 0x88_000000 | base;
            case ACTIVE -> {
                cBg = 0xff_000000 | base;
                cText = 0xff_000000 | returnDarkerColor(base);
            }
            case DISABLED -> {
                cText = 0x88_000000 | base;
                cBorder = 0x88_000000 | base;
                cBg = 0x33_000000 | base;
            }
            default -> { /* NORMAL keeps initial values */ }
        }

        this.text = cText;
        this.border = cBorder;
        this.bg = cBg;
    }

    public int text() { return text; }
    public int bg() { return bg; }
    public int border() { return border; }

    private static int returnDarkerColor(int color) {
        float ratio = 1.0f - 0.2f;
        int a = (color >> 24) & 0xFF;
        int r = (int) (((color >> 16) & 0xFF) * ratio);
        int g = (int) (((color >> 8) & 0xFF) * ratio);
        int b = (int) ((color & 0xFF) * ratio);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColorSet c)) return false;
        return text == c.text && bg == c.bg && border == c.border;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, bg, border);
    }
}
