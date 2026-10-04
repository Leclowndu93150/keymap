package com.leclowndu93150.keymap.keys.wrappers.categories;

import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;

import java.util.List;

public class VanillaCategory implements CategoryHolder {
    public static final List<String> MC_CATEGORIES = List.of(
            "key.category.minecraft.movement",
            "key.category.minecraft.misc",
            "key.category.minecraft.multiplayer",
            "key.category.minecraft.gameplay",
            "key.category.minecraft.ui",
            "key.category.minecraft.inventory",
            "key.category.minecraft.creative",
            "key.category.minecraft.spectator",
            "key.category.minecraft.debug");

    protected final String category;
    protected final Component translatedName;

    public VanillaCategory(String category) {
        this.category = category;
        this.translatedName = Component.translatable(category);
    }

    @Override
    public String getTranslatableName() {
        return category;
    }

    @Override
    public Component getTranslatedName() {
        return translatedName;
    }

    @Override
    public String getModName() {
        String s = category;
        if (MC_CATEGORIES.contains(category)) s = "advancements.story.root.title";
        return Language.getInstance().getOrDefault(s);
    }

    @Override
    public String getFilterSlug() {
        return String.format("@%s", Language.getInstance().getOrDefault(category));
    }
}
