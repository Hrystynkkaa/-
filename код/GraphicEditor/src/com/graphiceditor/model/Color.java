package com.graphiceditor.model;

// Models
public class Color {
    private long id;
    private String value;

    public Color(long id, String value) {
        this.id = id;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}