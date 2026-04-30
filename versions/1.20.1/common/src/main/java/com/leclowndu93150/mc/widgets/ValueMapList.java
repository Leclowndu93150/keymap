package com.leclowndu93150.mc.widgets;

import com.leclowndu93150.mc.widgets.utils.Rect;
import net.minecraft.client.gui.GuiGraphics;

import java.util.Objects;

public class ValueMapList extends EList<ValueMapList.ValueMapEntry<?>> {
    public ValueMapList(int itemHeight, int x, int y, int w, int h, boolean canDeselectItem) {
        super(itemHeight, x, y, w, h);
        this.canDeselectItem = canDeselectItem;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        super.renderWidget(graphics, mouseX, mouseY, a);
        drawOutline(graphics, 0xff_ffffff);
    }

    public void setItemSelectedWithKey(String key) {
        for (ValueMapEntry<?> filteredItem : filteredItems()) {
            if (Objects.equals(filteredItem.key, key)) {
                setItemSelected(filteredItem);
                return;
            }
        }
    }

    public <T> void setItemSelectedWithValue(T value) {
        for (ValueMapEntry<?> filteredItem : filteredItems()) {
            if (Objects.equals(filteredItem.value, value)) {
                setItemSelected(filteredItem);
                return;
            }
        }
    }

    public static class ValueMapEntry<V> extends EListEntry<ValueMapEntry<?>> {
        protected String key;
        protected V value;

        public ValueMapEntry(String key, V value, ValueMapList container) {
            super(container);
            this.key = key;
            this.value = value;
        }

        public String key() { return key; }
        public V value() { return value; }

        @Override
        public void renderWidget(GuiGraphics graphics, Rect r, float a) {
            String trimmed = font.plainSubstrByWidth(key, r.w());
            graphics.drawString(font, trimmed, r.x(), r.y(), getVariant().text());
        }
    }
}
