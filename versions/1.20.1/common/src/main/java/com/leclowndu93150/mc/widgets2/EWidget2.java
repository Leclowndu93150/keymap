package com.leclowndu93150.mc.widgets2;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.sounds.SoundEvents;

public abstract class EWidget2 extends EWidget2Utils {
    protected EWidget2(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    protected void init() {}
    protected void updateTooltips() {}

    protected boolean onMouseClicked(double mouseX, double mouseY, int button) { return true; }

    private boolean handleMouseClick(double mouseX, double mouseY, int button) {
        switch (button) {
            case 0: return onLeftMouseReleased(mouseX, mouseY, button);
            case 1: return onRightMouseReleased(mouseX, mouseY, button);
            case 2: return onMiddleMouseReleased(mouseX, mouseY, button);
            default: return onOtherMouseReleased(mouseX, mouseY, button);
        }
    }

    protected boolean onMouseReleased(double mouseX, double mouseY, int button) {
        if (handleMouseClick(mouseX, mouseY, button)) {
            playSound(SoundEvents.UI_BUTTON_CLICK.value());
            return true;
        }
        return false;
    }

    protected boolean onLeftMouseReleased(double mouseX, double mouseY, int button) { return false; }
    protected boolean onMiddleMouseReleased(double mouseX, double mouseY, int button) { return false; }
    protected boolean onRightMouseReleased(double mouseX, double mouseY, int button) { return false; }
    protected boolean onOtherMouseReleased(double mouseX, double mouseY, int button) { return false; }
    protected boolean onKeyPressed(int keyCode, int scanCode, int modifiers) { return false; }
    protected boolean onCharTyped(char codePoint, int modifiers) { return false; }
    protected boolean onMouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) { return false; }
    protected boolean onMouseScrolled(double mouseX, double mouseY, double delta) { return false; }

    protected void preRenderWidget(GuiGraphics graphics, int mouseX, int mouseY, float a) {}
    protected abstract void onRenderWidget(GuiGraphics graphics, int mouseX, int mouseY, float a);
    protected void postRenderWidget(GuiGraphics graphics, int mouseX, int mouseY, float a) {}

    private boolean onEscape() { return false; }
    public boolean escape() { return onEscape(); }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!rect.contains(mouseX, mouseY)) return false;
        return onMouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (!active()) return false;
        if (!rect.contains(mouseX, mouseY)) return false;
        return onMouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return onMouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        return onMouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return onKeyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return onCharTyped(codePoint, modifiers);
    }

    public void extractRenderState(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        preRenderWidget(graphics, mouseX, mouseY, a);
        onRenderWidget(graphics, mouseX, mouseY, a);
        postRenderWidget(graphics, mouseX, mouseY, a);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        extractRenderState(graphics, mouseX, mouseY, a);
    }
}
