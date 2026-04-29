package com.leclowndu93150.keymap.keys.layout;

public class KeyMeta {
    protected String author;
    protected String name;
    protected String code;

    public KeyMeta() {}
    public KeyMeta(String author, String name, String code) {
        this.author = author;
        this.name = name;
        this.code = code;
    }

    public String author() { return author; }
    public String name() { return name; }
    public String code() { return code; }
}
