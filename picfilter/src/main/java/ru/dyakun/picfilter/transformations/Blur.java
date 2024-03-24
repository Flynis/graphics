package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.transformations.base.MatrixFilter;
import ru.dyakun.picfilter.model.ImageTransformation;
import ru.dyakun.picfilter.model.proprerty.IntegerProperty;

import java.awt.image.BufferedImage;

public class Blur extends MatrixFilter implements ImageTransformation {

    public final IntegerProperty kernelSize = new IntegerProperty(3, 3, 11, "Kernel size");

    private static final int[] kernel3 = {
            1, 2, 1,
            2, 4, 2,
            1, 2, 1 };

    private static final int[] kernel5 = {
            1,  4,  7,  4, 1,
            4, 16, 26, 16, 4,
            7, 26, 41, 26, 7,
            4, 16, 26, 16, 4,
            1,  4,  7,  4, 1 };

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
                return applyKernel(size, 16, src, dst);
            }
            case 5 -> {
                kernel.copy(kernel5);
                return applyKernel(size, 273, src, dst);
            }
            default -> {
                for(int y = 0; y < size; y++) {
                    for(int x = 0; x < size; x++) {
                        kernel.set(x, y, 1, size);
                    }
                }
                return applyKernel(size, size * size, src, dst);
            }
        }
    }

}
