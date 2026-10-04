package com.leclowndu93150.keymap.fabric.cross.services;

import com.leclowndu93150.keymap.cross.services.ITickHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class FabricTickHelper implements ITickHelper {
    @Override
    public void registerEndClientTick(EndTickListener listener) {
        ClientTickEvents.END_CLIENT_TICK.register(listener::execute);
    }
}
