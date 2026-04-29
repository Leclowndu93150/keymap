package com.leclowndu93150.mc.widgets.utils;

public final class ColorGroup {
    private final ColorSet normal;
    private final ColorSet hover;
    private final ColorSet active;
    private final ColorSet disabled;

    public ColorGroup(ColorSet normal, ColorSet hover, ColorSet active, ColorSet disabled) {
        this.normal = normal;
        this.hover = hover;
        this.active = active;
        this.disabled = disabled;
    }

    public ColorGroup(int color) {
        this.normal = new ColorSet(color, ColorType.NORMAL);
        this.hover = new ColorSet(color, ColorType.HOVER);
        this.active = new ColorSet(color, ColorType.ACTIVE);
        this.disabled = new ColorSet(color, ColorType.DISABLED);
    }

    public ColorSet normal() { return normal; }
    public ColorSet hover() { return hover; }
    public ColorSet active() { return active; }
    public ColorSet disabled() { return disabled; }

    public ColorSet getVariant(boolean hover, boolean active, boolean disabled) {
        if (disabled) return this.disabled;
        if (active) return this.active;
        if (hover) return this.hover;
        return this.normal;
    }
}
