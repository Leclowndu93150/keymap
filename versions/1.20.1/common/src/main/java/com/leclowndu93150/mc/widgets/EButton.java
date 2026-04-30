package com.leclowndu93150.mc.widgets;

import com.leclowndu93150.mc.widgets.utils.ColorSet;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class EButton extends EWidget {
    Component text;
    SimpleWidgetAction<EWidget> clickAction;

    public EButton(Component text, int x, int y, int w, int h) {
        super(x, y, w, h);
        this.text = text;
    }

    public Component text() { return text; }
    public EButton text(Component text) { this.text = text; return this; }

    public EButton clickAction(SimpleWidgetAction<EWidget> action) { this.clickAction = action; return this; }

    @Override
    public void setTooltip(Component tip) {
        super.setTooltip(tip);
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        ColorSet colors = colorVariant();
        drawBg(graphics, colors.bg());
        drawOutline(graphics, colors.border());
        graphics.drawCenteredString(font, text, midX(), midY() - font.lineHeight / 2 + 1, colors.text());
    }

    @Override
    public boolean onMouseReleased(boolean inside, double mouseX, double mouseY, int button) {
        if (clickAction != null) {
            clickAction.run(this);
            return true;
        }
        return false;
    }
}
