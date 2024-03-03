package ru.dyakun.picfilter.filter;

import ru.dyakun.picfilter.model.Filter;

import java.awt.image.BufferedImage;

public class BlackWhiteFilter implements Filter {

    @Override
    public BufferedImage apply(BufferedImage src, BufferedImage dst) {
        double r = 0.299;
        double g = 0.587;
        double b = 1 - r - g;
        for (int i = 0; i < src.getHeight(); i++) {
            for (int j = 0; j < src.getWidth(); j++) {
                int c = src.getRGB(j, i);
                int y = (int) (r * (c >> 16) +
                               g * ((c >> 8) & 0xFF) +
                               b * (c  & 0xFF));
                c = y + (y << 8) + (y << 16);
                dst.setRGB(j , i, c);
            }
        }
        return dst;
    }

}
