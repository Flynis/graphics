package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.*;
import ru.dyakun.picfilter.transformations.base.ChannelProcessor;
import ru.dyakun.picfilter.transformations.base.TransformationUtil;

import java.awt.image.BufferedImage;

public class InverseFilter implements ImageTransformation, ChannelProcessor {

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        return TransformationUtil.applyByChannels(this, src, dst);
    }

    @Override
    public int red(int x, int y, BorderImage src, BufferedImage dst) {
        int red = (src.getRGB(x, y) >> 16) & 0xFF;
        return 255 - red;
    }

    @Override
    public int green(int x, int y, BorderImage src, BufferedImage dst) {
        int green = (src.getRGB(x, y) >> 8) & 0xFF;
        return 255 - green;
    }

    @Override
    public int blue(int x, int y, BorderImage src, BufferedImage dst) {
        int blue = src.getRGB(x, y) & 0xFF;
        return 255 - blue;

    }

}
