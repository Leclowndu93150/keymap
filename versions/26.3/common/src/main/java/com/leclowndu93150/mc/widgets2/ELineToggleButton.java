package com.leclowndu93150.mc.widgets2;

import com.leclowndu93150.mc.widgets.utils.Rect;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class ELineToggleButton extends EToggleButton {
    protected Rect checkBox;

    public ELineToggleButton(Component text, int x, int y, int w, int h) {
        super(text, x, y, w, h);
    }

    @Override
    protected void init() {
        super.init();
        int boxS = rect.h() - padding.y() * 2;
        checkBox = new Rect(rect.right() - padding.x() - boxS, rect.top() + padding.y(), boxS, boxS);
    }

    @Override
    protected void preRenderWidget(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        U.bg(graphics, rect, ColorOption.baseBG.fromState(state()));
    }

    @Override
    protected void onRenderWidget(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.text(font, text, padding.x() + rect.left(), center().y() - font.lineHeight / 2 + 1, tColor());
        if (checkBox != null) {
            U.outline(graphics, checkBox, ColorOption.baseFG.fromState(state()));
            if (Boolean.TRUE.equals(value())) U.bg(graphics, checkBox, ColorOption.baseFG.base());
        }
    }
}
