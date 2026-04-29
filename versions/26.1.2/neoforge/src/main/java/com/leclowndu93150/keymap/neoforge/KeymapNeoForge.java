package com.leclowndu93150.keymap.neoforge;

import com.leclowndu93150.keymap.Keymap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@Mod(value = Keymap.MOD_ID, dist = Dist.CLIENT)
public class KeymapNeoForge {
    public KeymapNeoForge(IEventBus modBus) {
        modBus.addListener(KeymapNeoForge::onRegisterKeyMappings);
        modBus.addListener((FMLClientSetupEvent e) -> Keymap.init());
    }

    private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        com.leclowndu93150.keymap.neoforge.cross.services.NeoForgeKeybindHelper.PENDING.forEach(event::register);
        com.leclowndu93150.keymap.neoforge.cross.services.NeoForgeKeybindHelper.PENDING.clear();
    }
}
