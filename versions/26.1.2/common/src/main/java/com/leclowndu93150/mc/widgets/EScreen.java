package com.leclowndu93150.mc.widgets;

import com.leclowndu93150.mc.widgets.utils.ColorGroups;
import com.leclowndu93150.mc.widgets.utils.Point;
import com.leclowndu93150.mc.widgets.utils.Rect;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
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
    public boolean keyPressed(KeyEvent event) {
        EWidget focus = (EWidget) getFocused();

        if (focus != null && focus.keyPressed(event)) return true;
        if (focus != null && event.key() == InputConstants.KEY_ESCAPE) {
            focus.focused(false);
            setFocused(null);
            return true;
        }
        if (event.key() == InputConstants.KEY_ESCAPE) {
            onClose();
            return true;
        }
        return false;
    }

    protected boolean onCharTyped(CharacterEvent event) {
        EWidget focus = (EWidget) getFocused();
        if (focus != null) return focus.charTyped(event);
        return false;
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        return onCharTyped(event);
    }

    @Override
    public void onClose() {
        if (minecraft != null) minecraft.setScreen(parent);
    }

    public boolean onMouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        clickState = true;
        double mx = event.x();
        double my = event.y();
        int button = event.button();

        EWidget focus = (EWidget) getFocused();
        setFocused(null);

        if (focus != null && hoveredWidget != focus) {
            focus.focused(false);
            focus.mouseClicked(event, doubleClick);
        }

        boolean ret = false;
        if (hoveredWidget != null) {
            hoveredWidget.focused(true);
            ret = hoveredWidget.mouseClicked(event, doubleClick);
            if (ret) setFocused(hoveredWidget);
        }

        setDragging(true);

        return onMouseClicked(mx, my, button) || ret;
    }

    public boolean onMouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        setDragging(false);
        if (!clickState) return false;
        clickState = false;

        EWidget focus = (EWidget) getFocused();

        boolean ret = false;
        if (hoveredWidget != focus && focus != null) {
            focus.mouseReleased(event);
        }
        if (hoveredWidget != null) {
            ret = hoveredWidget.mouseReleased(event);
        }

        return onMouseReleased(event.x(), event.y(), event.button()) || ret;
    }

    public boolean onMouseDragged(MouseButtonEvent event, double dx, double dy) {
        EWidget focus = (EWidget) getFocused();
        if (focus == null) return false;
        return focus.mouseDragged(event, dx, dy);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        return onMouseDragged(event, dx, dy);
    }

    public boolean onMouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (hoveredWidget == null) return false;
        return hoveredWidget.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        return onMouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    public List<EWidget> widgets() {
        return children().stream()
                .filter(EWidget.class::isInstance)
                .map(EWidget.class::cast)
                .toList();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
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
            graphics.setComponentTooltipForNextFrame(font, hoveredWidget.getTooltips(), mouseX, mouseY);
        }
    }

    protected void preRenderScreen(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    }

    protected void postRenderScreen(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    }

    public int eLeft() { return 0; }
    public int eTop() { return 0; }
    public int eRight() { return width; }
    public int eBottom() { return height; }

    public void drawOutline(GuiGraphicsExtractor graphics, int left, int top, int right, int bottom, int color) {
        graphics.horizontalLine(left, right, top, color);
        graphics.horizontalLine(left, right, bottom, color);
        graphics.verticalLine(left, top, bottom, color);
        graphics.verticalLine(right, top, bottom, color);
    }

    public void drawOutline(GuiGraphicsExtractor graphics, int color) {
        drawOutline(graphics, eLeft(), eTop(), eRight(), eBottom(), color);
    }

    public void drawOutline(GuiGraphicsExtractor graphics, Rect r, int color) {
        drawOutline(graphics, r.left(), r.top(), r.right(), r.bottom(), color);
    }

    public void drawBg(GuiGraphicsExtractor graphics, int left, int top, int right, int bottom, int color) {
        graphics.fill(left, top, right, bottom, color);
    }

    public void drawBg(GuiGraphicsExtractor graphics, int color) {
        drawBg(graphics, eLeft(), eTop(), eRight(), eBottom(), color);
    }
}
