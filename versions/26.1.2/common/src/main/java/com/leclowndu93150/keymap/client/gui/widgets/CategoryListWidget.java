package com.leclowndu93150.keymap.client.gui.widgets;

import com.leclowndu93150.keymap.config.KeymapConfig;
import com.leclowndu93150.keymap.keys.wrappers.categories.CategoryHolder;
import com.leclowndu93150.keymap.utils.Utils;
import com.leclowndu93150.mc.widgets.EList;
import com.leclowndu93150.mc.widgets.utils.Rect;
import com.leclowndu93150.mc.widgets.utils.Styles;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class CategoryListWidget extends EList<CategoryListWidget.CategoryListEntry> {

    public CategoryListWidget(int itemHeight, int x, int y, int w, int h) {
        super(itemHeight, x, y, w, h);
    }

    protected CategoryListWidget(int itemHeight, Rect rect) {
        super(itemHeight, rect);
    }

    @Override
    public boolean onMouseReleased(boolean inside, double mouseX, double mouseY, int button) {
        boolean ret = super.onMouseReleased(inside, mouseX, mouseY, button);
        setItemSelected(null);
        setLastItemSelected(null);
        return ret;
    }

    public static class CategoryListEntry extends EListEntry<CategoryListEntry> {
        protected CategoryHolder category;
        protected Component keyString;

        public CategoryListEntry(CategoryHolder category, CategoryListWidget container) {
            super(container);
            this.category = category;
            this.keyString = this.category.getTranslatedName();
            updateTooltips();
        }

        public CategoryHolder category() { return category; }
        public Component keyString() { return keyString; }

        @Override
        public void updateTooltips() {
            tooltips.clear();
            tooltips.add(Component.literal(keyString.getString()).withStyle(Styles.headerBold()));
            tooltips.add(Component.literal(category.getModName()).withStyle(Styles.muted2()));

            if (KeymapConfig.instance().debug()) {
                tooltips.add(Component.literal(Utils.SEPARATOR).withStyle(Styles.muted()));
                tooltips.add(Component.literal(String.format("Search: %s", category.getFilterSlug()))
                        .withStyle(Styles.yellow()));
            }
        }

        @Override
        public void renderWidget(GuiGraphicsExtractor graphics, Rect r, float a) {
            String trimmed = font.substrByWidth(keyString, r.w()).getString();
            graphics.text(font, trimmed, r.x(), r.y(), getVariant().text());
        }
    }
}
