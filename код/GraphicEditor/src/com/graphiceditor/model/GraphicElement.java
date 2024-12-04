package com.graphiceditor.model;

import java.awt.Color;
import java.awt.Graphics;


public class GraphicElement extends Element {
    private String figure;

    public GraphicElement(int x, int y, String figure, Color color) {
        super(x, y, color);
        this.figure = figure;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        switch (figure) {
            case "rectangle":
                g.fillRect(x, y, 100, 50);
                break;
            case "circle":
                g.fillOval(x, y, 50, 50);
                break;
        }
    }

    @Override
    public GraphicElement clone() {
        return new GraphicElement(x, y, figure, color);
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }
}
