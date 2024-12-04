package com.graphiceditor.decorator;

import java.awt.image.BufferedImage;

public interface ImageEffect {
    BufferedImage applyEffect(BufferedImage image);
}

