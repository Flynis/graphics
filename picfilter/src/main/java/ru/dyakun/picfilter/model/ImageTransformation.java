package ru.dyakun.picfilter.model;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface ImageTransformation {

    BufferedImage apply(BufferedImage src, BufferedImage dst);

}
