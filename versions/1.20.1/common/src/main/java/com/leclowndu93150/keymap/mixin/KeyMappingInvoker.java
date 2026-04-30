package com.leclowndu93150.keymap.mixin;

import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(KeyMapping.class)
public interface KeyMappingInvoker {
    @Invoker("setDown")
    void keymap$setDownInvoker(boolean down);
}
