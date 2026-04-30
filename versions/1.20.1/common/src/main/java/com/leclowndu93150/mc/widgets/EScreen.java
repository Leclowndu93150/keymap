package com.leclowndu93150.mc.widgets;

import com.leclowndu93150.mc.widgets.utils.ColorGroups;
import com.leclowndu93150.mc.widgets.utils.Point;
import com.leclowndu93150.mc.widgets.utils.Rect;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public abstract class EScreen extends Screen {
    protected final Point<Integer> margin = new Point<>(6);
    protected final Point<Integer> padding = new Point<>(4);

    protected boolean autoRenderChild = true;
    protected boolean clickState = false;
    protected boolean renderBg = true;
    protected EWidget hoveredWidget = null;
    protected ELabel debugFocus;
    protected ELabel debugHover;
    protected Rect scr;

    Screen parent;

    protected EScreen(Screen parent, Component text) {
        super(text);
        this.parent = parent;
    }

    public Screen parent() { return parent; }

    @Override
    protected void init() {
        debugFocus = new ELabel(Component.literal("focused"), 0, 4, width, font.lineHeight);
        debugHover = new ELabel(Component.literal("hovered"), 0, 14, width, font.lineHeight);
        debugFocus.color(ColorGroups.WHITE);
        debugHover.color(ColorGroups.WHITE);
        debugFocus.center(true);
        debugHover.center(true);

        onInit();
    }

    protected Rect scrFromWidth(int w) {
        return new Rect(
                Math.max((width - w) / 2, 0) + margin.x(), margin.y(), w - margin.x() * 2, height - margin.y() * 2);
    }

    protected abstract void onInit();

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        EWidget focus = (EWidget) getFocused();

        if (focus != null && focus.keyPressed(keyCode, scanCode, modifiers)) return true;
        if (focus != null && keyCode == InputConstants.KEY_ESCAPE) {
            focus.focused(false);
            setFocused(null);
            return true;
        }
        if (keyCode == InputConstants.KEY_ESCAPE) {
            onClose();
            return true;
        }
        return false;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        EWidget focus = (EWidget) getFocused();
        if (focus != null) return focus.charTyped(codePoint, modifiers);
        return false;
    }

    @Override
    public void onClose() {
        if (minecraft != null) minecraft.setScreen(parent);
    }

    public boolean onMouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        clickState = true;

        EWidget focus = (EWidget) getFocused();
        setFocused(null);

        if (focus != null && hoveredWidget != focus) {
            focus.focused(false);
            focus.mouseClicked(mouseX, mouseY, button);
        }

        boolean ret = false;
        if (hoveredWidget != null) {
            hoveredWidget.focused(true);
            ret = hoveredWidget.mouseClicked(mouseX, mouseY, button);
            if (ret) setFocused(hoveredWidget);
        }

        setDragging(true);

        return onMouseClicked(mouseX, mouseY, button) || ret;
    }

    public boolean onMouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        setDragging(false);
        if (!clickState) return false;
        clickState = false;

        EWidget focus = (EWidget) getFocused();

        boolean ret = false;
        if (hoveredWidget != focus && focus != null) {
            focus.mouseReleased(mouseX, mouseY, button);
        }
        if (hoveredWidget != null) {
            ret = hoveredWidget.mouseReleased(mouseX, mouseY, button);
        }

        return onMouseReleased(mouseX, mouseY, button) || ret;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        EWidget focus = (EWidget) getFocused();
        if (focus == null) return false;
        return focus.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (hoveredWidget == null) return false;
        return hoveredWidget.mouseScrolled(mouseX, mouseY, delta);
    }

    public List<EWidget> widgets() {
        return children().stream()
                .filter(EWidget.class::isInstance)
                .map(EWidget.class::cast)
                .toList();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        preRenderScreen(graphics, mouseX, mouseY, a);
        hoveredWidget = null;
        if (autoRenderChild) {
            for (EWidget d : widgets()) {
                d.extractRenderState(graphics, mouseX, mouseY, a);
                if (d.rect().contains(mouseX, mouseY)) {
                    hoveredWidget = d;
                }
            }
        }
        postRenderScreen(graphics, mouseX, mouseY, a);

        if (hoveredWidget != null && hoveredWidget.getTooltips() != null && !hoveredWidget.getTooltips().isEmpty()) {
            graphics.renderComponentTooltip(font, hoveredWidget.getTooltips(), mouseX, mouseY);
        }
    }

    protected void preRenderScreen(GuiGraphics graphics, int mouseX, int mouseY, float a) {}

    protected void postRenderScreen(GuiGraphics graphics, int mouseX, int mouseY, float a) {}

    public int eLeft() { return 0; }
    public int eTop() { return 0; }
    public int eRight() { return width; }
    public int eBottom() { return height; }

    public void drawOutline(GuiGraphics graphics, int left, int top, int right, int bottom, int color) {
        graphics.hLine(left, right, top, color);
        graphics.hLine(left, right, bottom, color);
        graphics.vLine(left, top, bottom, color);
        graphics.vLine(right, top, bottom, color);
    }

    public void drawOutline(GuiGraphics graphics, int color) {
        drawOutline(graphics, eLeft(), eTop(), eRight(), eBottom(), color);
    }

    public void drawOutline(GuiGraphics graphics, Rect r, int color) {
        drawOutline(graphics, r.left(), r.top(), r.right(), r.bottom(), color);
    }

    public void drawBg(GuiGraphics graphics, int left, int top, int right, int bottom, int color) {
        graphics.fill(left, top, right, bottom, color);
    }

    public void drawBg(GuiGraphics graphics, int color) {
        drawBg(graphics, eLeft(), eTop(), eRight(), eBottom(), color);
    }
}
