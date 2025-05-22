package com.github.einjerjar.mc.widgets.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;

public class WidgetUtils {
    private WidgetUtils() {}

    public static double clamp(double x, double min, double max) {
        return Math.max(Math.min(x, max), min);
    }

    public static int clamp(int x, int min, int max) {
        return Math.max(Math.min(x, max), min);
    }

    public static void drawQuad(
            BufferBuilder bb,
            int left,
            int right,
            int top,
            int bottom,
            int color) {
        bb.addVertex(left, bottom, 0.0f).setColor(color);
        bb.addVertex(right, bottom, 0.0f).setColor(color);
        bb.addVertex(right, top, 0.0f).setColor(color);
        bb.addVertex(left, top, 0.0f).setColor(color);
    }
    /*
    public static void drawQuad(int left, int right, int top, int bottom, int color) {
        RenderSystem.setShader(CoreShaders.POSITION_COLOR);
        BufferBuilder bb = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        drawQuad(bb, left, right, top, bottom, color);

        BufferUploader.drawWithShader(bb.buildOrThrow());
    }
     */

    public static class SColor {
        public final int a;
        public final int r;
        public final int g;
        public final int b;

        public SColor(float a, float r, float g, float b) {
            this.a = (int) (a * 255);
            this.r = (int) (r * 255);
            this.g = (int) (g * 255);
            this.b = (int) (b * 255);
        }

        public SColor(int color) {
            this.a = color >> 24 & 0xff;
            this.r = color >> 16 & 0xff;
            this.g = color >> 8 & 0xff;
            this.b = color & 0xff;
        }
    }
}
