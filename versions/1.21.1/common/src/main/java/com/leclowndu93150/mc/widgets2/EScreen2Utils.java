package com.leclowndu93150.mc.widgets2;

import com.leclowndu93150.mc.widgets.utils.Point;
import com.leclowndu93150.mc.widgets.utils.Rect;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class EScreen2Utils extends Screen {
    protected final Point<Integer> margin = new Point<>(6);
    protected final Point<Integer> padding = new Point<>(6);
    protected int targetScreenWidth = -1;
    protected int minScreenWidth = 10;
    protected List<EWidget2> children = new ArrayList<>();

    private EWidget2 focusWidget;
    private EWidget2 hoverWidget;
    private EWidget2 activeWidget;

    protected Rect scr;
    protected Screen parent;

    public Point<Integer> margin() { return margin; }
    public Point<Integer> padding() { return padding; }
    public int targetScreenWidth() { return targetScreenWidth; }
    public int minScreenWidth() { return minScreenWidth; }
    public Rect scr() { return scr; }
    public Screen parent() { return parent; }

    @Nullable
    protected EWidget2 focusWidget() {
        if (focusWidget != null) {
            if (focusWidget.focused) return focusWidget;
            focusWidget(null);
        }
        return null;
    }

    @Nullable
    protected EWidget2 hoverWidget() {
        if (hoverWidget != null) {
            if (hoverWidget.hovered) return hoverWidget;
            hoverWidget(null);
        }
        return null;
    }

    @Nullable
    protected EWidget2 activeWidget() {
        if (activeWidget != null) {
            if (activeWidget.active) return activeWidget;
            activeWidget(null);
        }
        return null;
    }

    protected void focusWidget(EWidget2 w) {
        if (focusWidget != w && focusWidget != null) focusWidget.focused(false);
        focusWidget = w;
        if (w != null) w.focused(true);
    }

    protected void hoverWidget(EWidget2 w) {
        if (hoverWidget != w && hoverWidget != null) hoverWidget.hovered(false);
        hoverWidget = w;
        if (hoverWidget != null) hoverWidget.hovered(true);
    }

    protected void activeWidget(EWidget2 w) {
        if (activeWidget != w && activeWidget != null) activeWidget.active(false);
        activeWidget = w;
        if (w != null) w.active(true);
    }

    protected boolean clickState = false;

    protected EScreen2Utils(Component component) {
        super(component);
    }

    protected void drawOutline(GuiGraphics graphics, int l, int t, int r, int b, int c) {
        U.outline(graphics, l, t, r, b, c);
    }

    protected void drawOutline(GuiGraphics graphics, Rect r, int c) {
        drawOutline(graphics, r.left(), r.top(), r.right(), r.bottom(), c);
    }

    protected void drawOutline(GuiGraphics graphics, int c) {
        drawOutline(graphics, scr, c);
    }

    protected void drawOutsideOutline(GuiGraphics graphics, Rect r, int c) {
        drawOutline(graphics, r.left() - 1, r.top() - 1, r.right() - 1, r.bottom() - 1, c);
    }

    protected void drawOutsideOutline(GuiGraphics graphics, int c) {
        drawOutsideOutline(graphics, scr, c);
    }

    protected Rect scrFromWidth(int w) {
        if (w == -1) w = width;
        w = Math.max(Math.min(w, width - margin.x() * 2), minScreenWidth);
        return new Rect(
                Math.max((width - w) / 2, 0) + margin.x(), margin.y(), w - margin.x() * 2, height - margin.y() * 2);
    }

    protected Point<Integer> center() {
        return new Point<>((scr.left() + scr.right()) / 2, (scr.top() + scr.bottom()) / 2);
    }
}
