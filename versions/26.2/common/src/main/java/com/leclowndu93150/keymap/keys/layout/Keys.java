package com.leclowndu93150.keymap.keys.layout;

import java.util.ArrayList;
import java.util.List;

public class Keys {
    protected List<KeyRow> basic = new ArrayList<>();
    protected List<KeyRow> mouse = new ArrayList<>();
    protected List<KeyRow> extra = new ArrayList<>();
    protected List<KeyRow> numpad = new ArrayList<>();

    public List<KeyRow> basic() { return basic; }
    public List<KeyRow> mouse() { return mouse; }
    public List<KeyRow> extra() { return extra; }
    public List<KeyRow> numpad() { return numpad; }
}
