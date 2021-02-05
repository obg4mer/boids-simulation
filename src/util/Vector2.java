package util;

import java.util.Objects;

public class Vector2 {

    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    //region Class Methods

    public Vector2 add(Vector2 vec) {
        this.x += vec.x;
        this.y += vec.y;
        return this;
    }

    public Vector2 multiply(float value) {
        this.x *= value;
        this.y *= value;
        return this;
    }

    public Vector2 invert() {
        this.x *= -1;
        this.y *= -1;
        return this;
    }

    public static Vector2 fromAngle(double angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);

        return new Vector2(cos, sin);
    }

    public Vector2 rotate(double angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);

        float newX = x * cos - y * sin;
        float newY = x * sin + y * cos;

        x = newX;
        y = newY;

        return this;
    }

    //endregion

    //region Default Java Methods

    @Override
    public Vector2 clone() {
        return new Vector2(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vec = (Vector2) o;
        return x == vec.x && y == vec.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    //endregion

}
