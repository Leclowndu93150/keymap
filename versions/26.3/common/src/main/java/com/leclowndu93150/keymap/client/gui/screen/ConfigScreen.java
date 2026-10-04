package com.leclowndu93150.keymap.client.gui.screen;

import com.leclowndu93150.keymap.config.KeymapConfig;
import com.leclowndu93150.mc.widgets.utils.Point;
import com.leclowndu93150.mc.widgets2.ELabel2;
import com.leclowndu93150.mc.widgets2.ELineToggleButton;
import com.leclowndu93150.mc.widgets2.EScreen2;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class ConfigScreen extends EScreen2 {

    public ConfigScreen(Screen parent) {
        super(parent);
        targetScreenWidth = 450;
    }

    @Override
    protected void onInit() {
        children.clear();
        Style bold = Style.EMPTY.withBold(true);

        int h = font.lineHeight + 8;

        ELabel2 labelGeneral = new ELabel2(
                Component.translatable("keymap.catGeneral").withStyle(bold), scr.left(), scr.top(), scr.w(), h);
        ELineToggleButton toggleReplaceKeybind = new ELineToggleButton(
                Component.translatable("keymap.optReplaceKeybindScreen"), scr.left(), scr.top() + h, scr.w(), h);

        toggleReplaceKeybind.padding(new Point<>(4));
        toggleReplaceKeybind.value(KeymapConfig.instance().replaceKeybindScreen());
        toggleReplaceKeybind.onToggle(self -> KeymapConfig.instance().replaceKeybindScreen(self.value()));

        ELabel2 labelLayout = new ELabel2(
                Component.translatable("keymap.catLayout").withStyle(bold), scr.left(), scr.top() + h * 2, scr.w(), h);
        ELineToggleButton toggleAutoSelectLayout = new ELineToggleButton(
                Component.translatable("keymap.optAutoSelectLayout"), scr.left(), scr.top() + h * 3, scr.w(), h);

        toggleAutoSelectLayout.padding(new Point<>(4));
        toggleAutoSelectLayout.value(KeymapConfig.instance().autoSelectLayout());
        toggleAutoSelectLayout.onToggle(self -> KeymapConfig.instance().autoSelectLayout(self.value()));

        ELabel2 labelTooltips = new ELabel2(
                Component.translatable("keymap.catTooltips").withStyle(bold),
                scr.left(),
                scr.top() + h * 4,
                scr.w(),
                h);
        ELineToggleButton toggleShowHelpTooltips = new ELineToggleButton(
                Component.translatable("keymap.optShowHelpTooltips"), scr.left(), scr.top() + h * 5, scr.w(), h);

        toggleShowHelpTooltips.padding(new Point<>(4));
        toggleShowHelpTooltips.value(KeymapConfig.instance().showHelpTooltips());
        toggleShowHelpTooltips.onToggle(self -> KeymapConfig.instance().showHelpTooltips(self.value()));

        ELabel2 labelExtra = new ELabel2(
                Component.translatable("keymap.catExtra").withStyle(bold), scr.left(), scr.top() + h * 6, scr.w(), h);
        ELineToggleButton toggleFirstOpenDone = new ELineToggleButton(
                Component.translatable("keymap.optFirstOpenDoneExtra"), scr.left(), scr.top() + h * 7, scr.w(), h);
        ELineToggleButton toggleDebug = new ELineToggleButton(
                Component.translatable("keymap.optDebug"), scr.left(), scr.top() + h * 8, scr.w(), h);
        ELineToggleButton toggleDebug2 = new ELineToggleButton(
                Component.translatable("keymap.optDebug2"), scr.left(), scr.top() + h * 9, scr.w(), h);
        ELineToggleButton toggleMad = new ELineToggleButton(
                Component.translatable("keymap.optCrashOnProblematicError"),
                scr.left(),
                scr.top() + h * 10,
                scr.w(),
                h);

        toggleFirstOpenDone.padding(new Point<>(4));
        toggleFirstOpenDone.value(KeymapConfig.instance().firstOpenDone());
        toggleFirstOpenDone.onToggle(self -> KeymapConfig.instance().firstOpenDone(self.value()));

        toggleDebug.padding(new Point<>(4));
        toggleDebug.value(KeymapConfig.instance().debug());
        toggleDebug.onToggle(self -> KeymapConfig.instance().debug(self.value()));

        toggleDebug2.padding(new Point<>(4));
        toggleDebug2.value(KeymapConfig.instance().debug2());
        toggleDebug2.onToggle(self -> KeymapConfig.instance().debug2(self.value()));

        toggleMad.padding(new Point<>(4));
        toggleMad.value(KeymapConfig.instance().crashOnProblematicError());
        toggleMad.onToggle(self -> KeymapConfig.instance().crashOnProblematicError(self.value()));

        children.add(labelGeneral);
        children.add(toggleReplaceKeybind);

        children.add(labelLayout);
        children.add(toggleAutoSelectLayout);

        children.add(labelTooltips);
        children.add(toggleShowHelpTooltips);

        children.add(labelExtra);
        children.add(toggleFirstOpenDone);
        children.add(toggleDebug);
        children.add(toggleDebug2);
        children.add(toggleMad);
    }

    @Override
    protected void preRender(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.preRender(graphics, mouseX, mouseY, a);
    }

    @Override
    protected void postRender(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    }

    @Override
    public void onClose() {
        KeymapConfig.save();
        super.onClose();
    }
}
