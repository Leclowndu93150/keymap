package com.leclowndu93150.keymap.neoforge.cross.services;

import com.leclowndu93150.keymap.cross.services.IKeybindHelper;
import com.leclowndu93150.keymap.cross.services.IPlatformHelper;
import com.leclowndu93150.keymap.cross.services.ITickHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;

public class NeoForgePlatformHelper implements IPlatformHelper {
    @Override
    public String loader() {
        return "neoforge";
    }

    @Override
    public boolean modLoaded(String modid) {
        return ModList.get() != null && ModList.get().isLoaded(modid);
    }

    @Override
    public boolean dev() {
        return !FMLLoader.getCurrent().isProduction();
    }

    @Override
    public File config(String file) {
        return FMLPaths.CONFIGDIR.get().resolve(file).toFile();
    }

    @Override
    public IKeybindHelper keybindHelper() {
        return new NeoForgeKeybindHelper();
    }

    @Override
    public ITickHelper tickHelper() {
        return new NeoForgeTickHelper();
    }
}
