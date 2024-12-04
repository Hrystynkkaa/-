package com.graphiceditor.service;

import com.graphiceditor.model.Color;
import com.graphiceditor.repository.ColorRepository;

import java.util.List;

// ColorService.java
public class ColorService {
    private final ColorRepository colorRepository;

    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public Color getColor(long id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Color not found for id: " + id));
    }

    public void saveColor(Color color) {
        colorRepository.save(color);
    }

    public void deleteColor(long id) {
        colorRepository.deleteById(id);
    }

    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }
}

