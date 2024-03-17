package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.transformations.base.MatrixFilter;
import ru.dyakun.picfilter.model.ImageTransformation;
import ru.dyakun.picfilter.model.proprerty.IntegerProperty;

import java.awt.image.BufferedImage;

public class Blur extends MatrixFilter implements ImageTransformation {

    public IntegerProperty kernelSize = new IntegerProperty(3, 3, 11, "Kernel size");

    private static final int[] kernel3 = {
            0, 1, 0,
            1, 2, 1,
            0, 1, 0 };

    private static final int[] kernel5 = {
            1,  5,  8,  5, 1,
            5, 21, 34, 21, 5,
            8, 34, 55, 34, 8,
            5, 21, 34, 21, 5,
            1,  5,  8,  5, 1 };

    public Blur() {
        super(11);
        kernelSize.setMustBeOdd(true);
    }

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        int size = kernelSize.getVal();
        src.fillBorders(BorderImage.BorderType.Mirror);
        switch (size) {
            case 3 -> {
                kernel.copy(kernel3);
                return applyKernel(size, 6, false, src, dst);
            }
            case 5 -> {
                kernel.copy(kernel5);
                return applyKernel(size, 351,false, src, dst);
            }
            default -> {
                for(int y = 0; y < size; y++) {
                    for(int x = 0; x < size; x++) {
                        kernel.set(x, y, 1, size);
                    }
                }
                return applyKernel(size, size * size, false, src, dst);
            }
        }
    }

}
