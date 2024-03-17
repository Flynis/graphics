package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.model.ChannelsObserver;
import ru.dyakun.picfilter.model.ImageTransformation;
import ru.dyakun.picfilter.model.proprerty.IntegerProperty;
import ru.dyakun.picfilter.transformations.base.MatrixFilter;

import java.awt.image.BufferedImage;

import static ru.dyakun.picfilter.transformations.base.TransformationUtil.round;

public class Emboss extends MatrixFilter implements ImageTransformation {

    public final IntegerProperty azimuth = new IntegerProperty(0, 0, 360, "Azimuth");
    public final IntegerProperty offset = new IntegerProperty(128, 0, 255, "Offset");
    private static final int size = 3;
    private static final int[] shift = { 0, 1, 2, 5, 8, 7, 6, 3 };

    public Emboss() {
        super(size);
    }

    private void setValue(int val, int pos) {
        pos = shift[pos % shift.length];
        int x = pos % size;
        int y = pos / size;
        kernel.set(x, y, val, size);
    }

    private void fillKernel() {
        int angle = azimuth.getVal() / 45;
        // rotate kernel
        //  1  2  1
        //  0  0  0
        // -1 -2 -1
        setValue(1, angle);
        setValue(2, 1 + angle);
        setValue(1, 2 + angle);
        setValue(0, 3 + angle);
        setValue(-1, 4 + angle);
        setValue(-2, 5 + angle);
        setValue(-1, 6 + angle);
        setValue(0, 7 + angle);
    }

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        fillKernel();
        dst = applyKernel(size, 1, false, src, dst);
        int off = offset.getVal();
        int alpha = 0xFF;
        ChannelsObserver observer = ChannelsObserver.getInstance();
        for (int i = 0; i < dst.getHeight(); i++) {
            for (int j = 0; j < dst.getWidth(); j++) {
                int c = dst.getRGB(j, i);
                int red = (c >> 16) & 0xFF;
                if(observer.red()) {
                    red = round(red + off);
                }
                int green = (c >> 8) & 0xFF;
                if(observer.green()) {
                    green = round(green + off);
                }
                int blue = c & 0xFF;
                if(observer.blue()) {
                    blue = round(blue + off);
                }
                c = (alpha << 24) + (red << 16) + (green << 8) + blue;
                dst.setRGB(j , i, c);
            }
        }
        return dst;
    }

}
