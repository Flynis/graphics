package ru.dyakun.picfilter.filter;

import ru.dyakun.picfilter.model.Filter;

import java.awt.image.BufferedImage;

public class InverseFilter implements Filter {

    @Override
    public BufferedImage apply(BufferedImage src, BufferedImage dst) {
        for (int i = 0; i < src.getHeight(); i++) {
            for (int j = 0; j < src.getWidth(); j++) {
                int c = src.getRGB(j, i);
                c = ~c & 0xFFFFFF;
                dst.setRGB(j, i, c);
            }
        }
        return dst;
    }

}
