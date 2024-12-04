package com.graphiceditor.model;

import java.awt.*;
import java.awt.Color;

public abstract class Element implements Prototype<Element> {
    protected int x;
    protected int y;
    protected Color color;

    public Element(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public abstract void draw(Graphics g);

    @Override
    public abstract Element clone();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
