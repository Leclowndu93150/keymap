package com.leclowndu93150.mc.widgets2;

import com.leclowndu93150.mc.widgets.utils.Point;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class ELabel2 extends EWidget2 {
    protected Component text;
    protected Point<Boolean> centered = new Point<>(true);

    public ELabel2(Component text, int x, int y, int w, int h) {
        super(x, y, w, h);
        this.text(text);
        focusable(false);
    }

    public Component text() { return text; }
    public ELabel2 text(Component t) { this.text = t; return this; }
    public Point<Boolean> centered() { return centered; }
    public ELabel2 centered(Point<Boolean> c) { this.centered = c; return this; }

    @Override
    protected void onRenderWidget(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        int y = (centered.y() ? center().y() - font.lineHeight / 2 : rect().top()) + 1;
        int lColor = tColor();

        if (centered.x()) {
            graphics.drawCenteredString(font, text, center().x(), y, lColor);
        } else {
            graphics.drawString(font, text, rect().left(), y, lColor);
        }
    }
}
