package ru.dyakun.picfilter.transformations.base;

import ru.dyakun.picfilter.model.BorderImage;

import java.awt.image.BufferedImage;

import static ru.dyakun.picfilter.transformations.base.TransformationUtil.applyByChannels;
import static ru.dyakun.picfilter.transformations.base.TransformationUtil.round;

public class MatrixFilter implements ChannelProcessor {

    protected final Matrix kernel;
    private int size = 1;
    private int div = 1;

    public MatrixFilter(int maxKernelSize) {
        kernel = new Matrix(maxKernelSize, maxKernelSize);
    }

    @Override
    public int red(int x0, int y0, BorderImage src, BufferedImage dst) {
        int sum = 0;
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                int c = src.getRGB(x0 - size / 2 + x, y0 - size / 2 + y);
                sum += ((c >> 16) & 0xFF) * kernel.get(x, y, size);
            }
        }
        return round(sum / div);
    }

    @Override
    public int green(int x0, int y0, BorderImage src, BufferedImage dst) {
        int sum = 0;
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                int c = src.getRGB(x0 - size / 2 + x, y0 - size / 2 + y);
                sum += ((c >> 8) & 0xFF) * kernel.get(x, y, size);
            }
        }
        return round(sum / div);
    }

    @Override
    public int blue(int x0, int y0, BorderImage src, BufferedImage dst) {
        int sum = 0;
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                int c = src.getRGB(x0 - size / 2 + x, y0 - size / 2 + y);
                sum += (c & 0xFF) * kernel.get(x, y, size);
            }
        }
        return round(sum / div);
    }

    protected BufferedImage applyKernel(int size, int div, boolean ignoreDisabledChannels, BorderImage src, BufferedImage dst) {
        this.size = size;
        this.div = div;
        return applyByChannels(this, ignoreDisabledChannels, src, dst);
    }

}
