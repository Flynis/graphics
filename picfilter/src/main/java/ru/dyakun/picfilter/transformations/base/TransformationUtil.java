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
        if(observer.red()) {
            for(int y = 0; y < src.getHeight(); y++) {
                for(int x = 0; x < src.getWidth(); x++) {
                    int p = src.getRGB(x, y);
                    int red = processor.red(x, y, src, dst);
                    int c = (alpha << 24) + (red << 16) + (p & 0x00FFFF);
                    dst.setRGB(x, y, c);
                }
            }
        }
        if(observer.green()) {
            for(int y = 0; y < src.getHeight(); y++) {
                for(int x = 0; x < src.getWidth(); x++) {
                    int p = src.getRGB(x, y);
                    int red = (((observer.red()) ? dst.getRGB(x, y) : p) >> 16) & 0xFF;
                    int green = processor.green(x, y, src, dst);
                    int c = (alpha << 24) + (red << 16) + (green << 8) + (p & 0xFF);
                    dst.setRGB(x, y, c);
                }
            }
        }
        if(observer.blue()) {
            for(int y = 0; y < src.getHeight(); y++) {
                for(int x = 0; x < src.getWidth(); x++) {
                    int p = src.getRGB(x, y);
                    int red = (((observer.red()) ? dst.getRGB(x, y) : p) >> 16) & 0xFF;
                    int green = (((observer.green()) ? dst.getRGB(x, y) : p) >> 8) & 0xFF;
                    int blue = processor.blue(x, y, src, dst);
                    int c = (alpha << 24) + (red << 16) + (green << 8) + blue;
                    dst.setRGB(x, y, c);
                }
            }
        }
        return dst;
    }

}
