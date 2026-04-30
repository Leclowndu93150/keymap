package com.leclowndu93150.keymap.forge;

import com.leclowndu93150.keymap.Keymap;
import com.leclowndu93150.keymap.forge.cross.services.ForgeKeybindHelper;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Keymap.MOD_ID)
public class KeymapForge {
    public KeymapForge() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(KeymapForge::onRegisterKeyMappings);
        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLClientSetupEvent e) -> Keymap.init());
    }

    private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        ForgeKeybindHelper.PENDING.forEach(event::register);
        ForgeKeybindHelper.PENDING.clear();
    }
}
