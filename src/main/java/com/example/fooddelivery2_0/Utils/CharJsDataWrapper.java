package com.example.fooddelivery2_0.Utils;
import java.io.Serializable;

public class CharJsDataWrapper<T> implements Serializable {
    private String x;
    private T y;

    public CharJsDataWrapper(String x, T y) {
        this.x = x;
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }
}

