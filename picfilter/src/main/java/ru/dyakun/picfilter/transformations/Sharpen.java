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

    protected static final int size = 3;

    public Sharpen() {
        super(size);
        kernel.copy(kernel3);
    }

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        src.fillBorders(BorderImage.BorderType.Mirror);
        return applyKernel(size, 1, src, dst);
    }

}
