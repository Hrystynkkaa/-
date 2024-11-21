package com.graphiceditor.model;

import java.util.HashMap;
import java.util.Map;

public class Layer {
    private long id;
    private int order;
    private Map<String, String> coordinates;

    public Layer(long id, int order) {
        this.id = id;
        this.order = order;
        this.coordinates = new HashMap<>();
    }

    public long getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Map<String, String> getCoordinates() {
        return coordinates;
    }
}
