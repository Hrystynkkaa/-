package com.graphiceditor.repository;


import com.graphiceditor.model.Element;

import java.util.List;
import java.util.Optional;

// ElementRepository.java
public interface ElementRepository {
    Optional<Element> findById(long id);
    List<Element> findAll();
    void save(Element element);
    void deleteById(long id);
}
