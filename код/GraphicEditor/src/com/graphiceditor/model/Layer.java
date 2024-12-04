package com.graphiceditor.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Layer implements Cloneable {
    private long id;
    private String name;
    private BufferedImage content;
    private boolean visible;

    public Layer(long id) {
        this.id = id;
        this.name = "Layer " + id;
        this.visible = true;
        this.content = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage getContent() {
        return content;
    }

    public void setContent(BufferedImage content) {
        this.content = content;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public Layer clone() {
        try {
            Layer clone = (Layer) super.clone();
            clone.id = System.currentTimeMillis();
            clone.content = new BufferedImage(content.getWidth(), content.getHeight(), content.getType());
            Graphics g = clone.content.getGraphics();
            g.drawImage(content, 0, 0, null);
            g.dispose();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Layer cloning failed", e);
        }
    }
}

