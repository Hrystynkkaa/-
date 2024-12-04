package com.graphiceditor.service;

import com.graphiceditor.model.Layer;
import com.graphiceditor.repository.LayerRepository;

import java.util.Map;

public class LayerService {
    private final LayerRepository layerRepository;

    public LayerService(LayerRepository layerRepository) {
        this.layerRepository = layerRepository;
    }

    public Layer createLayer() {
        Layer newLayer = new Layer(System.currentTimeMillis());
        layerRepository.saveLayer(newLayer);
        return newLayer;
    }

    public Layer loadLayer(long id) {
        return layerRepository.loadLayer(id);
    }

    public void deleteLayer(long id) {
        layerRepository.deleteLayer(id);
    }

    public Layer cloneLayer(long id) {
        Layer originalLayer = layerRepository.loadLayer(id);
        if (originalLayer == null) {
            throw new IllegalArgumentException("Layer not found: " + id);
        }
        Layer clonedLayer = originalLayer.clone();
        layerRepository.saveLayer(clonedLayer);
        return clonedLayer;
    }

    public Map<Long, Layer> getAllLayers() {
        return layerRepository.getAllLayers();
    }
}
