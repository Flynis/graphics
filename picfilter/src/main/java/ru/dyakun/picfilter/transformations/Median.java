package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.model.ImageTransformation;
import ru.dyakun.picfilter.model.proprerty.IntegerProperty;
import ru.dyakun.picfilter.transformations.base.ChannelProcessor;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import static ru.dyakun.picfilter.transformations.base.TransformationUtil.applyByChannels;

public class Median implements ImageTransformation, ChannelProcessor {

    public IntegerProperty kernelSize = new IntegerProperty(3, 3, 11, "Kernel size");
    private int[] arr;
    private int size;

    public Median() {
        kernelSize.setMustBeOdd(true);
    }

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        size = kernelSize.getVal();
        if(arr == null || arr.length != size * size) {
            arr = new int[size * size];
        }
        return applyByChannels(this, src, dst);
    }

    @Override
    public int red(int x0, int y0, BorderImage src, BufferedImage dst) {
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                int c = src.getRGB(x0 - size / 2 + x, y0 - size / 2 + y);
                arr[y * size + x] = (c >> 16) & 0xFF;
            }
        }
        Arrays.sort(arr);
        return arr[(size + 1) / 2];
    }

    @Override
    public int green(int x0, int y0, BorderImage src, BufferedImage dst) {
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                int c = src.getRGB(x0 - size / 2 + x, y0 - size / 2 + y);
                arr[y * size + x] = (c >> 8) & 0xFF;
            }
        }
        Arrays.sort(arr);
        return arr[(size + 1) / 2];
    }

    @Override
    public int blue(int x0, int y0, BorderImage src, BufferedImage dst) {
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                int c = src.getRGB(x0 - size / 2 + x, y0 - size / 2 + y);
                arr[y * size + x] = c & 0xFF;
            }
        }
        Arrays.sort(arr);
        return arr[(size + 1) / 2];
    }

}
