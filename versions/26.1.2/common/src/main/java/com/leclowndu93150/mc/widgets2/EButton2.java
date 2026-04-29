package com.leclowndu93150.mc.widgets2;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class EButton2<T extends EButton2<T>> extends EWidget2 {
    protected Component text;
    protected EAction<T> onClick = null;
    protected EAction<T> onRightClick = null;

    public EButton2(Component text, int x, int y, int w, int h) {
        super(x, y, w, h);
        this.text(text);
    }

    public Component text() { return text; }
    @SuppressWarnings("unchecked")
    public T text(Component t) { this.text = t; return (T) this; }
    @SuppressWarnings("unchecked")
    public T onClick(EAction<T> a) { this.onClick = a; return (T) this; }
    @SuppressWarnings("unchecked")
    public T onRightClick(EAction<T> a) { this.onRightClick = a; return (T) this; }

    @Override
    @SuppressWarnings("unchecked")
    protected boolean onLeftMouseReleased(double mouseX, double mouseY, int button) {
        if (onClick != null) {
            onClick.run((T) this);
            return true;
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected boolean onRightMouseReleased(double mouseX, double mouseY, int button) {
        if (onRightClick != null) {
            onRightClick.run((T) this);
            return true;
        }
        return false;
    }

    @Override
    protected void preRenderWidget(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        U.bbg(graphics, rect, ColorOption.baseBG, ColorOption.baseFG, state());
    }

    @Override
    protected void onRenderWidget(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.centeredText(font, text, center().x(), center().y() - font.lineHeight / 2 + 1, tColor());
    }
}
