package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.ImageTransformation;

import java.awt.image.BufferedImage;

public class BlackWhiteFilter implements ImageTransformation {

    @Override
    public BufferedImage apply(BufferedImage src, BufferedImage dst) {
        double r = 0.299;
        double g = 0.587;
        double b = 1.0 - r - g;
        int alpha = 0xFF;
        for (int i = 0; i < src.getHeight(); i++) {
            for (int j = 0; j < src.getWidth(); j++) {
                int c = src.getRGB(j, i);
                int y = (int) (r * ((c >> 16) & 0xFF) +
                               g * ((c >> 8) & 0xFF) +
                               b * (c  & 0xFF));
                c = (alpha << 24) + (y << 16) + (y << 8) + y;
                dst.setRGB(j , i, c);
            }
        }
        return dst;
    }

}
