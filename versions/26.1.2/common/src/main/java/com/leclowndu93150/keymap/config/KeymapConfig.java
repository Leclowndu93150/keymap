package com.leclowndu93150.keymap.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leclowndu93150.keymap.Keymap;
import com.leclowndu93150.keymap.cross.Services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class KeymapConfig {
    static KeymapConfig instance;
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static File cfgFile = null;

    protected boolean firstOpenDone = false;
    protected boolean autoSelectLayout = false;
    protected String customLayout = "en_us";
    protected boolean replaceKeybindScreen = true;
    protected boolean malilibSupport = true;
    protected boolean debug = false;
    protected boolean debug2 = false;
    protected boolean crashOnProblematicError = false;
    protected boolean showHelpTooltips = true;

    public boolean firstOpenDone() { return firstOpenDone; }
    public KeymapConfig firstOpenDone(boolean v) { this.firstOpenDone = v; return this; }
    public boolean autoSelectLayout() { return autoSelectLayout; }
    public KeymapConfig autoSelectLayout(boolean v) { this.autoSelectLayout = v; return this; }
    public String customLayout() { return customLayout; }
    public KeymapConfig customLayout(String v) { this.customLayout = v; return this; }
    public boolean replaceKeybindScreen() { return replaceKeybindScreen; }
    public KeymapConfig replaceKeybindScreen(boolean v) { this.replaceKeybindScreen = v; return this; }
    public boolean malilibSupport() { return malilibSupport; }
    public KeymapConfig malilibSupport(boolean v) { this.malilibSupport = v; return this; }
    public boolean debug() { return debug; }
    public KeymapConfig debug(boolean v) { this.debug = v; return this; }
    public boolean debug2() { return debug2; }
    public KeymapConfig debug2(boolean v) { this.debug2 = v; return this; }
    public boolean crashOnProblematicError() { return crashOnProblematicError; }
    public KeymapConfig crashOnProblematicError(boolean v) { this.crashOnProblematicError = v; return this; }
    public boolean showHelpTooltips() { return showHelpTooltips; }
    public KeymapConfig showHelpTooltips(boolean v) { this.showHelpTooltips = v; return this; }

    private static synchronized File cfgFile() {
        if (cfgFile == null) cfgFile = Services.PLATFORM.config("keymap.json");
        return cfgFile;
    }

    public static synchronized KeymapConfig instance() {
        if (instance == null) instance = new KeymapConfig();
        return instance;
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(cfgFile())) {
            gson.toJson(instance(), writer);
        } catch (Exception e) {
            Keymap.logger().error("!! Cant save config !!", e);
        }
    }

    public static void load() {
        try (FileReader reader = new FileReader(cfgFile())) {
            instance = gson.fromJson(reader, KeymapConfig.class);
        } catch (FileNotFoundException e) {
            Keymap.logger().warn("!! Config not found, using default settings !!");
            save();
        } catch (Exception e) {
            Keymap.logger().warn("!! Cant read config, using default settings !!", e);
            save();
        }
    }

    @FunctionalInterface
    public interface KeymapConfigDirProvider {
        File execute(String name);
    }
}
