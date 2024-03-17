package ru.dyakun.picfilter.model;

import ru.dyakun.picfilter.transformations.base.TransformationUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BorderImage {

    public static final int BORDER_SIZE = TransformationUtil.KERNEL_SIZE_MAX / 2;
    private final BufferedImage image;
    private final int width;
    private final int height;
    private BorderType type;

    public BorderImage(BufferedImage src) {
        int newWidth = src.getWidth() + 2 * BORDER_SIZE;
        int newHeight = src.getHeight() + 2 * BORDER_SIZE;
        width = src.getWidth();
        height = src.getHeight();
        if(width < BORDER_SIZE || height < BORDER_SIZE) {
            throw new IllegalArgumentException("Too small image");
        }
        image = new BufferedImage(newWidth, newHeight, Image.SCALE_DEFAULT);
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int c = src.getRGB(x, y);
                image.setRGB(x + BORDER_SIZE, y + BORDER_SIZE, c);
            }
        }
        fillMirror();
        type = BorderType.Mirror;
    }

    public int getRGB(int x, int y) {
        return image.getRGB(x + BORDER_SIZE, y + BORDER_SIZE);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public enum BorderType {
        Mirror,
    }

    public void fillBorders(BorderType borderType) {
        if(type == borderType) {
            return;
        }
        switch (borderType) {
            case Mirror -> fillMirror();
        }
        type = borderType;
    }

    private void fillMirror() {
        for(int y = 0; y < BORDER_SIZE; y++) {
            for(int x = BORDER_SIZE; x < width + BORDER_SIZE; x++) {
                // upper
                int c = image.getRGB(x, 2 * BORDER_SIZE - y - 1);
                image.setRGB(x, y, c);
                // lower
                c = image.getRGB(x, height + y);
                image.setRGB(x, image.getHeight() - y - 1, c);
            }
        }
        for(int y = BORDER_SIZE; y < height + BORDER_SIZE; y++) {
            for(int x = 0; x < BORDER_SIZE; x++) {
                // left
                int c = image.getRGB(2 * BORDER_SIZE - x - 1, y);
                image.setRGB(x, y, c);
                // right
                c = image.getRGB(width + x, y);
                image.setRGB(image.getWidth() - x - 1, y, c);
            }
        }
        for(int y = 0; y < BORDER_SIZE; y++) {
            for(int x = 0; x < BORDER_SIZE; x++) {
                // left upper
                int c = image.getRGB(2 * BORDER_SIZE - x - 1, y);
                image.setRGB(x, y, c);
                // left bottom
                c = image.getRGB(2 * BORDER_SIZE - x - 1, height + BORDER_SIZE + y);
                image.setRGB(x, height + BORDER_SIZE + y, c);
                // right upper
                c = image.getRGB(width + x, y);
                image.setRGB(image.getWidth() - x - 1, y, c);
                // right bottom
                c = image.getRGB(width + x, height + BORDER_SIZE + y);
                image.setRGB(image.getWidth() - x - 1, height + BORDER_SIZE + y, c);
            }
        }
    }

}
