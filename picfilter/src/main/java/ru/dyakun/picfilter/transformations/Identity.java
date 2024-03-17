package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.model.ChannelsObserver;
import ru.dyakun.picfilter.model.ImageTransformation;

import java.awt.image.BufferedImage;

public class Identity implements ImageTransformation {

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        ChannelsObserver observer = ChannelsObserver.getInstance();
        int alpha = 0xFF;
        for(int y = 0; y < src.getHeight(); y++) {
            for(int x = 0; x < src.getWidth(); x++) {
                int p = src.getRGB(x, y);
                int red = observer.red() ? ((p >> 16) & 0xFF) : 0;
                int green = observer.green() ? ((p >> 8) & 0xFF) : 0;
                int blue = observer.blue() ? (p & 0xFF) : 0;
                int c = (alpha << 24) + (red << 16) + (green << 8) + blue;
                dst.setRGB(x, y, c);
            }
        }
        return dst;
    }

}
