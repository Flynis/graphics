package ru.dyakun.picfilter.model;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface Filter {

    BufferedImage apply(BufferedImage src, BufferedImage dst);

}
