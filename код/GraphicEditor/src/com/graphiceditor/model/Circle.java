package com.graphiceditor.model;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends GraphicElement {
    private int radius;

    public Circle(int x, int y, int radius, Color color) {
        super(x, y,"circle", color);
        this.radius = radius;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, radius * 2, radius * 2);
    }

    @Override
    public GraphicElement clone() {
        return new Circle(x, y, radius, color); // Створюємо новий об'єкт з такими ж властивостями
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}

