package com.leclowndu93150.keymap.keys.layout;

import java.util.List;

public class KeyRow {
    protected List<KeyData> row;

    public KeyRow() {}

    public KeyRow(List<KeyData> row) { this.row = row; }

    public List<KeyData> row() { return row; }
}
