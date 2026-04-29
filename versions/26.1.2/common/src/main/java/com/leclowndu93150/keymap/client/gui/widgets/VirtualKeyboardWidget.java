package com.leclowndu93150.keymap.client.gui.widgets;

import com.leclowndu93150.keymap.Keymap;
import com.leclowndu93150.keymap.keys.layout.KeyData;
import com.leclowndu93150.keymap.keys.layout.KeyRow;
import com.leclowndu93150.mc.widgets.EWidget;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.ArrayList;
import java.util.List;

public class VirtualKeyboardWidget extends EWidget {
    protected final List<KeyWidget> childKeys = new ArrayList<>();
    protected final List<KeyRow> keys;
    protected int gap = 2;

    protected SimpleWidgetAction<VirtualKeyboardWidget> onKeyClicked;
    protected SpecialVKKeyClicked onSpecialKeyClicked;

    protected KeyWidget lastActionFrom;

    public VirtualKeyboardWidget(List<KeyRow> keys, int x, int y, int w, int h) {
        super(x, y, w, h);
        this.keys = keys;
        init();
    }

    public List<KeyWidget> childKeys() { return childKeys; }
    public int gap() { return gap; }
    public VirtualKeyboardWidget gap(int g) { this.gap = g; return this; }
    public KeyWidget lastActionFrom() { return lastActionFrom; }
    public VirtualKeyboardWidget onKeyClicked(SimpleWidgetAction<VirtualKeyboardWidget> a) { this.onKeyClicked = a; return this; }
    public VirtualKeyboardWidget onSpecialKeyClicked(SpecialVKKeyClicked a) { this.onSpecialKeyClicked = a; return this; }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return false;
    }

    public VirtualKeyboardWidget destroy() {
        for (KeyWidget childKey : childKeys) {
            childKey.destroy();
        }
        return this;
    }

    protected void _onKeyClicked(KeyWidget source) {
        lastActionFrom = source;
        if (onKeyClicked != null) onKeyClicked.run(this);
        lastActionFrom = null;
    }

    protected void _onSpecialKeyClicked(KeyWidget source, int button) {
        if (source.isNormal()) {
            Keymap.logger().warn("False Special");
            _onKeyClicked(source);
            return;
        }
        lastActionFrom = source;
        if (onSpecialKeyClicked != null) onSpecialKeyClicked.run(this, source, button);
        lastActionFrom = null;
    }

    @Override
    protected void init() {
        int w = 16;
        int h = 16;
        int currentX = left();
        int currentY = top();

        int maxX = 0;

        for (KeyRow kk : keys) {
            if (kk.row() == null) continue;
            for (KeyData k : kk.row()) {
                int ww = w + k.width();
                int hh = h + k.height();
                KeyWidget kw = new KeyWidget(k, currentX, currentY, ww, hh);
                kw.onClick(this::_onKeyClicked);
                kw.onSpecialClick(this::_onSpecialKeyClicked);

                childKeys.add(kw);
                currentX += gap + ww;
            }
            maxX = Math.max(currentX - gap, maxX);
            currentX = left();
            currentY += gap + h;
        }

        currentY -= gap;
        rect.w(maxX - left());
        rect.h(currentY - top());
    }

    @Override
    protected void renderWidget(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        for (KeyWidget kw : childKeys) {
            kw.extractRenderState(graphics, mouseX, mouseY, a);
        }
    }

    public interface SpecialVKKeyClicked {
        void run(VirtualKeyboardWidget source, KeyWidget keySource, int button);
    }
}
