package com.graphiceditor.repository;


import com.graphiceditor.model.Layer;

import java.util.List;
import java.util.Optional;

// LayerRepository.java
public interface LayerRepository {
    Optional<Layer> findById(long id);
    List<Layer> findAll();
    void save(Layer layer);
    void deleteById(long id);
}
