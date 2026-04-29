package com.leclowndu93150.keymap.mixin;

import com.leclowndu93150.keymap.client.gui.screen.KeymapScreen;
import com.leclowndu93150.keymap.client.gui.screen.LayoutSelectionScreen;
import com.leclowndu93150.keymap.config.KeymapConfig;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.MouseSettingsScreen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ControlsScreen.class)
public abstract class ControlsOptionsScreenMixin extends OptionsSubScreen {

    public ControlsOptionsScreenMixin(Screen lastScreen, Options options, Component title) {
        super(lastScreen, options, title);
    }

    /**
     * @author Leclowndu93150
     * @reason Replace keybinds screen with custom keymap screen
     */
    @Overwrite
    public void addOptions() {
        this.list.addSmall(
                Button.builder(Component.translatable("options.mouse_settings"),
                        button -> this.minecraft.setScreen(new MouseSettingsScreen(this, this.options))).build(),
                Button.builder(Component.translatable("controls.keybinds"),
                        button -> {
                            Screen scr;
                            if (KeymapConfig.instance().firstOpenDone()) {
                                scr = new KeymapScreen((ControlsScreen) (Object) this);
                            } else {
                                scr = new LayoutSelectionScreen((ControlsScreen) (Object) this);
                            }
                            this.minecraft.setScreen(scr);
                        }).build()
        );
        this.list.addSmall(
                this.options.toggleCrouch(),
                this.options.toggleSprint(),
                this.options.toggleAttack(),
                this.options.toggleUse(),
                this.options.autoJump(),
                this.options.sprintWindow(),
                this.options.operatorItemsTab());
    }
}
