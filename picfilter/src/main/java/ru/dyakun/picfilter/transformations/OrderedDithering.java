package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.model.ImageTransformation;

import java.awt.image.BufferedImage;

public class OrderedDithering implements ImageTransformation {

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        return dst;
    }

}
