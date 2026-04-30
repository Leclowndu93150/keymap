package com.leclowndu93150.mc.widgets.utils;

import java.util.Objects;

public class Rect {
    int x;
    int y;
    int w;
    int h;

    public Rect(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public int x() { return x; }
    public int y() { return y; }
    public int w() { return w; }
    public int h() { return h; }
    public Rect x(int x) { this.x = x; return this; }
    public Rect y(int y) { this.y = y; return this; }
    public Rect w(int w) { this.w = w; return this; }
    public Rect h(int h) { this.h = h; return this; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rect rect)) return false;
        return x == rect.x && y == rect.y && w == rect.w && h == rect.h;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, w, h);
    }

    public int top() { return y; }
    public int bottom() { return y + h; }
    public int left() { return x; }
    public int right() { return x + w; }

    public void top(int t) { this.y = t; }
    public void bottom(int b) { this.h = b - this.y; }
    public void left(int l) { this.x = l; }
    public void right(int r) { this.w = r - this.x; }

    public int midX() { return (right() + left()) / 2; }
    public int midY() { return (bottom() + top()) / 2; }

    public boolean contains(double px, double py) {
        return px >= left() && px <= right() && py >= top() && py <= bottom();
    }

    public boolean contains(Point<Integer> p) {
        return contains(p.x(), p.y());
    }
}
