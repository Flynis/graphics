package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.model.Filter;
import ru.dyakun.picfilter.model.ImageTransformation;
import ru.dyakun.picfilter.model.Kernel;
import ru.dyakun.picfilter.model.proprerty.IntegerProperty;

import java.awt.image.BufferedImage;

public class Blur implements ImageTransformation {

    public IntegerProperty kernelSize = new IntegerProperty(3, 3, 11, "Kernel size");

    private static final int[] kernel3 = {
            1, 2, 1,
            2, 3, 2,
            1, 2, 1 };

    private static final int[] kernel5 = {
            1,  5,  8,  5, 1,
            5, 21, 34, 21, 5,
            8, 34, 55, 34, 8,
            5, 21, 34, 21, 5,
            1,  5,  8,  5, 1 };

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        Kernel kernel = Filter.kernel;
        int size = kernelSize.getVal();
        switch (size) {
            case 3 -> {
                kernel.copy(kernel3);
                kernel.setDiv(15);
                return Filter.apply(size, src, dst);
            }
            case 5 -> {
                kernel.copy(kernel5);
                kernel.setDiv(351);
                return Filter.apply(size, src, dst);
            }
            default -> {
                for(int y = 0; y < size; y++) {
                    for(int x = 0; x < size; x++) {
                        kernel.set(x, y, 1);
                    }
                }
                kernel.setDiv(size * size);
                return Filter.apply(size, src, dst);
            }
        }
    }

}
