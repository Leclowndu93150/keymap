package com.leclowndu93150.keymap.keys.layout;

public class KeyData {
    protected int code;
    protected String name;
    protected boolean enabled;
    protected boolean mouse;
    protected int width;
    protected int height;

    public KeyData() {}

    public KeyData(int code, String name, boolean enabled, boolean mouse, int width, int height) {
        this.code = code;
        this.name = name;
        this.enabled = enabled;
        this.mouse = mouse;
        this.width = width;
        this.height = height;
    }

    public int code() { return code; }
    public String name() { return name; }
    public boolean enabled() { return enabled; }
    public boolean mouse() { return mouse; }
    public int width() { return width; }
    public int height() { return height; }
    public KeyData mouse(boolean m) { this.mouse = m; return this; }
}
