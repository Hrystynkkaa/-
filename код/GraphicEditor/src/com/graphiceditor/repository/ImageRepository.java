package com.graphiceditor.repository;

import com.graphiceditor.model.Image;

import java.util.List;
import java.util.Optional;

// ImageRepository.java
public interface ImageRepository {
    Optional<Image> findById(long id);
    List<Image> findAll();
    void save(Image image);
    void deleteById(long id);
}
