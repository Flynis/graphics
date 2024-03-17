package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.model.ImageTransformation;
import ru.dyakun.picfilter.model.proprerty.IntegerProperty;
import ru.dyakun.picfilter.transformations.base.Operator;

import java.awt.image.BufferedImage;

public class Roberts extends Operator implements ImageTransformation {

    private static final int[] kernel3X = {
            0,  0, 0,
            0, -1, 0,
            0,  0, 1 };
    private static final int[] kernel3Y = {
            0, 0,  0,
            0, 0, -1,
            0, 1,  0 };

    public final IntegerProperty threshold = new IntegerProperty(128, 0, 255, "Threshold");

    public Roberts() {
        kernelX.copy(kernel3X);
        kernelY.copy(kernel3Y);
    }

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        src.fillBorders(BorderImage.BorderType.Zero);
        return apply(threshold.getVal(), src, dst);
    }

}
