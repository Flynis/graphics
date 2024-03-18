package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.model.ImageTransformation;
import ru.dyakun.picfilter.model.proprerty.IntegerProperty;
import ru.dyakun.picfilter.transformations.base.ChannelProcessor;

import java.awt.image.BufferedImage;

import static ru.dyakun.picfilter.transformations.base.TransformationUtil.palette;

public class Dithering implements ImageTransformation, ChannelProcessor {

    public final IntegerProperty red = new IntegerProperty(2, 2, 128, "Red");
    public final IntegerProperty green = new IntegerProperty(2, 2, 128, "Green");
    public final IntegerProperty blue = new IntegerProperty(2, 2, 128, "Blue");

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        return dst;
    }

    @Override
    public int red(int x, int y, BorderImage src, BufferedImage dst) {
        int p = (src.getRGB(x, y) >> 16) & 0xFF;
        int c = palette(p, red.getVal());

        return 0;
    }

    @Override
    public int green(int x, int y, BorderImage src, BufferedImage dst) {
        return 0;
    }

    @Override
    public int blue(int x, int y, BorderImage src, BufferedImage dst) {
        return 0;
    }

}
