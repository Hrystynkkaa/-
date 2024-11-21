package com.graphiceditor.model;

public class GraphicElement extends Element {
    private String figure;

    public GraphicElement(long id, Color color, String figure) {
        super(id, color);
        this.figure = figure;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }
}
