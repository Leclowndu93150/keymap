package com.leclowndu93150.keymap.mixin;

import com.google.common.collect.BiMap;
import com.leclowndu93150.keymap.keys.extrakeybind.KeyComboData;
import com.leclowndu93150.keymap.keys.extrakeybind.KeymapRegistry;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Unique
    private KeyComboData keymap$lastValidCombo = null;

    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    private void keymap$onKeyPress(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
        if (windowPointer != minecraft.getWindow().getWindow()) return;
        if (minecraft.screen != null) return;

        BiMap<KeyComboData, KeyMapping> combos = KeymapRegistry.bindMap().inverse();
        KeyComboData kd = new KeyComboData(key, Screen.hasAltDown(), Screen.hasShiftDown(), Screen.hasControlDown());
        if (keymap$lastValidCombo != null
                && action == InputConstants.RELEASE
                && keymap$lastValidCombo.keyCode() == kd.keyCode()
                && combos.containsKey(keymap$lastValidCombo)) {
            KeyMapping k = combos.get(keymap$lastValidCombo);
            ((KeyMappingInvoker) (Object) k).keymap$setDownInvoker(false);
            keymap$lastValidCombo = null;
            ci.cancel();
            return;
        }
        if (kd.onlyKey()) return;
        if (combos.containsKey(kd)) {
            KeyMapping k = combos.get(kd);
            KeyMappingAccessor accessor = (KeyMappingAccessor) (Object) k;
            ((KeyMappingInvoker) (Object) k).keymap$setDownInvoker(action == InputConstants.PRESS || action == InputConstants.REPEAT);
            accessor.keymap$setClickCount(accessor.keymap$getClickCount() + 1);
            keymap$lastValidCombo = kd;
            ci.cancel();
        }
    }
}
