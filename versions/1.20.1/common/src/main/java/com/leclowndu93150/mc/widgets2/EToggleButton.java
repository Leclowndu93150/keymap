package com.leclowndu93150.mc.widgets2;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class EToggleButton extends EButton2<EToggleButton> implements EValueContainer<Boolean> {
    protected boolean value = false;

    EAction<EToggleButton> onToggle = null;

    public EToggleButton(Component text, int x, int y, int w, int h) {
        super(text, x, y, w, h);
        onClick(self -> {
            self.value(!self.value());
            if (onToggle != null) onToggle.run(this);
        });
    }

    public EAction<EToggleButton> onToggle() { return onToggle; }
    public EToggleButton onToggle(EAction<EToggleButton> a) { this.onToggle = a; return this; }

    @Override
    public Boolean value() { return value; }

    @Override
    public void value(Boolean v) { this.value = v; }

    @Override
    protected void onRenderWidget(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        graphics.drawCenteredString(
                font,
                Component.literal("").append(text).append(": ").append(Boolean.TRUE.equals(value()) ? "ON" : "OFF"),
                center().x(),
                center().y() - font.lineHeight / 2 + 1,
                tColor());
    }
}
