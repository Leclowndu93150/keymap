package com.leclowndu93150.keymap.mixin;

import com.leclowndu93150.keymap.keys.extrakeybind.KeymapRegistry;
import com.leclowndu93150.keymap.keys.layout.KeyLayout;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(method = "init", at = @At("HEAD"))
    private void keymap$onInit(CallbackInfo ci) {
        try { KeyLayout.loadKeys(); } catch (Exception ignored) {}
        KeymapRegistry.load();
    }
}
