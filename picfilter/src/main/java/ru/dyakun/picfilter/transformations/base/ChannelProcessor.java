package ru.dyakun.picfilter.transformations.base;

import ru.dyakun.picfilter.model.BorderImage;

import java.awt.image.BufferedImage;

public interface ChannelProcessor {

    int red(int x, int y, BorderImage src, BufferedImage dst);

    int green(int x, int y, BorderImage src, BufferedImage dst);

    int blue(int x, int y, BorderImage src, BufferedImage dst);

}
