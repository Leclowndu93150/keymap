package com.leclowndu93150.mc.widgets;

import com.leclowndu93150.mc.widgets.utils.Rect;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class ELabel extends EWidget {
    Component text;
    boolean centerX;
    boolean centerY;

    public ELabel(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public ELabel(Component text, int x, int y, int w, int h) {
        super(x, y, w, h);
        this.text = text;
    }

    public ELabel(Rect rect) {
        super(rect);
    }

    public ELabel(Component text, Rect rect) {
        super(rect);
        this.text = text;
    }

    public Component text() { return text; }
    public ELabel text(Component t) { this.text = t; return this; }
    public boolean centerX() { return centerX; }
    public ELabel centerX(boolean v) { this.centerX = v; return this; }
    public boolean centerY() { return centerY; }
    public ELabel centerY(boolean v) { this.centerY = v; return this; }

    public void center(boolean x, boolean y) {
        this.centerX = x;
        this.centerY = y;
    }

    public void center(boolean xy) {
        this.centerX = xy;
        this.centerY = xy;
    }

    @Override
    protected void renderWidget(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        if (text == null) return;
        int x = centerX ? midX() : left();
        int y = centerY ? midY() - font.lineHeight / 2 : top();
        if (centerX) {
            graphics.centeredText(font, text, x, y, colorVariant().text());
        } else {
            graphics.text(font, text, x, y, colorVariant().text());
        }
    }
}
