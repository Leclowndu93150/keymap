package com.leclowndu93150.mc.widgets2;

import com.leclowndu93150.mc.widgets.utils.Point;
import com.leclowndu93150.mc.widgets.utils.Rect;
import com.leclowndu93150.mc.widgets.utils.Tooltipped;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class EWidget2Utils implements Renderable, GuiEventListener, NarratableEntry, Tooltipped {
    protected final Font font = Minecraft.getInstance().font;

    protected Rect rect;
    protected boolean visible = true;
    protected boolean enabled = true;
    protected boolean focused = false;
    protected boolean active = false;
    protected boolean hovered = false;
    protected boolean focusable = false;
    protected int color = 0xFFFFFF;
    protected int transparency = 0xFF_000000;
    protected List<Component> tooltips = new ArrayList<>();
    protected Point<Integer> padding = new Point<>(0);

    protected EWidget2Utils(int x, int y, int w, int h) {
        this.rect = new Rect(x, y, w, h);
    }

    protected EWidget2Utils(Rect rect) {
        this.rect = rect;
    }

    public Rect rect() { return rect; }
    public EWidget2Utils rect(Rect r) { this.rect = r; return this; }
    public boolean visible() { return visible; }
    public EWidget2Utils visible(boolean v) { this.visible = v; return this; }
    public boolean enabled() { return enabled; }
    public EWidget2Utils enabled(boolean v) { this.enabled = v; return this; }
    public boolean focused() { return focused; }
    public EWidget2Utils focused(boolean v) { this.focused = v; return this; }
    public boolean active() { return active; }
    public EWidget2Utils active(boolean v) { this.active = v; return this; }
    public boolean hovered() { return hovered; }
    public EWidget2Utils hovered(boolean v) { this.hovered = v; return this; }
    public boolean focusable() { return focusable; }
    public EWidget2Utils focusable(boolean v) { this.focusable = v; return this; }
    public int color() { return color; }
    public EWidget2Utils color(int v) { this.color = v; return this; }
    public int transparency() { return transparency; }
    public EWidget2Utils transparency(int v) { this.transparency = v; return this; }
    public List<Component> tooltips() { return tooltips; }
    public EWidget2Utils tooltips(List<Component> t) { this.tooltips = t; return this; }
    public Point<Integer> padding() { return padding; }
    public EWidget2Utils padding(Point<Integer> p) { this.padding = p; return this; }

    protected int tColor(int t) { return t | color; }
    protected int tColor() { return tColor(transparency); }

    protected void drawOutline(GuiGraphics graphics, int l, int t, int r, int b, int c) {
        U.outline(graphics, l, t, r, b, c);
    }

    protected void drawOutline(GuiGraphics graphics, Rect r, int c) {
        drawOutline(graphics, r.left(), r.top(), r.right(), r.bottom(), c);
    }

    protected void drawOutline(GuiGraphics graphics, int c) {
        drawOutline(graphics, rect, c);
    }

    protected Point<Integer> center() {
        return new Point<>((rect.left() + rect.right()) / 2, (rect.top() + rect.bottom()) / 2);
    }

    @Override
    public void setFocused(boolean focused) { focused(focused); }

    @Override
    public boolean isFocused() { return focused(); }

    public WidgetState state() {
        if (!enabled()) return WidgetState.DISABLED;
        if (active()) return WidgetState.ACTIVE;
        if (hovered()) return WidgetState.HOVER;
        if (focused()) return WidgetState.FOCUS;
        return WidgetState.BASE;
    }

    protected void playSound(SoundEvent sound, float pitch, float volume) {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(sound, pitch, volume));
    }

    protected void playSound(SoundEvent sound, float pitch) { playSound(sound, pitch, 0.25f); }
    protected void playSound(SoundEvent sound) { playSound(sound, 1f); }

    @Override
    public List<Component> getTooltips() { return tooltips; }

    public void setTooltip(Component tip) {
        tooltips.clear();
        tooltips.add(tip);
    }

    public void setTooltips(List<Component> tips) { tooltips = tips; }

    @Override
    public NarrationPriority narrationPriority() { return NarrationPriority.NONE; }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {}
}
