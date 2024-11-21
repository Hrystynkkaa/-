package com.graphiceditor.service;

import com.graphiceditor.model.Element;
import com.graphiceditor.model.Image;
import com.graphiceditor.repository.ImageRepository;

import java.util.List;

// ImageService.java
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image createNewImage(String format) {
        Image image = new Image(System.currentTimeMillis(), format); // Generate ID based on timestamp
        imageRepository.save(image);
        return image;
    }

    public void applyVisualEffect(Image image, String effect) {
        image.setVisualEffect(effect);
        imageRepository.save(image);
    }

    public void addElementToImage(Image image, Element element) {
        image.getElements().add(element);
        imageRepository.save(image);
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
}
