package com.graphiceditor.service;

import com.graphiceditor.model.Layer;
import com.graphiceditor.repository.LayerRepository;

import java.util.List;

// LayerService.java
public class LayerService {
    private final LayerRepository layerRepository;

    public LayerService(LayerRepository layerRepository) {
        this.layerRepository = layerRepository;
    }

    public void insertLayer(Layer layer) {
        layerRepository.save(layer);
    }

    public void deleteLayer(long id) {
        layerRepository.deleteById(id);
    }

    public void changeOrder(Layer layer, int newOrder) {
        layer.setOrder(newOrder);
        layerRepository.save(layer);
    }

    public List<Layer> getAllLayers() {
        return layerRepository.findAll();
    }
}
