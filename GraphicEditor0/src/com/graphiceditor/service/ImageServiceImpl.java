package com.graphiceditor.service;

import com.graphiceditor.model.Image;
import com.graphiceditor.model.ImageProxy;

import java.util.HashMap;
import java.util.Map;

public class ImageServiceImpl implements ImageService {

    private Map<Long, Image> imageDatabase = new HashMap<>();

    @Override
    public void saveImage(Image image) {
        imageDatabase.put(image.getId(), image);
        System.out.println("Image saved to the database: " + image.getId());
    }

    @Override
    public Image loadImage(long id) {
        if (imageDatabase.containsKey(id)) {
            return imageDatabase.get(id);
        }
        System.out.println("Image not found in the database: " + id);
        return null;
    }

    @Override
    public void deleteImage(long id) {
        imageDatabase.remove(id);
        System.out.println("Image deleted from the database: " + id);
    }
}

