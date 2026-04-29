package com.leclowndu93150.keymap.client.gui.screen;

import com.leclowndu93150.keymap.utils.Utils;
import com.leclowndu93150.mc.widgets.EButton;
import com.leclowndu93150.mc.widgets.ELabel;
import com.leclowndu93150.mc.widgets.EScreen;
import com.leclowndu93150.mc.widgets.EWidget;
import com.leclowndu93150.mc.widgets.ScrollTextList;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class HelpScreen extends EScreen {
    protected ScrollTextList listHelp;
    protected ELabel lblTitle;
    protected EButton btnClose;

    public HelpScreen(Screen parent) {
        super(parent, Component.translatable("keymap.scrHelp"));
    }

    @Override
    protected void onInit() {
        scr = scrFromWidth(Math.min(450, width));

        lblTitle = new ELabel(scr.left() + padding.x(), scr.top() + padding.y(), scr.w() - padding.x() * 2, 16);
        lblTitle.text(Component.translatable("keymap.scrHelp"));
        lblTitle.center(true);

        listHelp = ScrollTextList.createFromString(
                Utils.translate("keymap.textHelp"),
                lblTitle.left(),
                lblTitle.bottom() + padding.y(),
                lblTitle.rect().w(),
                scr.h() - padding.y() * 3 - 16);

        btnClose = new EButton(
                Component.translatable("keymap.btnClearSearch"), listHelp.right() - 16, scr.y() + padding.y(), 16, 16);

        btnClose.clickAction(this::onBtnCloseClicked);

        addRenderableWidget(lblTitle);
        addRenderableWidget(listHelp);
        addRenderableWidget(btnClose);
    }

    protected void onBtnCloseClicked(EWidget source) {
        onClose();
    }

    @Override
    protected void preRenderScreen(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.fill(0, 0, width, height, 0x55000000);
        if (scr != null) drawOutline(graphics, scr, 0xFFFFFFFF);
    }
}
