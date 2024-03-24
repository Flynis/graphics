package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.model.ChannelsObserver;
import ru.dyakun.picfilter.model.ImageTransformation;
import ru.dyakun.picfilter.model.proprerty.IntegerProperty;
import ru.dyakun.picfilter.transformations.base.ChannelProcessor;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import static ru.dyakun.picfilter.transformations.base.TransformationUtil.palette;

public class Dithering implements ImageTransformation, ChannelProcessor {

    public final IntegerProperty red = new IntegerProperty(2, 2, 128, "Red");
    public final IntegerProperty green = new IntegerProperty(2, 2, 128, "Green");
    public final IntegerProperty blue = new IntegerProperty(2, 2, 128, "Blue");

    private int redNextErr;
    private int greenNextErr;
    private int blueNextErr;
    private int[] redErr;
    private int[] redCurErr;
    private int[] greenErr;
    private int[] greenCurErr;
    private int[] blueErr;
    private int[] blueCurErr;
    private final double a = 7.0 / 16.0;
    private final double b = 5.0 / 16.0;
    private final double c = 3.0 / 16.0;
    private final double d = 1 - a - b - c;

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        redNextErr = 0;
        greenNextErr = 0;
        blueNextErr = 0;
        redErr = new int[src.getWidth() + 2];
        redCurErr = new int[src.getWidth() + 2];
        greenErr = new int[src.getWidth() + 2];
        greenCurErr = new int[src.getWidth() + 2];
        blueErr = new int[src.getWidth() + 2];
        blueCurErr = new int[src.getWidth() + 2];
        return applyByChannels(this, src, dst);
    }

    private BufferedImage applyByChannels(ChannelProcessor processor,
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
            var tmp = redErr;
            redErr = redCurErr;
            redCurErr = tmp;
            Arrays.fill(redErr, 0);
            tmp = greenErr;
            greenErr = greenCurErr;
            greenCurErr = tmp;
            Arrays.fill(greenErr, 0);
            tmp = blueErr;
            blueErr = blueCurErr;
            blueCurErr = tmp;
            Arrays.fill(blueErr, 0);
        }
        return dst;
    }

    @Override
    public int red(int x, int y, BorderImage src, BufferedImage dst) {
        int p = ((src.getRGB(x, y) >> 16) & 0xFF) + redNextErr + redCurErr[x + 1];
        int processed = palette(p, red.getVal());
        int e = p - processed;
        redNextErr = (int) Math.round(e * a);
        redErr[x] += (int) Math.round(e * c);
        redErr[x + 1] += (int) Math.round(e * b);
        redErr[x + 2] += (int) Math.round(e * d);
        return processed;
    }

    @Override
    public int green(int x, int y, BorderImage src, BufferedImage dst) {
        int p = ((src.getRGB(x, y) >> 8) & 0xFF) + greenNextErr + greenCurErr[x + 1];
        int processed = palette(p, green.getVal());
        int e = p - processed;
        greenNextErr = (int) Math.round(e * a);
        greenErr[x] += (int) Math.round(e * c);
        greenErr[x + 1] += (int) Math.round(e * b);
        greenErr[x + 2] += (int) Math.round(e * d);
        return processed;
    }

    @Override
    public int blue(int x, int y, BorderImage src, BufferedImage dst) {
        int p = (src.getRGB(x, y) & 0xFF) + blueNextErr + blueCurErr[x + 1];
        int processed = palette(p, blue.getVal());
        int e = p - processed;
        blueNextErr = (int) Math.round(e * a);
        blueErr[x] += (int) Math.round(e * c);
        blueErr[x + 1] += (int) Math.round(e * b);
        blueErr[x + 2] += (int) Math.round(e * d);
        return processed;
    }

}
