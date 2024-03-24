package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.model.ImageTransformation;
import ru.dyakun.picfilter.model.proprerty.IntegerProperty;
import ru.dyakun.picfilter.transformations.base.ChannelProcessor;

import java.awt.image.BufferedImage;

import static ru.dyakun.picfilter.transformations.base.TransformationUtil.*;

public class OrderedDithering implements ImageTransformation, ChannelProcessor {

    public final IntegerProperty red = new IntegerProperty(2, 2, 128, "Red");
    public final IntegerProperty green = new IntegerProperty(2, 2, 128, "Green");
    public final IntegerProperty blue = new IntegerProperty(2, 2, 128, "Blue");

    private final int[] m2 = new int[] {
            0, 2,
            3, 1
    };
    private final int[] m4 = new int[4 * 4];
    private final int[] m8 = new int[8 * 8];
    private final int[] m16 = new int[16 * 16];
    private int[] m;
    private int n;
    private double redDispersion;
    private double greenDispersion;
    private double blueDispersion;

    private void nextBayerMatrix(int[] prev, int[] m, int n) {
        int x0 = 0;
        int y0 = 0;
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                m[(y + y0) * 2 * n + x + x0] = 4 * prev[y * n + x];
            }
        }
        x0 = n;
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                m[(y + y0) * 2 * n + x + x0] = 4 * prev[y * n + x] + 2;
            }
        }
        x0 = 0;
        y0 = n;
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                m[(y + y0) * 2 * n + x + x0] = 4 * prev[y * n + x] + 3;
            }
        }
        x0 = n;
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                m[(y + y0) * 2 * n + x + x0] = 4 * prev[y * n + x] + 1;
            }
        }
    }

    public OrderedDithering() {
        nextBayerMatrix(m2, m4, 2);
        nextBayerMatrix(m4, m8, 4);
        nextBayerMatrix(m8, m16, 8);
    }

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        redDispersion = 255.0 / red.getVal();
        greenDispersion = 255.0 / green.getVal();
        blueDispersion = 255.0 / blue.getVal();
        int diff = Math.max(Math.max(256 / red.getVal(), 256 / green.getVal()), 256 / blue.getVal());
        if(m2.length >= diff) {
            m = m2;
            n = 2;
        } else if(m4.length >= diff) {
            m = m4;
            n = 4;
        } else if(m8.length >= diff) {
            m = m8;
            n = 8;
        } else if(m16.length >= diff) {
            m = m16;
            n = 16;
        }
        return applyByChannels(this, src, dst);
    }

    @Override
    public int red(int x, int y, BorderImage src, BufferedImage dst) {
        int p = (src.getRGB(x, y) >> 16) & 0xFF;
        double v = p + redDispersion * (m[(y % n) * n + (x % n)] / (double)m.length - 0.5);
        return palette((int) Math.round(v), red.getVal());
    }

    @Override
    public int green(int x, int y, BorderImage src, BufferedImage dst) {
        int p = (src.getRGB(x, y) >> 8) & 0xFF;
        double v = p + greenDispersion * (m[(y % n) * n + (x % n)] / (double)m.length - 0.5);
        return palette((int) Math.round(v), green.getVal());
    }

    @Override
    public int blue(int x, int y, BorderImage src, BufferedImage dst) {
        int p = src.getRGB(x, y) & 0xFF;
        double v = p + blueDispersion * (m[(y % n) * n + (x % n)] / (double)m.length - 0.5);
        return palette((int) Math.round(v), blue.getVal());
    }

}
