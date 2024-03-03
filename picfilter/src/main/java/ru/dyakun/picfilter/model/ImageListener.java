package ru.dyakun.picfilter.model;

import java.awt.image.BufferedImage;

public interface ImageListener {

    void onChange(BufferedImage image);

    void onSourceChange(BufferedImage image);

}
