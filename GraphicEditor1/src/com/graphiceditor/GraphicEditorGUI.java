package com.graphiceditor;

import com.graphiceditor.model.*;
import com.graphiceditor.model.Rectangle;
import com.graphiceditor.model.GraphicElement;
import com.graphiceditor.repository.LayerRepository;
import com.graphiceditor.service.ImageService;
import com.graphiceditor.service.ImageStorageProxy;
import com.graphiceditor.service.LayerService;
import com.graphiceditor.decorator.InfraredEffect;
import com.graphiceditor.decorator.BlackAndWhiteEffect;
import com.graphiceditor.decorator.ImageEffect;
import com.graphiceditor.model.TextElement;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphicEditorGUI extends JFrame {

    private ImageService imageService;
    private LayerService layerService;
    private Layer currentLayer; // Current layer
    private RealImage currentImage; // Current image
    private List<GraphicElement> graphicElements; // List of shapes
    private JPanel canvasPanel;
    private JLabel statusLabel;
    private JPanel effectsPanel;
    private String currentShapeType = "Rectangle"; // Default shape
    private Point startPoint; // Start point for drawing
    private Point endPoint; // End point for drawing

    public GraphicEditorGUI() {
        imageService = new ImageStorageProxy("D:/");
        layerService = new LayerService(new LayerRepository());
        graphicElements = new ArrayList<>();

        setTitle("Graphic Editor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createMenu();
        initializeUI();
    }

    private void initializeUI() {
        canvasPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawContent(g);
            }
        };
        canvasPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint(); // Save starting point
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endPoint = e.getPoint(); // Save ending point
                addShape(); // Add shape to the canvas
                repaint();
            }
        });

        canvasPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endPoint = e.getPoint();
                repaint(); // Repaint while dragging for preview
            }
        });
        add(canvasPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Ready");
        add(statusLabel, BorderLayout.SOUTH);

        // Add panel for effects buttons
        effectsPanel = new JPanel();
        add(effectsPanel, BorderLayout.NORTH);

        // Create buttons for applying effects
        JButton blackAndWhiteButton = new JButton("Black & White");
        blackAndWhiteButton.addActionListener(e -> applyEffect(new BlackAndWhiteEffect()));

        JButton infraredButton = new JButton("Infrared");
        infraredButton.addActionListener(e -> applyEffect(new InfraredEffect()));
        // Add buttons to the panel
        effectsPanel.add(blackAndWhiteButton);
        effectsPanel.add(infraredButton);
    }
    private void addText() {
        if (currentLayer == null) {
            showError("Спочатку створіть або виберіть шар.");
            return;
        }

        // Параметри для нового текстового елемента
        String text = JOptionPane.showInputDialog(this, "Введіть текст:", "Новий текст");
        if (text == null || text.trim().isEmpty()) {
            showError("Текст не може бути порожнім.");
            return;
        }

        // Створення нового текстового елемента
        Font font = new Font("Arial", Font.PLAIN, 24); // За замовчуванням
        TextElement textElement = new TextElement(100, 100, text, font, Color.BLACK); // За замовчуванням чорний текст

        // Додати текстовий елемент до списку елементів
        graphicElements.add(textElement);
        repaint();
    }

    private void applyEffect(ImageEffect effect) {
        if (currentImage != null && currentImage.getBufferedImage() != null) {
            // Apply the selected effect
            BufferedImage processedImage = effect.applyEffect(currentImage.getBufferedImage());
            currentImage.setBufferedImage(processedImage);
            repaint();
            statusLabel.setText("Effect applied");
        } else {
            showError("No image to apply effect.");
        }
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();

        // Menu for shapes
        JMenu shapeMenu = new JMenu("Shapes");
        JMenuItem rectangleItem = new JMenuItem("Rectangle");
        rectangleItem.addActionListener(e -> currentShapeType = "Rectangle");
        JMenuItem circleItem = new JMenuItem("Circle");
        circleItem.addActionListener(e -> currentShapeType = "Circle");
        JMenuItem addTextItem = new JMenuItem("Add Text");
        addTextItem.addActionListener(e -> addText());

        shapeMenu.add(rectangleItem);
        shapeMenu.add(circleItem);
        shapeMenu.add(addTextItem);

        JMenuItem cloneShapeItem = new JMenuItem("Clone Last Shape");
        cloneShapeItem.addActionListener(e -> cloneLastShape());
        shapeMenu.add(cloneShapeItem);

        // Menu for layers
        JMenu layerMenu = new JMenu("Layers");
        JMenuItem createLayerItem = new JMenuItem("Create Layer");
        createLayerItem.addActionListener(e -> createLayer());
        JMenuItem cloneLayerItem = new JMenuItem("Clone Layer");
        cloneLayerItem.addActionListener(e -> cloneLayer());
        JMenuItem deleteLayerItem = new JMenuItem("Delete Layer");
        deleteLayerItem.addActionListener(e -> deleteLayer());
        JMenuItem switchLayerItem = new JMenuItem("Switch Layer");
        switchLayerItem.addActionListener(e -> switchLayer());
        layerMenu.add(createLayerItem);
        layerMenu.add(cloneLayerItem);
        layerMenu.add(deleteLayerItem);
        layerMenu.add(switchLayerItem);

        // Menu for files
        JMenu fileMenu = new JMenu("File");
        JMenuItem newImageItem = new JMenuItem("New Image");
        newImageItem.addActionListener(e -> onCreateImage());
        JMenuItem loadImageItem = new JMenuItem("Load Image");
        loadImageItem.addActionListener(e -> onLoadImage());
        JMenuItem saveImageItem = new JMenuItem("Save");
        saveImageItem.addActionListener(e -> onSaveImage());
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newImageItem);
        fileMenu.add(loadImageItem);
        fileMenu.add(saveImageItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(shapeMenu);
        menuBar.add(layerMenu);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
    private void addShape() {
        if (startPoint == null || endPoint == null) return;

        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(endPoint.x - startPoint.x);
        int height = Math.abs(endPoint.y - startPoint.y);

        GraphicElement shape;
        if (currentShapeType.equals("Rectangle")) {
            shape = new Rectangle(x, y, width, height, Color.BLUE);
        } else {
            int radius = Math.max(width, height) / 2;
            shape = new Circle(x + radius, y + radius, radius, Color.RED);
        }

        graphicElements.add(shape);
        startPoint = null;
        endPoint = null;
    }


    private void cloneLastShape() {
        if (graphicElements.isEmpty()) {
            showError("Немає примітивів для клонування.");
            return;
        }
        GraphicElement lastGraphicElement = graphicElements.get(graphicElements.size() - 1);
        GraphicElement clonedGraphicElement = lastGraphicElement.clone();

        if (clonedGraphicElement instanceof Rectangle) {
            clonedGraphicElement = new Rectangle(((Rectangle) clonedGraphicElement).getX() + 20,
                    ((Rectangle) clonedGraphicElement).getY() + 20,
                    ((Rectangle) clonedGraphicElement).getWidth(),
                    ((Rectangle) clonedGraphicElement).getHeight(),
                    ((Rectangle) clonedGraphicElement).getColor());
        } else if (clonedGraphicElement instanceof Circle) {
            clonedGraphicElement = new Circle(((Circle) clonedGraphicElement).getX() + 20,
                    ((Circle) clonedGraphicElement).getY() + 20,
                    ((Circle) clonedGraphicElement).getRadius(),
                    ((Circle) clonedGraphicElement).getColor());
        }
        graphicElements.add(clonedGraphicElement);
        repaint();
    }

    private void createLayer() {
        Layer newLayer = layerService.createLayer();
        currentLayer = newLayer;
        repaint();
        statusLabel.setText("Створено новий шар: " + newLayer.getName());
    }

    private void cloneLayer() {
        if (currentLayer == null) {
            showError("Немає шару для клонування.");
            return;
        }
        Layer clonedLayer = layerService.cloneLayer(currentLayer.getId());
        currentLayer = clonedLayer;
        repaint();
        statusLabel.setText("Клоновано шар: " + clonedLayer.getName());
    }

    private void deleteLayer() {
        if (currentLayer == null) {
            showError("Немає шару для видалення.");
            return;
        }
        layerService.deleteLayer(currentLayer.getId());
        currentLayer = null;
        repaint();
        statusLabel.setText("Шар видалено.");
    }

    private void switchLayer() {
        Map<Long, Layer> layers = layerService.getAllLayers();
        if (layers.isEmpty()) {
            showError("Немає доступних шарів.");
            return;
        }

        String[] layerNames = layers.values().stream().map(Layer::getName).toArray(String[]::new);
        String selectedLayer = (String) JOptionPane.showInputDialog(this, "Виберіть шар:",
                "Перемикання шару", JOptionPane.PLAIN_MESSAGE, null, layerNames, layerNames[0]);

        if (selectedLayer != null) {
            currentLayer = layers.values().stream()
                    .filter(layer -> layer.getName().equals(selectedLayer))
                    .findFirst()
                    .orElse(null);
            repaint();
            statusLabel.setText("Активний шар: " + currentLayer.getName());
        }
    }

    private void onCreateImage() {
        String widthStr = JOptionPane.showInputDialog(this, "Введіть ширину зображення:", "500");
        String heightStr = JOptionPane.showInputDialog(this, "Введіть висоту зображення:", "500");

        try {
            int width = Integer.parseInt(widthStr);
            int height = Integer.parseInt(heightStr);

            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Розміри повинні бути додатними!");
            }

            BufferedImage newImageBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = newImageBuffer.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);
            g2d.dispose();

            long id = System.currentTimeMillis();
            RealImage newImage = new RealImage(id, "PNG", "");
            newImage.setBufferedImage(newImageBuffer);
            imageService.saveImage(newImage);

            currentImage = newImage;
            repaint();
            statusLabel.setText("Створено нове зображення (" + width + "x" + height + ").");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Введіть коректні числові значення.", "Помилка", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onLoadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Виберіть зображення для завантаження");

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            String format = filePath.substring(filePath.lastIndexOf('.') + 1);

            long id = System.currentTimeMillis();
            currentImage = new RealImage(id, format.toUpperCase(), filePath);
            imageService.saveImage(currentImage);
            repaint();

            statusLabel.setText("Зображення завантажено: " + filePath);
        } else {
            statusLabel.setText("Завантаження скасовано.");
        }
    }

    private void onSaveImage() {
        if (currentImage == null) {
            JOptionPane.showMessageDialog(this, "Немає зображення для збереження.", "Помилка", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Зберегти зображення");

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String savePath = selectedFile.getAbsolutePath();

            currentImage.saveImage(savePath);
            statusLabel.setText("Зображення збережено: " + savePath);
        } else {
            statusLabel.setText("Збереження скасовано.");
        }
    }


    private void drawContent(Graphics g) {
        if (currentImage != null && currentImage.getBufferedImage() != null) {
            g.drawImage(currentImage.getBufferedImage(), 0, 0, null);
        }
        Map<Long, Layer> layers = layerService.getAllLayers();
        for (Layer layer : layers.values()) {
            if (layer.isVisible()) {
                g.drawImage(layer.getContent(), 0, 0, null);
            }
        }
        for (GraphicElement graphicElement : graphicElements) {
            graphicElement.draw(g);
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Помилка", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphicEditorGUI editor = new GraphicEditorGUI();
            editor.setVisible(true);
        });
    }
}