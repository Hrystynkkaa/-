package com.graphiceditor.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class RealImage extends Image {
    private String filePath;
    private BufferedImage bufferedImage;
    private BufferedImage originalBufferedImage; // Для збереження початкового стану

    public RealImage(long id, String format, String filePath) {
        super(id, format);
        this.filePath = filePath;
        if (!filePath.isEmpty()) {
            loadImage();
        }
    }

    // Конструктор для створення порожнього зображення
    public RealImage(long id, String format, int width, int height) {
        super(id, format);
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();
        originalBufferedImage = deepCopy(bufferedImage); // Зберігаємо початковий стан
    }

    private void loadImage() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("Файл не знайдений: " + filePath);
                return;
            }
            bufferedImage = ImageIO.read(file);
            if (bufferedImage == null) {
                System.err.println("Не вдалося завантажити зображення: " + filePath);
            } else {
                System.out.println("Зображення завантажене успішно: " + filePath);
                originalBufferedImage = deepCopy(bufferedImage); // Зберігаємо початковий стан
            }
        } catch (IOException e) {
            System.err.println("Помилка завантаження: " + e.getMessage());
        }
    }

    public void saveImage(String path) {
        try {
            if (bufferedImage == null) {
                throw new IllegalArgumentException("Зображення порожнє");
            }
            File outputFile = new File(path);
            ImageIO.write(bufferedImage, "PNG", outputFile);
            System.out.println("Зображення збережено: " + path);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Помилка збереження: " + e.getMessage());
        }
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @Override
    public void display() {
        System.out.println("Displaying Real Image ID: " + getId());
        if (bufferedImage == null) {
            System.err.println("Зображення не завантажено або порожнє.");
            return;
        }

        JFrame frame = new JFrame("Display Image ID: " + getId());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(bufferedImage.getWidth(), bufferedImage.getHeight());
        JLabel imageLabel = new JLabel(new ImageIcon(bufferedImage));
        frame.add(imageLabel);
        frame.pack();
        frame.setVisible(true);
    }

    public void reload() {
        if (originalBufferedImage != null) {
            bufferedImage = deepCopy(originalBufferedImage); // Відновлюємо з оригіналу
            System.out.println("Зображення відновлено до початкового стану.");
        } else {
            System.err.println("Початковий стан зображення відсутній.");
        }
    }

    // Метод для створення копії BufferedImage
    private BufferedImage deepCopy(BufferedImage source) {
        BufferedImage copy = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics2D g = copy.createGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return copy;
    }
}
