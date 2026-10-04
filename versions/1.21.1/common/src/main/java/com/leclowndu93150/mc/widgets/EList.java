package com.leclowndu93150.mc.widgets;

import com.leclowndu93150.mc.widgets.utils.ColorGroup;
import com.leclowndu93150.mc.widgets.utils.ColorSet;
import com.leclowndu93150.mc.widgets.utils.ColorType;
import com.leclowndu93150.mc.widgets.utils.Point;
import com.leclowndu93150.mc.widgets.utils.Rect;
import com.leclowndu93150.mc.widgets.utils.Tooltipped;
import com.leclowndu93150.mc.widgets.utils.WidgetUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class EList<T extends EList.EListEntry<T>> extends EWidget {
    protected final Point<Double> lastClick = new Point<>(0d);

    protected boolean dragging;
    protected int itemHeight;
    protected int scrollBarWidth = 6;
    protected int scrollSpeed = 8;
    protected List<T> items = new ArrayList<>();
    protected T itemHovered;
    protected T itemSelected;
    protected T lastItemSelected;

    protected Minecraft client;
    protected double scrollOffset = 0;
    protected double lastDrag;
    protected double lastScrollPos;
    protected boolean canDeselectItem = true;
    protected boolean lastClickWasInside = false;
    protected boolean didDrag = false;

    SimpleWidgetAction<EList<T>> onItemSelected;

    protected EList(int itemHeight, int x, int y, int w, int h) {
        super(x, y, w, h);
        _init(itemHeight);
    }

    protected EList(int itemHeight, Rect rect) {
        super(rect);
        _init(itemHeight);
    }

    public boolean dragging() { return dragging; }
    public int itemHeight() { return itemHeight; }
    public int scrollBarWidth() { return scrollBarWidth; }
    public int scrollSpeed() { return scrollSpeed; }
    public List<T> items() { return items; }
    public T itemHovered() { return itemHovered; }
    public T itemSelected() { return itemSelected; }
    public T lastItemSelected() { return lastItemSelected; }

    public EList<T> onItemSelected(SimpleWidgetAction<EList<T>> a) { this.onItemSelected = a; return this; }

    protected List<T> filteredItems() {
        return items;
    }

    public void setItemSelectedWithIndex(int i) {
        if (size() == 0) return;
        if (size() <= i) setItemSelected(items.get(0));
        else setItemSelected(items.get(i));
    }

    protected void setSelected(T i, boolean selected) {
        if (i != null) i.selected(selected);
    }

    public void setItemSelected(T t) {
        setLastItemSelected(itemSelected);
        setSelected(itemSelected, false);
        itemSelected = t;
        setSelected(itemSelected, true);
    }

    protected void setLastItemSelected(T t) {
        setSelected(lastItemSelected, false);
        lastItemSelected = t;
        setSelected(lastItemSelected, true);
    }

    public void updateFilteredList() {}

    public void addItem(T item) {
        if (items.contains(item)) return;
        items.add(item);
    }

    public void removeItem(T item) {
        items.remove(item);
    }

    public void clearItems() {
        items.clear();
        updateFilteredList();
    }

    public int size() {
        return filteredItems().size();
    }

    protected int scrollBarX() {
        return right() - scrollBarWidth;
    }

    protected T getHoveredItem(double mouseX, double mouseY) {
        int x = (int) mouseX;
        int y = (int) mouseY;

        if (!(x >= left() + padding.x() && x <= right() - padding.x())) return null;
        y -= top() + padding.y() - scrollOffset;
        int ix = y / itemHeight;
        if (ix < 0 || ix >= size()) return null;
        return filteredItems().get(ix);
    }

    protected int contentHeight() {
        return size() * itemHeight;
    }

    protected double maxScroll() {
        return Math.max(0, contentHeight() - (rect.h() - padding.y() * 2));
    }

    protected boolean inScrollbar(double mouseX, double mouseY) {
        return mouseY >= top() + padding.y()
                && mouseY <= bottom() - padding.y()
                && mouseX >= scrollBarX()
                && mouseX <= scrollBarWidth + scrollBarX();
    }

    public void setScrollPos(double pos) {
        scrollOffset = WidgetUtils.clamp(pos, 0, maxScroll());
    }

    public void relativeScrollPos(double pos) {
        scrollOffset = WidgetUtils.clamp(scrollOffset + pos, 0, maxScroll());
    }

    protected void _init(int itemHeight) {
        this.client = Minecraft.getInstance();
        this.itemHeight = itemHeight;
        this.allowRightClick = true;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        renderList(graphics, mouseX, mouseY, a);
        renderScrollBar(graphics);
    }

    protected void renderScrollBar(GuiGraphics graphics) {
        int ch = contentHeight();
        int eh = rect.h() - padding.y() * 2;

        if (ch == 0) return;

        double scroll = (float) eh / ch;
        if (scroll >= 1) return;

        int colScrollBg = 0x88_000000;
        int colScrollFg = 0x88_ffffff;

        int scrollTop = (int) (scrollOffset * scroll);
        int scrollHeight = (int) (eh * scroll);

        int scrollLeft = right() - padding.x();
        int padTop = top() + padding.y();
        int actualScrollTop = padTop + scrollTop;
        int scrollBottom = actualScrollTop + scrollHeight;

        graphics.fill(scrollLeft, padTop, right(), bottom() - padding.y(), colScrollBg);
        graphics.fill(scrollLeft, actualScrollTop, right(), scrollBottom, colScrollFg);
    }

    protected void renderList(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        if (itemHovered != null) itemHovered.hovered(false);
        itemHovered = getHoveredItem(mouseX, mouseY);
        if (itemHovered != null) itemHovered.hovered(true);

        graphics.enableScissor(left() + padding.x(), top() + padding.y(), right() - padding.x(), bottom() - padding.y());
        for (int i = 0; i < size(); i++) {
            T e = filteredItems().get(i);
            Rect r = new Rect(
                    left() + padding.x(),
                    top() + i * itemHeight + padding.y() - ((int) scrollOffset),
                    rect.w() - padding.x() * 2 - scrollBarWidth,
                    itemHeight);

            if (r.midY() > rect.bottom() - padding.y() || r.midY() < rect.top() + padding.y()) continue;
            e.render(graphics, r, a);
        }
        graphics.disableScissor();
    }

    @Override
    public @Nullable List<Component> getTooltips() {
        if (itemHovered != null) return itemHovered.getTooltips();
        return null;
    }

    public void sort() {}

    @Override
    public boolean onMouseReleased(boolean inside, double mouseX, double mouseY, int button) {
        if (didDrag) {
            didDrag = false;
            return false;
        }
        if (inside && button == 1) {
            setItemSelected(null);
            return true;
        }
        itemHovered = getHoveredItem(mouseX, mouseY);
        if (itemHovered == null && itemSelected != null) {
            if (canDeselectItem) setItemSelected(null);
            return false;
        }
        if (itemHovered == null) return false;

        if (!inside) return false;
        for (T item : items) {
            item.selected(false);
            item.hovered(false);
        }
        setItemSelected(itemHovered);
        if (itemSelected != null) {
            itemSelected.selected(true);
            if (onItemSelected != null) onItemSelected.run(this);
        }
        return true;
    }

    @Override
    protected boolean onMouseScrolled(double mouseX, double mouseY, double delta) {
        relativeScrollPos(-delta * scrollSpeed);
        return true;
    }

    @Override
    public EWidget focused(boolean focused) {
        if (!focused && itemSelected != null && canDeselectItem) {
            setItemSelected(null);
        }
        return super.focused(focused);
    }

    @Override
    public boolean onMouseClicked(boolean inside, double mouseX, double mouseY, int button) {
        lastClickWasInside = inside;
        lastClick.setXY(mouseX, mouseY);
        lastScrollPos = scrollOffset;
        if (!inside) {
            onMouseReleased(inside, mouseX, mouseY, button);
        }
        return getHoveredItem(mouseX, mouseY) != null;
    }

    @Override
    protected boolean onMouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (!lastClickWasInside) return false;
        didDrag = true;
        setScrollPos(lastScrollPos - (mouseY - lastClick.y()));
        return true;
    }

    public abstract static class EListEntry<T extends EListEntry<T>> implements Tooltipped {
        protected final Font font;
        protected final List<Component> tooltips = new ArrayList<>();

        protected boolean selected = false;
        protected boolean hovered = false;
        protected EList<T> container;

        protected ColorGroup color = new ColorGroup(
                new ColorSet(0xffffff, ColorType.NORMAL),
                new ColorSet(0xff3333, ColorType.HOVER),
                new ColorSet(0x00ff00, ColorType.ACTIVE),
                new ColorSet(0xffffff, ColorType.DISABLED));

        protected EListEntry(EList<T> container) {
            font = Minecraft.getInstance().font;
            this.container = container;
        }

        public boolean selected() { return selected; }
        public EListEntry<T> selected(boolean v) { this.selected = v; return this; }
        public boolean hovered() { return hovered; }
        public EListEntry<T> hovered(boolean v) { this.hovered = v; return this; }
        public EList<T> container() { return container; }

        protected ColorSet getVariant() {
            if (selected) return color.active();
            if (hovered) return color.hover();
            return color.normal();
        }

        @Override
        public List<Component> getTooltips() {
            return tooltips;
        }

        public void render(GuiGraphics graphics, Rect r, float a) {
            renderWidget(graphics, r, a);
        }

        public void updateTooltips() {}

        public void provideTooltips(List<Component> tips) {
            tooltips.clear();
            if (tips == null) return;
            tooltips.addAll(tips);
        }

        public abstract void renderWidget(GuiGraphics graphics, Rect r, float a);
    }
}
