package com.leclowndu93150.keymap.neoforge.cross.services;

import com.leclowndu93150.keymap.cross.services.ITickHelper;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.ArrayList;
import java.util.List;

public class NeoForgeTickHelper implements ITickHelper {
    private static final List<EndTickListener> LISTENERS = new ArrayList<>();
    private static boolean registered = false;

    public NeoForgeTickHelper() {
        if (!registered) {
            NeoForge.EVENT_BUS.register(NeoForgeTickHelper.class);
            registered = true;
        }
    }

    @Override
    public void registerEndClientTick(EndTickListener listener) {
        LISTENERS.add(listener);
    }

    @SubscribeEvent
    public static void onPostTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        for (EndTickListener l : LISTENERS) {
            l.execute(mc);
        }
    }
}
