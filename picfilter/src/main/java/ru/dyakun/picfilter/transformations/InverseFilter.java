package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.ImageTransformation;

import java.awt.image.BufferedImage;

public class InverseFilter implements ImageTransformation {

    @Override
    public BufferedImage apply(BufferedImage src, BufferedImage dst) {
        int alpha = 0xFF;
        for (int i = 0; i < src.getHeight(); i++) {
            for (int j = 0; j < src.getWidth(); j++) {
                int c = src.getRGB(j, i);
                c = ~(c << 8);
                c = (c >> 8) + (alpha << 24);
                dst.setRGB(j, i, c);
            }
        }
        return dst;
    }

}
