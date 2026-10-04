package com.leclowndu93150.keymap.keys.wrappers.keys;

import com.leclowndu93150.keymap.keys.extrakeybind.KeyComboData;
import com.leclowndu93150.keymap.keys.extrakeybind.KeymapRegistry;
import com.leclowndu93150.keymap.keys.wrappers.categories.VanillaCategory;
import com.leclowndu93150.keymap.mixin.KeyMappingAccessor;
import com.leclowndu93150.keymap.utils.Utils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VanillaKeymap implements KeyHolder {
    protected final List<Integer> codes = new ArrayList<>();
    protected KeyMapping map;
    protected Component translatedName;
    protected Component translatedKey;
    protected boolean complex;
    protected String searchString;

    public VanillaKeymap(KeyMapping map) {
        this.map = map;
        this.translatedName = Component.translatable(map.getName());
        updateProps(currentKey());
    }

    public KeyMapping map() { return map; }

    @Override
    public List<Integer> getCode() {
        return codes;
    }

    @Override
    public Integer getSingleCode() {
        return codes.get(0);
    }

    @Override
    public Integer getKeyHash() {
        return Objects.hash(currentKey().getValue());
    }

    @Override
    public boolean isComplex() {
        return complex;
    }

    @Override
    public KeyComboData getComplexCode() {
        return null;
    }

    @Override
    public String getTranslatableName() {
        return map.getName();
    }

    @Override
    public String getCategory() {
        return map.getCategory();
    }

    @Override
    public Component getTranslatedName() {
        return translatedName;
    }

    @Override
    public String getTranslatableKey() {
        return currentKey().getName();
    }

    @Override
    public Component getTranslatedKey() {
        return translatedKey;
    }

    @Override
    public String getSearchString() {
        return searchString;
    }

    @Override
    public boolean setKey(List<Integer> keys, boolean mouse) {
        if (keys == null || keys.isEmpty()) return false;
        InputConstants.Type type = mouse ? InputConstants.Type.MOUSE : InputConstants.Type.KEYSYM;
        InputConstants.Key key = type.getOrCreate(keys.get(0));
        updateProps(key);
        KeyMapping.resetMapping();
        return true;
    }

    @Override
    public String getModName() {
        String s = getCategory();
        if (VanillaCategory.MC_CATEGORIES.contains(getCategory())) s = "advancements.story.root.title";
        return Language.getInstance().getOrDefault(s);
    }

    @Override
    public boolean resetKey() {
        updateProps(map.getDefaultKey());
        return true;
    }

    @Override
    public boolean isAssigned() {
        return currentKey().getValue() != -1 || KeymapRegistry.contains(map);
    }

    private InputConstants.Key currentKey() {
        return ((KeyMappingAccessor)(Object) map).keymap$getKey();
    }

    protected String searchableKey() {
        if (KeymapRegistry.bindMap().containsKey(map)) {
            KeyComboData k = KeymapRegistry.bindMap().get(map);
            return k.searchString();
        }
        return translatedKey.getString();
    }

    protected void updateSearchString() {
        String cat = Language.getInstance().getOrDefault(getCategory());
        searchString = String.format(
                        "%s [%s] $%s {%s} #%s (%s) @%s",
                        translatedName.getString(),
                        searchableKey(),
                        Utils.slugify(searchableKey()),
                        getModName(),
                        getModName(),
                        cat,
                        cat)
                .toLowerCase();
    }

    public void updateProps(InputConstants.Key key) {
        map.setKey(key);
        codes.clear();
        codes.add(key.getValue());

        if (KeymapRegistry.bindMap().containsKey(map)) {
            KeyComboData k = KeymapRegistry.bindMap().get(map);
            translatedKey = Component.literal(k.toKeyString());
        } else {
            translatedKey = key.getDisplayName();
        }
        updateSearchString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VanillaKeymap that = (VanillaKeymap) o;
        return map.equals(that.map) && codes.equals(that.codes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map, codes);
    }
}
