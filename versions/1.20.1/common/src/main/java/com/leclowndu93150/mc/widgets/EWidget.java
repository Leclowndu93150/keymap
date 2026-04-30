package com.leclowndu93150.mc.widgets;

import com.leclowndu93150.mc.widgets.utils.ColorGroup;
import com.leclowndu93150.mc.widgets.utils.ColorGroups;
import com.leclowndu93150.mc.widgets.utils.ColorSet;
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
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;
import java.util.List;

public abstract class EWidget implements Renderable, GuiEventListener, NarratableEntry, Tooltipped {
    protected Font font = Minecraft.getInstance().font;

    protected ColorGroup color = ColorGroups.WHITE;
    protected Rect rect;
    protected boolean visible = true;
    protected boolean enabled = true;
    protected boolean focused = false;
    protected boolean active = false;
    protected boolean hovered = false;
    protected boolean allowRightClick = false;
    protected List<Component> tooltips;
    protected Point<Integer> padding = new Point<>(4);

    protected EWidget(int x, int y, int w, int h) {
        this.rect = new Rect(x, y, w, h);
    }

    protected EWidget(Rect rect) {
        this.rect = rect;
    }

    public ColorGroup color() { return color; }
    public EWidget color(ColorGroup color) { this.color = color; return this; }

    public Rect rect() { return rect; }
    public EWidget rect(Rect rect) { this.rect = rect; return this; }

    public boolean visible() { return visible; }
    public EWidget visible(boolean v) { this.visible = v; return this; }

    public boolean enabled() { return enabled; }
    public EWidget enabled(boolean e) { this.enabled = e; return this; }

    public boolean focused() { return focused; }
    public EWidget focused(boolean f) { this.focused = f; return this; }

    public boolean active() { return active; }
    public boolean hovered() { return hovered; }
    public boolean allowRightClick() { return allowRightClick; }

    public List<Component> tooltips() { return tooltips; }
    public EWidget tooltips(List<Component> t) { this.tooltips = t; return this; }

    public Point<Integer> padding() { return padding; }
    public EWidget padding(Point<Integer> p) { this.padding = p; return this; }

    @Override
    public List<Component> getTooltips() {
        return tooltips;
    }

    public void setTooltip(Component tip) {
        if (tooltips == null) tooltips = new ArrayList<>();
        tooltips.clear();
        tooltips.add(tip);
    }

    protected boolean onCharTyped(char codePoint, int modifiers) {
        return false;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (enabled() && visible() && focused()) {
            return onCharTyped(codePoint, modifiers);
        }
        return false;
    }

    protected void init() {}

    protected ColorSet colorVariant() {
        return color.getVariant(hovered, active, !enabled);
    }

    @Override
    public void setFocused(boolean focused) {
        focused(focused);
    }

    @Override
    public boolean isFocused() {
        return focused();
    }

    public void extractRenderState(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        if (!visible) return;
        hovered = isMouseOver(mouseX, mouseY);
        renderWidget(graphics, mouseX, mouseY, a);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        extractRenderState(graphics, mouseX, mouseY, a);
    }

    public void updateTooltips() {}

    public boolean onMouseClicked(boolean inside, double mouseX, double mouseY, int button) {
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        hovered = isMouseOver(mouseX, mouseY);
        if (!hovered) {
            active = false;
            onMouseClicked(false, mouseX, mouseY, button);
            return false;
        }
        if (!allowRightClick && button != 0) return false;
        playSound(SoundEvents.UI_BUTTON_CLICK.value());
        this.active = true;
        return onMouseClicked(true, mouseX, mouseY, button);
    }

    public boolean onMouseReleased(boolean inside, double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.active = false;
        hovered = isMouseOver(mouseX, mouseY);
        if (!hovered) {
            onMouseReleased(false, mouseX, mouseY, button);
            return false;
        }
        if (!allowRightClick && button != 0) return false;
        return onMouseReleased(true, mouseX, mouseY, button);
    }

    protected boolean onKeyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!focused) return false;
        return onKeyPressed(keyCode, scanCode, modifiers);
    }

    protected boolean onMouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (!enabled) return false;
        return onMouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    protected boolean onMouseScrolled(double mouseX, double mouseY, double delta) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (!enabled) return false;
        return onMouseScrolled(mouseX, mouseY, delta);
    }

    protected abstract void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float a);

    public int top() { return rect.top(); }
    public int bottom() { return rect.bottom(); }
    public int left() { return rect.left(); }
    public int right() { return rect.right(); }
    public int midX() { return rect.midX(); }
    public int midY() { return rect.midY(); }

    public void drawOutline(GuiGraphics graphics, int left, int top, int right, int bottom, int color) {
        graphics.hLine(left, right, top, color);
        graphics.hLine(left, right, bottom, color);
        graphics.vLine(left, top, bottom, color);
        graphics.vLine(right, top, bottom, color);
    }

    public void drawOutline(GuiGraphics graphics, int color) {
        drawOutline(graphics, left(), top(), right(), bottom(), color);
    }

    public void drawOutline(GuiGraphics graphics) {
        drawOutline(graphics, colorVariant().border());
    }

    public void drawBg(GuiGraphics graphics, int left, int top, int right, int bottom, int color) {
        graphics.fill(left, top, right, bottom, color);
    }

    public void drawBg(GuiGraphics graphics, int color) {
        drawBg(graphics, left(), top(), right(), bottom(), color);
    }

    public void drawBg(GuiGraphics graphics) {
        drawBg(graphics, colorVariant().bg());
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return rect.contains(mouseX, mouseY);
    }

    protected void playSound(SoundEvent sound, float pitch, float volume) {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(sound, pitch, volume));
    }

    protected void playSound(SoundEvent sound, float pitch) {
        playSound(sound, pitch, 0.25f);
    }

    protected void playSound(SoundEvent sound) {
        playSound(sound, 1f);
    }

    @Override
    public NarrationPriority narrationPriority() {
        if (focused) return NarrationPriority.FOCUSED;
        if (hovered) return NarrationPriority.HOVERED;
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {}

    public interface SimpleWidgetAction<T> {
        void run(T source);
    }
}
