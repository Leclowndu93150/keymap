package com.leclowndu93150.keymap.keys.sources.category;

import com.leclowndu93150.keymap.keys.wrappers.categories.CategoryHolder;

import java.util.List;

public interface CategorySource {
    List<CategoryHolder> getCategoryHolders();
    boolean canUseSource();
}
