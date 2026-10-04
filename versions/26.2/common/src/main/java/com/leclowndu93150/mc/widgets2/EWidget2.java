package com.leclowndu93150.mc.widgets2;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.sounds.SoundEvents;

public abstract class EWidget2 extends EWidget2Utils {
    protected EWidget2(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    protected void init() {}

    protected void updateTooltips() {}

    protected boolean onMouseClicked(double mouseX, double mouseY, int button) {
        return true;
    }

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
    protected boolean onMouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) { return false; }

    protected void preRenderWidget(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {}
    protected abstract void onRenderWidget(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a);
    protected void postRenderWidget(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {}

    private boolean onEscape() { return false; }

    public boolean escape() { return onEscape(); }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (!rect.contains(event.x(), event.y())) return false;
        return onMouseClicked(event.x(), event.y(), event.button());
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        if (!active()) return false;
        if (!rect.contains(event.x(), event.y())) return false;
        return onMouseReleased(event.x(), event.y(), event.button());
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        return onMouseDragged(event.x(), event.y(), event.button(), dx, dy);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        return onMouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        return onKeyPressed(event.key(), event.scancode(), event.modifiers());
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        return onCharTyped((char) event.codepoint(), 0);
    }

    public boolean charTyped(char codePoint, int modifiers) {
        return onCharTyped(codePoint, modifiers);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        preRenderWidget(graphics, mouseX, mouseY, a);
        onRenderWidget(graphics, mouseX, mouseY, a);
        postRenderWidget(graphics, mouseX, mouseY, a);
    }
}
