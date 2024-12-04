package com.graphiceditor.model;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends GraphicElement {
    private int width;
    private int height;

    public Rectangle(int x, int y, int width, int height, Color color) {
        super(x, y, "rectangle", color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    @Override
    public GraphicElement clone() {
        return new Rectangle(x, y, width, height, color); // Створюємо копію
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

