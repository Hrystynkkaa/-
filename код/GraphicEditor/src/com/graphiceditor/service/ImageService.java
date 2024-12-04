package com.graphiceditor.service;

import com.graphiceditor.model.Image;

public interface ImageService {
    void saveImage(Image image);
    Image loadImage(long id);
    void deleteImage(long id);
}
