package com.graphiceditor.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class TextElement extends GraphicElement {
    private String text;
    private Font font;

    public TextElement(int x, int y, String text, Font font, Color color) {
        super(x, y, "text", color);  // Передаємо "text" як значення figure
        this.text = text;
        this.font = font;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(text, x, y);
    }

    @Override
    public GraphicElement clone() {
        return new TextElement(x, y, text, font, color);
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
