package com.graphiceditor.model;

public abstract class Element {
    private long id;
    private Color color;

    public Element(long id, Color color) {
        this.id = id;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
