package com.leclowndu93150.keymap.forge.cross.services;

import com.leclowndu93150.keymap.cross.services.IKeybindHelper;
import com.leclowndu93150.keymap.cross.services.IPlatformHelper;
import com.leclowndu93150.keymap.cross.services.ITickHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;

public class ForgePlatformHelper implements IPlatformHelper {
    @Override
    public String loader() { return "forge"; }

    @Override
    public boolean modLoaded(String modid) { return ModList.get() != null && ModList.get().isLoaded(modid); }

    @Override
    public boolean dev() { return !FMLLoader.isProduction(); }

    @Override
    public File config(String file) { return FMLPaths.CONFIGDIR.get().resolve(file).toFile(); }

    @Override
    public IKeybindHelper keybindHelper() { return new ForgeKeybindHelper(); }

    @Override
    public ITickHelper tickHelper() { return new ForgeTickHelper(); }
}
