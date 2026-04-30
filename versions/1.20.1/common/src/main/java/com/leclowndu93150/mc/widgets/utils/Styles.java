package com.leclowndu93150.mc.widgets.utils;

import net.minecraft.network.chat.Style;

public class Styles {
    private static final Style HEADER_BOLD = Style.EMPTY.withBold(true).withColor(0x00ff00).withItalic(true);
    private static final Style HEADER = Style.EMPTY.withColor(0x00ff00).withItalic(true);
    private static final Style MUTED = Style.EMPTY.withColor(0x555555);
    private static final Style MUTED2 = Style.EMPTY.withColor(0x888888);
    private static final Style NORMAL = Style.EMPTY;
    private static final Style RED = Style.EMPTY.withColor(0xff0000);
    private static final Style GREEN = Style.EMPTY.withColor(0x00ff00);
    private static final Style BLUE = Style.EMPTY.withColor(0x0000ff);
    private static final Style YELLOW = Style.EMPTY.withColor(0xffff00);
    private static final Style CYAN = Style.EMPTY.withColor(0x00ffff);
    private static final Style PURPLE = Style.EMPTY.withColor(0xff00ff);

    public static Style headerBold() { return HEADER_BOLD; }
    public static Style header() { return HEADER; }
    public static Style muted() { return MUTED; }
    public static Style muted2() { return MUTED2; }
    public static Style normal() { return NORMAL; }
    public static Style red() { return RED; }
    public static Style green() { return GREEN; }
    public static Style blue() { return BLUE; }
    public static Style yellow() { return YELLOW; }
    public static Style cyan() { return CYAN; }
    public static Style purple() { return PURPLE; }

    private Styles() {}
}
