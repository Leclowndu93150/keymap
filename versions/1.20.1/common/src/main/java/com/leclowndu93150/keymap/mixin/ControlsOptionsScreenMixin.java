package com.leclowndu93150.keymap.mixin;

import com.leclowndu93150.keymap.client.gui.screen.KeymapScreen;
import com.leclowndu93150.keymap.client.gui.screen.LayoutSelectionScreen;
import com.leclowndu93150.keymap.config.KeymapConfig;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.MouseSettingsScreen;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsScreen.class)
public abstract class ControlsOptionsScreenMixin extends OptionsSubScreen {

    public ControlsOptionsScreenMixin(Screen lastScreen, Options options, Component title) {
        super(lastScreen, options, title);
    }

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void redirectKeybindsButton(CallbackInfo ci) {
        int i = this.width / 2 - 155;
        int j = i + 160;
        int k = this.height / 6 - 12;
        this.addRenderableWidget(
            Button.builder(Component.translatable("options.mouse_settings"),
                button -> this.minecraft.setScreen(new MouseSettingsScreen(this, this.options)))
                .bounds(i, k, 150, 20).build()
        );
        this.addRenderableWidget(
            Button.builder(Component.translatable("controls.keybinds"),
                button -> {
                    Screen scr;
                    if (KeymapConfig.instance().firstOpenDone()) {
                        scr = new KeymapScreen((ControlsScreen)(Object) this);
                    } else {
                        scr = new LayoutSelectionScreen((ControlsScreen)(Object) this);
                    }
                    this.minecraft.setScreen(scr);
                }).bounds(j, k, 150, 20).build()
        );
        k += 24;
        this.addRenderableWidget(this.options.toggleCrouch().createButton(this.options, i, k, 150));
        this.addRenderableWidget(this.options.toggleSprint().createButton(this.options, j, k, 150));
        k += 24;
        this.addRenderableWidget(this.options.autoJump().createButton(this.options, i, k, 150));
        this.addRenderableWidget(this.options.operatorItemsTab().createButton(this.options, j, k, 150));
        ci.cancel();
    }
}
