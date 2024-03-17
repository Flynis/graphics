package ru.dyakun.picfilter.transformations.base;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.model.ChannelsObserver;

import java.awt.image.BufferedImage;

public class TransformationUtil {

    private TransformationUtil() {
        throw new AssertionError();
    }

    public static final int KERNEL_SIZE_MAX = 11;

    public static int round(int c) {
        if(c > 255) {
            return 255;
        } else {
            return Math.max(c, 0);
        }
    }

    public static BufferedImage applyByChannels(ChannelProcessor processor,
                                                BorderImage src,
                                                BufferedImage dst) {
        ChannelsObserver observer = ChannelsObserver.getInstance();
        int alpha = 0xFF;
        for(int y = 0; y < src.getHeight(); y++) {
            for(int x = 0; x < src.getWidth(); x++) {
                int p = src.getRGB(x, y);
                int red = observer.red() ? processor.red(x, y, src, dst) : ((p >> 16) & 0xFF);
                int green = observer.green() ? processor.green(x, y, src, dst) : ((p >> 8) & 0xFF);
                int blue = observer.blue() ? processor.blue(x, y, src, dst) : (p & 0xFF);
                int c = (alpha << 24) + (red << 16) + (green << 8) + blue;
                dst.setRGB(x, y, c);
            }
        }
        return dst;
    }

}
