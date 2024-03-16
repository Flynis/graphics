package ru.dyakun.picfilter.model;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface ImageTransformation {

    BufferedImage apply(BorderImage src, BufferedImage dst);

}
