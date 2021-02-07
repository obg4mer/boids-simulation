package util;

import java.util.Objects;

public class Vector2 {

    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        this(0, 0);
    }

    //region Class Methods

    public Vector2 add(Vector2 vec) {
        this.x += vec.x;
        this.y += vec.y;
        return this;
    }

    public Vector2 subtract(Vector2 vec) {
        this.x -= vec.x;
        this.y -= vec.y;
        return this;
    }

    public Vector2 multiply(float value) {
        this.x *= value;
        this.y *= value;
        return this;
    }

    public Vector2 divide(float value) {
        this.x /= value;
        this.y /= value;
        return this;
    }

    public Vector2 normalize() {
        float magnitude = (float)Math.sqrt(sqrMagnitude());
        if (magnitude > 0) multiply(1/magnitude);
        return this;
    }

    public Vector2 setMagnitude(float length) {
        return normalize().multiply(length);
    }

    public Vector2 rotate(float angle) {
        float sin = - (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);

        float newX = x * cos - y * sin;
        float newY = x * sin + y * cos;

        x = newX;
        y = newY;
        return this;
    }

    public float distance(Vector2 vec) {
        return (float)Math.sqrt(sqrDistance(vec));
    }

    public float sqrDistance(Vector2 vec) {
        return (float)(Math.pow(this.x - vec.x, 2) + Math.pow(this.y - vec.y, 2));
    }

    public float magnitude() {
        return (float)Math.sqrt(sqrMagnitude());
    }

    public float sqrMagnitude() {
        return x*x + y*y;
    }

    public float angle() {
        float angle = (float)Math.atan2(-y, x);
        if (angle > 2*Math.PI) angle -= 2*Math.PI;
        else if (angle < 0) angle += 2*Math.PI;
        return angle;
    }

    //endregion

    //region Static Methods

    public static Vector2 fromAngle(float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);

        return new Vector2(cos, -sin);
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

    @Override
    public String toString() {
        return "" + x + " // " + y;
    }

    //endregion

}
