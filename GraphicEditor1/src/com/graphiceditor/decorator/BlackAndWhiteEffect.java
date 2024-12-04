package com.graphiceditor.decorator;

import java.awt.image.BufferedImage;

public class BlackAndWhiteEffect implements ImageEffect {
    @Override
    public BufferedImage applyEffect(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);

                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                int luminance = (int)(0.3 * red + 0.59 * green + 0.11 * blue);

                int gray = (luminance << 16) | (luminance << 8) | luminance;

                image.setRGB(x, y, gray);
            }
        }
        return image;
    }
}

