package com.graphiceditor.repository;


import com.graphiceditor.model.Color;

import java.util.List;
import java.util.Optional;

// ColorRepository.java
public interface ColorRepository {
    Optional<Color> findById(long id);
    List<Color> findAll();
    void save(Color color);
    void deleteById(long id);
}

