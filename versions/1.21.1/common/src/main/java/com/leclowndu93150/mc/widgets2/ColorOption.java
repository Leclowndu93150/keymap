package com.leclowndu93150.mc.widgets2;

public class ColorOption {
    public static final ColorOption baseBG = builder().build();
    public static final ColorOption baseFG = builder()
            .transparencyBase(0xFF << 24)
            .transparencyFocus(0xFF << 24)
            .transparencyHover(0xFF << 24)
            .transparencyActive(0xFF << 24)
            .transparencyDisabled(0xFF << 24)
            .build();

    protected int color = 0xFFFFFF;
    protected int transparencyBase = 0x00_000000;
    protected int transparencyHover = 0x33_000000;
    protected int transparencyActive = 0x55_000000;
    protected int transparencyFocus = 0x11_000000;
    protected int transparencyDisabled = 0x00_000000;

    public int color() { return color; }
    public ColorOption color(int v) { this.color = v; return this; }

    public int transparencyBase() { return transparencyBase; }
    public ColorOption transparencyBase(int v) { this.transparencyBase = v; return this; }
    public int transparencyHover() { return transparencyHover; }
    public ColorOption transparencyHover(int v) { this.transparencyHover = v; return this; }
    public int transparencyActive() { return transparencyActive; }
    public ColorOption transparencyActive(int v) { this.transparencyActive = v; return this; }
    public int transparencyFocus() { return transparencyFocus; }
    public ColorOption transparencyFocus(int v) { this.transparencyFocus = v; return this; }
    public int transparencyDisabled() { return transparencyDisabled; }
    public ColorOption transparencyDisabled(int v) { this.transparencyDisabled = v; return this; }

    public int base() { return transparencyBase | color; }
    public int hover() { return transparencyHover | color; }
    public int active() { return transparencyActive | color; }
    public int focus() { return transparencyFocus | color; }
    public int disabled() { return transparencyDisabled | color; }

    public int fromState(WidgetState s) {
        switch (s) {
            case DISABLED: return disabled();
            case ACTIVE: return active();
            case HOVER: return hover();
            case FOCUS: return focus();
            default: return base();
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ColorOption co = new ColorOption();
        public Builder color(int v) { co.color = v; return this; }
        public Builder transparencyBase(int v) { co.transparencyBase = v; return this; }
        public Builder transparencyHover(int v) { co.transparencyHover = v; return this; }
        public Builder transparencyActive(int v) { co.transparencyActive = v; return this; }
        public Builder transparencyFocus(int v) { co.transparencyFocus = v; return this; }
        public Builder transparencyDisabled(int v) { co.transparencyDisabled = v; return this; }
        public ColorOption build() { return co; }
    }
}
