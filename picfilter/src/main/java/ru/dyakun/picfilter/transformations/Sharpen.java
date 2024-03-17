package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.model.ImageTransformation;
import ru.dyakun.picfilter.transformations.base.MatrixFilter;

import java.awt.image.BufferedImage;

public class Sharpen extends MatrixFilter implements ImageTransformation {

    private static final int[] kernel3 = {
            -1, -1, -1,
            -1,  9, -1,
            -1, -1, -1 };

    public Sharpen() {
        super(3);
        kernel.copy(kernel3);
    }

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        return applyKernel(3, 1, src, dst);
    }

}
