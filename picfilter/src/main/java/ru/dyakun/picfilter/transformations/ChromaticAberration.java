package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.model.ImageTransformation;
import ru.dyakun.picfilter.model.proprerty.IntegerProperty;

import java.awt.image.BufferedImage;

public class ChromaticAberration implements ImageTransformation {

    public final IntegerProperty red = new IntegerProperty(5, -128, 128, "Red");
    public final IntegerProperty green = new IntegerProperty(4, -128, 128, "Green");
    public final IntegerProperty blue = new IntegerProperty(2, -128, 128, "Blue");

    private int signedRemainder(int idx, int w) {
        int remainder = idx % w;
        return (idx < 0) ? w + remainder : remainder;
    }

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        src.fillBorders(BorderImage.BorderType.Repeat);
        int w = src.getWidth();
        int h = src.getHeight();
        int alpha = 0xFF;
        for(int i = 0; i < w * h; i++) {
            int idx = i + red.getVal();
            int rc = ((src.getRGB(signedRemainder(idx, w), idx / w) >> 16) & 0xFF);
            idx = i + green.getVal();
            int gc = ((src.getRGB(signedRemainder(idx, w), idx / w) >> 8) & 0xFF);
            idx = i + blue.getVal();
            int bc = (src.getRGB(signedRemainder(idx, w), idx / w) & 0xFF);
            int c = (alpha << 24) + (rc << 16) + (gc << 8) + bc;
            dst.setRGB(i % w, i / w, c);
        }
        return dst;
    }

}
