package com.graphiceditor.decorator;

import java.awt.image.BufferedImage;

public class InfraredEffect implements ImageEffect {
    @Override
    public BufferedImage applyEffect(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Get the RGB value of the pixel
                int rgb = image.getRGB(x, y);

                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                red = Math.min((int)(red * 1.5), 255);
                green = Math.max((int)(green * 0.5), 0);
                blue = Math.max((int)(blue * 0.5), 0);

                int newRgb = (red << 16) | (green << 8) | blue;

                image.setRGB(x, y, newRgb);
            }
        }
        return image;
    }
}
