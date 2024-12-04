package com.graphiceditor.model;

public class ImageProxy extends Image {

    private RealImage realImage;
    private String filePath;

    public ImageProxy(long id, String format, String filePath) {
        super(id, format);
        this.filePath = filePath;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(getId(), getFormat(), filePath);
        }
        realImage.display();
    }
}

