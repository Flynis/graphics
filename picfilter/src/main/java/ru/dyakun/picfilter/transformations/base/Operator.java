package ru.dyakun.picfilter.transformations.base;

import ru.dyakun.picfilter.model.BorderImage;

import java.awt.image.BufferedImage;

import static ru.dyakun.picfilter.transformations.base.TransformationUtil.applyByChannels;

public class Operator implements ChannelProcessor {

    private final int size = 3;
    protected final Matrix kernelX;
    protected final Matrix kernelY;
    private int threshold = 1;

    public Operator() {
        kernelX = new Matrix(size, size);
        kernelY = new Matrix(size, size);
    }

    @Override
    public int red(int x0, int y0, BorderImage src, BufferedImage dst) {
        int gx = 0;
        int gy = 0;
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                int c = src.getRGB(x0 - size / 2 + x, y0 - size / 2 + y);
                c = (c >> 16) & 0xFF;
                gx += c * kernelX.get(x, y, size);
                gy += c * kernelY.get(x, y, size);
            }
        }
        int f = Math.abs(gx) + Math.abs(gy);
        return (f > threshold) ? 255 : 0;
    }

    @Override
    public int green(int x0, int y0, BorderImage src, BufferedImage dst) {
        int gx = 0;
        int gy = 0;
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                int c = src.getRGB(x0 - size / 2 + x, y0 - size / 2 + y);
                c = (c >> 8) & 0xFF;
                gx += c * kernelX.get(x, y, size);
                gy += c * kernelY.get(x, y, size);
            }
        }
        int f = Math.abs(gx) + Math.abs(gy);
        return (f > threshold) ? 255 : 0;
    }

    @Override
    public int blue(int x0, int y0, BorderImage src, BufferedImage dst) {
        int gx = 0;
        int gy = 0;
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                int c = src.getRGB(x0 - size / 2 + x, y0 - size / 2 + y);
                c = c & 0xFF;
                gx += c * kernelX.get(x, y, size);
                gy += c * kernelY.get(x, y, size);
            }
        }
        int f = Math.abs(gx) + Math.abs(gy);
        return (f > threshold) ? 255 : 0;
    }

    protected BufferedImage apply(int threshold, BorderImage src, BufferedImage dst) {
        this.threshold = threshold;
        return applyByChannels(this, src, dst);
    }

}
