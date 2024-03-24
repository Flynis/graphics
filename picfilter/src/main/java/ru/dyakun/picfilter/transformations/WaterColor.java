package ru.dyakun.picfilter.transformations;

import ru.dyakun.picfilter.model.BorderImage;
import ru.dyakun.picfilter.model.ImageTransformation;

import java.awt.image.BufferedImage;

public class WaterColor extends Sharpen implements ImageTransformation {

    private final Median median = new Median();

    public WaterColor() {
        median.kernelSize.setVal(5);
    }

    @Override
    public BufferedImage apply(BorderImage src, BufferedImage dst) {
        src.fillBorders(BorderImage.BorderType.Mirror);
        dst = median.apply(src, dst);
        BorderImage buf = new BorderImage(dst);
        return applyKernel(size, 1, buf, dst);
    }

}
