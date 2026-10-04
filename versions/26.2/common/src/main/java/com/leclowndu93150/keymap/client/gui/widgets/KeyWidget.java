package com.leclowndu93150.keymap.client.gui.widgets;

import com.leclowndu93150.keymap.config.KeymapConfig;
import com.leclowndu93150.keymap.keys.extrakeybind.KeymapRegistry;
import com.leclowndu93150.keymap.keys.layout.KeyData;
import com.leclowndu93150.keymap.keys.sources.KeymappingNotifier;
import com.leclowndu93150.keymap.keys.wrappers.keys.KeyHolder;
import com.leclowndu93150.keymap.utils.Utils;
import com.leclowndu93150.mc.widgets.EWidget;
import com.leclowndu93150.mc.widgets.utils.ColorGroups;
import com.leclowndu93150.mc.widgets.utils.ColorSet;
import com.leclowndu93150.mc.widgets.utils.Styles;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class KeyWidget extends EWidget implements KeymappingNotifier.KeybindingRegistrySubscriber {
    protected KeyData key;
    protected InputConstants.Key mcKey;

    protected SimpleWidgetAction<KeyWidget> onClick;
    protected SpecialKeyWidgetAction onSpecialClick;

    protected boolean selected = false;

    protected Component text;
    protected boolean hasComplex = false;

    public KeyWidget(KeyData key, int x, int y, int w, int h) {
        super(x, y, w, h);
        _init(key);
    }

    public KeyData key() { return key; }
    public InputConstants.Key mcKey() { return mcKey; }
    public boolean selected() { return selected; }
    public KeyWidget selected(boolean v) { this.selected = v; return this; }
    public KeyWidget onClick(SimpleWidgetAction<KeyWidget> a) { this.onClick = a; return this; }
    public KeyWidget onSpecialClick(SpecialKeyWidgetAction a) { this.onSpecialClick = a; return this; }

    protected void _init(KeyData key) {
        this.key = key;
        this.text = Component.literal(key.name() == null ? "" : key.name());
        this.mcKey = getMCKey(key);
        this.tooltips = new ArrayList<>();
        this.tooltips.add(mcKey.getDisplayName());
        if (key.code() == -2) {
            this.allowRightClick = true;
            for (int i = 0; i < 10; i++) {
                KeymappingNotifier.subscribe(i, this);
            }
        } else {
            KeymappingNotifier.subscribe(key.code(), this);
        }
        updateTooltips();
    }

    public void destroy() {
        if (key.code() == -2) {
            for (int i = 0; i < 10; i++) {
                KeymappingNotifier.unsubscribe(i, this);
            }
        } else {
            KeymappingNotifier.unsubscribe(key.code(), this);
        }
    }

    public boolean isNormal() {
        return key.code() != -2;
    }

    public boolean updateTooltipForOtherMouseKeys() {
        tooltips.add(Component.literal(text.getString()).withStyle(Styles.header()));
        List<Component> boundKeys = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (KeymappingNotifier.keys().containsKey(i)) {
                for (KeyHolder k : KeymappingNotifier.keys().get(i)) {
                    boundKeys.add(Component.literal(
                            String.format("[%s] %s", i, k.getTranslatedName().getString())));
                }
            }
        }
        int size = boundKeys.size();

        this.color(ColorGroups.WHITE);
        if (size > 0) {
            this.color(ColorGroups.GREEN);
            tooltips.add(Component.literal(Utils.SEPARATOR).withStyle(Styles.muted()));
            tooltips.addAll(boundKeys);
        }
        if (selected) this.color(ColorGroups.YELLOW);
        return true;
    }

    public boolean updateSpecialTooltip() {
        if (key.code() == -2) {
            return updateTooltipForOtherMouseKeys();
        }
        return false;
    }

    protected void updateDebugTooltips() {
        if (KeymapConfig.instance().debug()) {
            tooltips.add(Component.literal(Utils.SEPARATOR).withStyle(Styles.muted()));
            tooltips.add(Component.literal(String.format("Code: %d", key.code())).withStyle(Styles.yellow()));
            tooltips.add(Component.literal(String.format("Mouse?: %b", key.mouse())).withStyle(Styles.yellow()));
            tooltips.add(Component.literal(String.format("Name: %s", key.name())).withStyle(Styles.yellow()));
        }
    }

    public void updateNormalTooltip() {
        tooltips.add(Component.literal(String.format("(%s) ", text.getString()))
                .withStyle(Styles.yellow())
                .append(Component.literal(mcKey.getDisplayName().getString()).withStyle(Styles.headerBold())));

        hasComplex = KeymapRegistry.containsKey(key.code());
        if (KeymappingNotifier.keys().containsKey(key.code())) {
            List<KeyHolder> holders = List.copyOf(KeymappingNotifier.keys().get(key.code()));

            int size = holders.size();
            switch (size) {
                case 0: color(ColorGroups.WHITE); break;
                case 1: color(ColorGroups.GREEN); break;
                default: color(ColorGroups.RED); break;
            }

            if (size > 0) {
                tooltips.add(Component.literal(Utils.SEPARATOR).withStyle(Styles.muted()));
                for (KeyHolder k : holders) {
                    tooltips.add(k.getTranslatedName());
                }
            }
        } else {
            color(ColorGroups.WHITE);
        }

        if (hasComplex) {
            List<KeyMapping> m = KeymapRegistry.getMappings(key.code());
            if (m.isEmpty() && tooltips.size() == 1)
                tooltips.add(Component.literal(Utils.SEPARATOR).withStyle(Styles.muted()));
            for (KeyMapping km : m) {
                tooltips.add(Component.translatable(km.getName()));
            }
        }
        if (selected) color(ColorGroups.YELLOW);
    }

    @Override
    public void updateTooltips() {
        if (tooltips == null) tooltips = new ArrayList<>();
        tooltips.clear();
        if (!updateSpecialTooltip()) {
            updateNormalTooltip();
        }
        updateDebugTooltips();
    }

    protected InputConstants.Key getMCKey(KeyData key) {
        if (key.mouse()) return InputConstants.Type.MOUSE.getOrCreate(key.code());
        return InputConstants.Type.KEYSYM.getOrCreate(key.code());
    }

    @Override
    public boolean onMouseReleased(boolean inside, double mouseX, double mouseY, int button) {
        if (isNormal()) {
            if (onClick != null) {
                onClick.run(this);
                return true;
            }
        } else {
            if (onSpecialClick != null) {
                onSpecialClick.run(this, button);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void renderWidget(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        ColorSet colors = colorVariant();
        drawBg(graphics, colors.bg());
        if (hasComplex) {
            graphics.fill(right() - 6, bottom() - 6, right(), bottom(), 0xFF_ffff00);
        }
        drawOutline(graphics, colors.border());
        graphics.centeredText(font, text, midX(), midY() - font.lineHeight / 2 + 1, colors.text());
    }

    @Override
    public void keybindingRegistryUpdated(boolean selected) {
        this.selected = selected;
        updateTooltips();
    }

    public interface SpecialKeyWidgetAction {
        void run(KeyWidget source, int button);
    }
}
