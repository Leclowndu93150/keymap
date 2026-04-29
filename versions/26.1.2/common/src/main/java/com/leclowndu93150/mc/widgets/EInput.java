package com.leclowndu93150.mc.widgets;

import com.leclowndu93150.mc.widgets.utils.WidgetUtils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.locale.Language;
import net.minecraft.util.StringUtil;

public class EInput extends EWidget {
    protected final StringBuilder text = new StringBuilder();
    protected final String placeholder = Language.getInstance().getOrDefault("keymap.inpSearchPlaceholder");

    protected String display = "";

    int cursor = 0;

    EInputChangedAction onChanged;

    public EInput(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public int cursor() { return cursor; }

    public EInput onChanged(EInputChangedAction a) { this.onChanged = a; return this; }

    public String text() {
        return text.toString();
    }

    public void setCursor(int c) {
        cursor = WidgetUtils.clamp(c, 0, text.length());
        updateDisplay();
    }

    public void moveCursor(int offset) {
        setCursor(cursor + offset);
    }

    protected void updateDisplay() {
        display = text.toString();
        int maxW = rect.w() - padding.x() * 2;
        int tWidth = font.width(display);
        if (tWidth > maxW) {
            StringBuilder temp = new StringBuilder(text);
            display = new StringBuilder(font.plainSubstrByWidth(temp.reverse().toString(), maxW))
                    .reverse()
                    .toString();
            temp.reverse();

            int delta = text.length() - display.length();
            int cDelta = cursor - delta;
            if (cDelta <= 0) {
                display = font.plainSubstrByWidth(temp.substring(Math.max(0, cursor - 1), temp.length()), maxW);
            }
        }
        if (onChanged != null) onChanged.run(this, text.toString());
    }

    public void text(String t) {
        text.setLength(0);
        text.append(t);
        moveCursor(text.length());
        updateDisplay();
    }

    public void textAppend(String t) {
        text.append(t);
        updateDisplay();
        moveCursor(text.length());
    }

    public void textDelete(int count) {
        if (cursor > text.length() || cursor < 0 || count == 0) return;
        int sub = Math.max(0, cursor - count);
        int min = Math.min(sub, cursor);
        int max = Math.max(sub, cursor);
        if (sub == cursor) return;
        text.delete(min, max);
        updateDisplay();
        moveCursor(0);
    }

    protected void write(String s) {
        if (cursor == text.length()) text.append(s);
        else text.insert(cursor, s);
        updateDisplay();
        moveCursor(s.length());
    }

    protected void write(char s) {
        if (cursor == text.length()) text.append(s);
        else text.insert(cursor, s);
        updateDisplay();
        moveCursor(1);
    }

    @Override
    protected boolean onCharTyped(char codePoint, int modifiers) {
        if (StringUtil.isAllowedChatCharacter(codePoint)) {
            write(codePoint);
            return true;
        }
        return false;
    }

    @Override
    protected boolean onKeyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == InputConstants.KEY_BACKSPACE) {
            boolean isLast = cursor == text.length();
            textDelete(1);
            if (!isLast) moveCursor(-1);
            return true;
        }
        if (keyCode == InputConstants.KEY_DELETE) {
            textDelete(-1);
            moveCursor(0);
            return true;
        }
        if (keyCode == InputConstants.KEY_LEFT) {
            moveCursor(-1);
            return true;
        }
        if (keyCode == InputConstants.KEY_RIGHT) {
            moveCursor(1);
            return true;
        }
        if (keyCode == InputConstants.KEY_END) {
            setCursor(text.length());
            return true;
        }
        if (keyCode == InputConstants.KEY_HOME) {
            setCursor(0);
            return true;
        }
        return false;
    }

    @Override
    protected void renderWidget(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        drawBg(graphics);
        drawOutline(graphics);

        if (!display.isEmpty()) {
            graphics.text(font, display, left() + padding.x(), top() + padding.y(), colorVariant().text());
        } else {
            graphics.text(font, placeholder, left() + padding.x(), top() + padding.y(), color.disabled().text());
        }

        int cPosX = 0;
        if (cursor > 0) {
            int cDelta = cursor - (text.length() - display.length());
            cPosX = font.width(display.substring(0, Math.min(cDelta <= 0 ? 1 : cDelta, display.length())));
        }

        graphics.horizontalLine(
                left() + padding.x() + cPosX,
                left() + padding.x() + cPosX + 4,
                bottom() - padding.y(),
                focused() ? 0xffff0000 : 0xffffffff);
    }

    public interface EInputChangedAction {
        void run(EInput source, String newText);
    }
}
