package com.graphiceditor.service;

import com.graphiceditor.model.Image;
import com.graphiceditor.model.RealImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImageStorageProxy implements ImageService {

    private ImageServiceImpl imageServiceImpl;
    private String storagePath;

    public ImageStorageProxy(String storagePath) {
        this.imageServiceImpl = new ImageServiceImpl();
        this.storagePath = storagePath;
    }

    @Override
    public void saveImage(Image image) {
        if (image instanceof RealImage) {
            RealImage realImage = (RealImage) image;
            String imagePath = storagePath + "/" + image.getId() + "." + image.getFormat().toLowerCase();

            try {
                // Перевірка на наявність BufferedImage перед збереженням
                if (realImage.getBufferedImage() != null) {
                    // Збереження зображення на диск
                    File outputFile = new File(imagePath);
                    ImageIO.write(realImage.getBufferedImage(), image.getFormat().toLowerCase(), outputFile);
                    System.out.println("Image saved to file system: " + imagePath);

                    // Після збереження викликаємо saveImage в ImageServiceImpl
                    imageServiceImpl.saveImage(image);
                } else {
                    System.err.println("Cannot save: BufferedImage is null.");
                }
            } catch (IOException e) {
                System.err.println("Error saving image: " + e.getMessage());
            }
        }
    }




    @Override
    public Image loadImage(long id) {
        for (String ext : new String[]{"png", "jpg", "bmp"}) {
            String imagePath = storagePath + "/" + id + "." + ext;
            File imageFile = new File(imagePath);

            // Логування шляху до файлу
            System.out.println("Looking for image at path: " + imagePath);

            if (imageFile.exists()) {
                System.out.println("Image found: " + imagePath);
                return new RealImage(id, ext.toUpperCase(), imagePath);
            }
        }
        System.out.println("Image not found in file system for ID: " + id);
        return null;
    }

    @Override
    public void deleteImage(long id) {
        String imagePath = storagePath + "/" + id + ".png";
        File imageFile = new File(imagePath);

        if (imageFile.exists()) {
            imageFile.delete();
            System.out.println("Image deleted from file system: " + imagePath);
        } else {
            System.out.println("Image not found in file system for deletion: " + imagePath);
        }
        imageServiceImpl.deleteImage(id);
    }
}
