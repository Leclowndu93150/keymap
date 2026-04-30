package com.leclowndu93150.keymap.fabric.cross.services;

import com.leclowndu93150.keymap.cross.services.IKeybindHelper;
import com.leclowndu93150.keymap.cross.services.IPlatformHelper;
import com.leclowndu93150.keymap.cross.services.ITickHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public String loader() { return "fabric"; }

    @Override
    public boolean modLoaded(String modid) { return FabricLoader.getInstance().isModLoaded(modid); }

    @Override
    public boolean dev() { return FabricLoader.getInstance().isDevelopmentEnvironment(); }

    @Override
    public File config(String file) { return FabricLoader.getInstance().getConfigDir().resolve(file).toFile(); }

    @Override
    public IKeybindHelper keybindHelper() { return new FabricKeybindHelper(); }

    @Override
    public ITickHelper tickHelper() { return new FabricTickHelper(); }
}
