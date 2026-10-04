package com.leclowndu93150.mc.widgets.utils;

import java.util.Objects;

public class Point<T> {
    T x;
    T y;

    public Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public Point(T xy) {
        this.x = xy;
        this.y = xy;
    }

    public T x() { return x; }
    public T y() { return y; }
    public Point<T> x(T x) { this.x = x; return this; }
    public Point<T> y(T y) { this.y = y; return this; }

    public void setXY(T x, T y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point<?> point = (Point<?>) o;
        return Objects.equals(x, point.x) && Objects.equals(y, point.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
