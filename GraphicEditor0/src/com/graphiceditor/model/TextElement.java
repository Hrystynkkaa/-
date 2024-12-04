package com.graphiceditor.model;

public class TextElement extends Element {
    private String text;

    public TextElement(long id, Color color, String text) {
        super(id, color);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
