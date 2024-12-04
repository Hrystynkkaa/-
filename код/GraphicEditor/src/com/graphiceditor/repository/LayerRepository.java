package com.graphiceditor.repository;

import com.graphiceditor.model.Layer;

import java.util.HashMap;
import java.util.Map;

public class LayerRepository {
    private final Map<Long, Layer> layerStorage = new HashMap<>();

    public void saveLayer(Layer layer) {
        layerStorage.put(layer.getId(), layer);
        System.out.println("Layer saved: " + layer.getId());
    }

    public Layer loadLayer(long id) {
        Layer layer = layerStorage.get(id);
        if (layer == null) {
            System.out.println("Layer not found: " + id);
        } else {
            System.out.println("Layer loaded: " + id);
        }
        return layer;
    }

    public void deleteLayer(long id) {
        layerStorage.remove(id);
        System.out.println("Layer deleted: " + id);
    }

    public Map<Long, Layer> getAllLayers() {
        return new HashMap<>(layerStorage);
    }
}
