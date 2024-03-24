package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.model.ImageTransformation;
import ru.dyakun.picfilter.model.proprerty.IntegerProperty;

import java.awt.image.BufferedImage;

public class ChromaticAberration implements ImageTransformation {

    public final IntegerProperty red = new IntegerProperty(5, -128, 128, "Red");
    public final IntegerProperty green = new IntegerProperty(4, -128, 128, "Green");
    public final IntegerProperty blue = new IntegerProperty(2, -128, 128, "Blue");

    private int cutOutOfRange(int idx, int w) {
        if(idx < 0) {
            return 0;
        }
        return Math.min(idx, w - 1);
    }

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        src.fillBorders(BorderImage.BorderType.Repeat);
        int w = src.getWidth();
        int h = src.getHeight();
        int alpha = 0xFF;
        for(int y = 0; y < h; y++) {
            for(int x = 0; x < w; x++) {
                int idx = x + red.getVal();
                int rc = ((src.getRGB(cutOutOfRange(idx, w), y) >> 16) & 0xFF);
                idx = x + green.getVal();
                int gc = ((src.getRGB(cutOutOfRange(idx, w), y) >> 8) & 0xFF);
                idx = x + blue.getVal();
                int bc = (src.getRGB(cutOutOfRange(idx, w), y) & 0xFF);
                int c = (alpha << 24) + (rc << 16) + (gc << 8) + bc;
                dst.setRGB(x, y, c);
            }
        }
        return dst;
    }

}
