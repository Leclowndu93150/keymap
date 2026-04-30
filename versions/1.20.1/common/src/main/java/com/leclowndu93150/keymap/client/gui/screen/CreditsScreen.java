package com.leclowndu93150.keymap.client.gui.screen;

import com.leclowndu93150.keymap.objects.Credits;
import com.leclowndu93150.keymap.utils.Utils;
import com.leclowndu93150.mc.widgets.EButton;
import com.leclowndu93150.mc.widgets.ELabel;
import com.leclowndu93150.mc.widgets.EScreen;
import com.leclowndu93150.mc.widgets.EWidget;
import com.leclowndu93150.mc.widgets.ScrollTextList;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class CreditsScreen extends EScreen {
    protected ScrollTextList listHelp;
    protected ELabel lblTitle;
    protected EButton btnClose;

    public CreditsScreen(Screen parent) {
        super(parent, Component.translatable("keymap.scrCredits"));
    }

    @Override
    protected void onInit() {
        scr = scrFromWidth(Math.min(450, width));

        lblTitle = new ELabel(scr.left() + padding.x(), scr.top() + padding.y(), scr.w() - padding.x() * 2, 16);
        lblTitle.text(Component.translatable("keymap.lblCredits"));
        lblTitle.center(true);

        StringBuilder layoutCredits = new StringBuilder(Utils.translate("keymap.lblCreditsLayout"));
        StringBuilder languageCredits = new StringBuilder(Utils.translate("keymap.lblCreditsLanguage"));
        StringBuilder coreCredits = new StringBuilder(Utils.translate("keymap.lblCreditsCore"));

        Credits credits = Credits.instance();
        if (credits != null) {
            if (credits.layout() != null) {
                for (Credits.LayoutCredits lay : credits.layout()) {
                    layoutCredits.append("\n\n- ").append(lay.key());
                    if (lay.name() != null) for (String name : lay.name()) layoutCredits.append("\n   - ").append(name);
                }
            }
            if (credits.language() != null) {
                for (Credits.LanguageCredits lang : credits.language()) {
                    languageCredits.append("\n\n- ").append(lang.lang());
                    if (lang.name() != null) for (String name : lang.name()) languageCredits.append("\n   - ").append(name);
                }
            }
            if (credits.core() != null) {
                for (Credits.CoreCredits contrib : credits.core()) {
                    coreCredits.append("\n\n- ").append(contrib.name());
                    if (contrib.contributions() != null) for (String c : contrib.contributions()) coreCredits.append("\n   - ").append(c);
                }
            }
        }

        listHelp = ScrollTextList.createFromString(
                coreCredits + "\n\n\n" + layoutCredits + "\n\n\n" + languageCredits,
                lblTitle.left(), lblTitle.bottom() + padding.y(),
                lblTitle.rect().w(), scr.h() - padding.y() * 3 - 16);

        btnClose = new EButton(
                Component.translatable("keymap.btnClearSearch"),
                listHelp.right() - 16, scr.y() + padding.y(), 16, 16);

        btnClose.clickAction(this::onBtnCloseClicked);

        addRenderableWidget(lblTitle);
        addRenderableWidget(listHelp);
        addRenderableWidget(btnClose);
    }

    protected void onBtnCloseClicked(EWidget source) { onClose(); }

    @Override
    protected void preRenderScreen(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        renderBackground(graphics);
        if (scr != null) drawOutline(graphics, scr, 0xFFFFFFFF);
    }
}
