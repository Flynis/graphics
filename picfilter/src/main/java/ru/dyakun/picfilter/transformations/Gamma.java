package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.model.ImageTransformation;
import ru.dyakun.picfilter.model.proprerty.DoubleProperty;
import ru.dyakun.picfilter.transformations.base.ChannelProcessor;
import ru.dyakun.picfilter.transformations.base.TransformationUtil;

import java.awt.image.BufferedImage;

public class Gamma implements ImageTransformation, ChannelProcessor {

    public final DoubleProperty gamma = new DoubleProperty(0.1, 0.1, 10.0, "Gamma");

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        return TransformationUtil.applyByChannels(this, src, dst);
    }

    @Override
    public int red(int x, int y, BorderImage src, BufferedImage dst) {
        int red = (src.getRGB(x, y) >> 16) & 0xFF;
        double res = Math.pow(red, gamma.getVal());
        return TransformationUtil.round((int) Math.round(res));
    }

    @Override
    public int green(int x, int y, BorderImage src, BufferedImage dst) {
        int green = (src.getRGB(x, y) >> 8) & 0xFF;
        double res = Math.pow(green, gamma.getVal());
        return TransformationUtil.round((int) Math.round(res));
    }

    @Override
    public int blue(int x, int y, BorderImage src, BufferedImage dst) {
        int blue = src.getRGB(x, y) & 0xFF;
        double res = Math.pow(blue, gamma.getVal());
        return TransformationUtil.round((int) Math.round(res));
    }

}
