package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.model.ImageTransformation;
import ru.dyakun.picfilter.model.proprerty.IntegerProperty;

import java.awt.image.BufferedImage;

public class ChromaticAberration implements ImageTransformation {

    public final IntegerProperty red = new IntegerProperty(5, -128, 128, "Red");
    public final IntegerProperty green = new IntegerProperty(0, -128, 128, "Green");
    public final IntegerProperty blue = new IntegerProperty(-3, -128, 128, "Blue");

    private int cutOutOfRange(int x, int w, int offset) {
        int idx = x + offset;
        if(idx < 0 || idx > w - 1) {
            return x;
        }
        return idx;
    }

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        src.fillBorders(BorderImage.BorderType.Repeat);
        int w = src.getWidth();
        int h = src.getHeight();
        int alpha = 0xFF;
        for(int y = 0; y < h; y++) {
            for(int x = 0; x < w; x++) {
                int rc = ((src.getRGB(cutOutOfRange(x, w, red.getVal()), y) >> 16) & 0xFF);
                int gc = ((src.getRGB(cutOutOfRange(x, w, green.getVal()), y) >> 8) & 0xFF);
                int bc = (src.getRGB(cutOutOfRange(x, w, blue.getVal()), y) & 0xFF);
                int c = (alpha << 24) + (rc << 16) + (gc << 8) + bc;
                dst.setRGB(x, y, c);
            }
        }
        return dst;
    }

}
