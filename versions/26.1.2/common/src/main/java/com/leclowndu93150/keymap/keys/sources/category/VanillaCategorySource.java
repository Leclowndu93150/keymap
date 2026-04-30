package com.leclowndu93150.keymap.keys.sources.category;

import com.leclowndu93150.keymap.keys.wrappers.categories.CategoryHolder;
import com.leclowndu93150.keymap.keys.wrappers.categories.VanillaCategory;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public class VanillaCategorySource implements CategorySource {

    @Override
    public List<CategoryHolder> getCategoryHolders() {
        List<String> categories = new ArrayList<>();
        for (KeyMapping km : Minecraft.getInstance().options.keyMappings) {
            Identifier id = km.getCategory().id();
            String cat = id.toLanguageKey("key.category");
            if (!categories.contains(cat)) categories.add(cat);
        }

        List<CategoryHolder> categoryHolders = new ArrayList<>();
        for (String category : categories) {
            categoryHolders.add(new VanillaCategory(category));
        }

        return categoryHolders;
    }

    @Override
    public boolean canUseSource() {
        return true;
    }
}
