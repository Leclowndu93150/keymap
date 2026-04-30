package com.leclowndu93150.keymap.forge.cross.services;

import com.leclowndu93150.keymap.cross.services.ITickHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeTickHelper implements ITickHelper {
    @Override
    public void registerEndClientTick(EndTickListener listener) {
        MinecraftForge.EVENT_BUS.register(new Object() {
            @SubscribeEvent
            public void onClientTick(TickEvent.ClientTickEvent event) {
                if (event.phase == TickEvent.Phase.END) {
                    listener.execute(Minecraft.getInstance());
                }
            }
        });
    }
}
