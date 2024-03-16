package ru.dyakun.picfilter.model;

import java.awt.image.BufferedImage;

public class Filter {

    private Filter() {
        throw new AssertionError();
    }

    public static final int KERNEL_SIZE_MAX = 11;
    public static final Kernel kernel = new Kernel(KERNEL_SIZE_MAX);

    private static int round(int c) {
        if(c > 255) {
            return 255;
        } else {
            return Math.max(c, 0);
        }
    }

    public static BufferedImage apply(int sz, BorderImage src, BufferedImage dst) {
        ChannelsObserver observer = ChannelsObserver.getInstance();
        int alpha = 0xFF;
        if(observer.red()) {
            for(int y0 = 0; y0 < src.getHeight(); y0++) {
                for(int x0 = 0; x0 < src.getWidth(); x0++) {
                    int sum = 0;
                    for(int y = 0; y < sz; y++) {
                        for(int x = 0; x < sz; x++) {
                            int c = src.getRGB(x0 - sz / 2 + x, y0 - sz / 2 + y);
                            sum += ((c >> 16) & 0xFF) * kernel.get(x, y);
                        }
                    }
                    int result = round(sum / kernel.getDiv());
                    int old = dst.getRGB(x0, y0);
                    int c = (alpha << 24) + (result << 16) + (old & 0x00FFFF);
                    dst.setRGB(x0, y0, c);
                }
            }
        }
        if(observer.green()) {
            for(int y0 = 0; y0 < src.getHeight(); y0++) {
                for(int x0 = 0; x0 < src.getWidth(); x0++) {
                    int sum = 0;
                    for(int y = 0; y < sz; y++) {
                        for(int x = 0; x < sz; x++) {
                            int c = src.getRGB(x0 - sz / 2 + x, y0 - sz / 2 + y);
                            sum += ((c >> 8) & 0xFF) * kernel.get(x, y);
                        }
                    }
                    int result = round(sum / kernel.getDiv());
                    int old = dst.getRGB(x0, y0);
                    int c = (alpha << 24) + (result << 8) + (old & 0xFF00FF);
                    dst.setRGB(x0, y0, c);
                }
            }
        }
        if(observer.blue()) {
            for(int y0 = 0; y0 < src.getHeight(); y0++) {
                for(int x0 = 0; x0 < src.getWidth(); x0++) {
                    int sum = 0;
                    for(int y = 0; y < sz; y++) {
                        for(int x = 0; x < sz; x++) {
                            int c = src.getRGB(x0 - sz / 2 + x, y0 - sz / 2 + y);
                            sum += (c & 0xFF) * kernel.get(x, y);
                        }
                    }
                    int result = round(sum / kernel.getDiv());
                    int old = dst.getRGB(x0, y0);
                    int c = (alpha << 24) + result + (old & 0xFFFF00);
                    dst.setRGB(x0, y0, c);
                }
            }
        }
        return dst;
    }

}
