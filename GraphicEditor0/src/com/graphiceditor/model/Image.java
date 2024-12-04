package com.graphiceditor.model;

import java.util.ArrayList;
import java.util.List;

public class Image {
    private long id;
    private String format;
    private String visualEffect;
    private List<Layer> layers;
    private List<Element> elements;

    public Image(long id, String format) {
        this.id = id;
        this.format = format;
        this.layers = new ArrayList<>();
        this.elements = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getFormat() {
        return format;
    }

    public String getVisualEffect() {
        return visualEffect;
    }

    public void setVisualEffect(String visualEffect) {
        this.visualEffect = visualEffect;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public List<Element> getElements() {
        return elements;
    }
}

