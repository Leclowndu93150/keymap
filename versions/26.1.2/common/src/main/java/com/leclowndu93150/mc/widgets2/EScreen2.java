package com.leclowndu93150.mc.widgets2;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public abstract class EScreen2 extends EScreen2Utils {

    protected EScreen2(Screen parent) {
        this(parent, Component.translatable("SCREEN"));
    }

    protected EScreen2(Screen parent, Component text) {
        super(text);
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        int tw = targetScreenWidth == -1 ? width : targetScreenWidth;
        scr = scrFromWidth(Math.min(tw, width));
        children.clear();

        onInit();

        for (EWidget2 child : children) {
            child.init();
        }
    }

    protected abstract void onInit();

    protected boolean onKeyPressed(int keyCode, int scanCode, int modifiers) { return false; }
    protected boolean onCharTyped(char codePoint, int modifiers) { return false; }
    protected boolean onMouseClicked(double mouseX, double mouseY, int button) { return false; }

    protected boolean onMouseReleased(double mouseX, double mouseY, int button) {
        switch (button) {
            case 0: return onLeftMouseReleased(mouseX, mouseY, button);
            case 1: return onRightMouseReleased(mouseX, mouseY, button);
            case 2: return onMiddleMouseReleased(mouseX, mouseY, button);
            default: return onOtherMouseReleased(mouseX, mouseY, button);
        }
    }

    protected boolean onLeftMouseReleased(double mouseX, double mouseY, int button) { return false; }
    protected boolean onMiddleMouseReleased(double mouseX, double mouseY, int button) { return false; }
    protected boolean onRightMouseReleased(double mouseX, double mouseY, int button) { return false; }
    protected boolean onOtherMouseReleased(double mouseX, double mouseY, int button) { return false; }
    protected boolean onMouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) { return false; }
    protected boolean onMouseScrolled(double mouseX, double mouseY, double delta) { return false; }

    protected void preRender(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    }

    protected void onRender(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        hoverWidget(null);
        for (EWidget2 w : children) {
            if (w.rect.contains(mouseX, mouseY)) hoverWidget(w);
            w.extractRenderState(graphics, mouseX, mouseY, a);
        }
    }

    protected void postRender(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {}

    @Override
    public void onClose() {
        if (minecraft != null) minecraft.setScreen(parent);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (focusWidget() != null && focusWidget().keyPressed(event)) return true;

        if (event.key() == InputConstants.KEY_ESCAPE) {
            boolean r = false;
            if (focusWidget() != null) {
                if (focusWidget().escape()) r = true;
                focusWidget(null);
            }
            if (!r) onClose();
            return true;
        }

        return onKeyPressed(event.key(), event.scancode(), event.modifiers());
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        if (focusWidget() != null && focusWidget().charTyped(event)) return true;
        return onCharTyped((char) event.codepoint(), 0);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        clickState = true;

        if (focusWidget() != null && hoverWidget() != focusWidget()) {
            focusWidget().mouseClicked(event, doubleClick);
            focusWidget(null);
        }

        if (hoverWidget() != null && hoverWidget().mouseClicked(event, doubleClick)) {
            activeWidget(hoverWidget());
            focusWidget(hoverWidget());
            return true;
        }

        return onMouseClicked(event.x(), event.y(), event.button());
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        if (!clickState) return false;
        clickState = false;

        EWidget2 active = activeWidget();
        boolean r = false;

        if (hoverWidget() != active && active != null) {
            active.mouseReleased(event);
            focusWidget(null);
        } else if (hoverWidget() != null && hoverWidget().mouseReleased(event)) {
            r = true;
        }

        activeWidget(null);

        return r || onMouseReleased(event.x(), event.y(), event.button());
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        if (!clickState) return false;
        return onMouseDragged(event.x(), event.y(), event.button(), dx, dy);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        return onMouseScrolled(mouseX, mouseY, scrollY);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        preRender(graphics, mouseX, mouseY, a);
        onRender(graphics, mouseX, mouseY, a);
        postRender(graphics, mouseX, mouseY, a);
    }
}
