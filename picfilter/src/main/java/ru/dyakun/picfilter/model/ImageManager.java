package ru.dyakun.picfilter.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageManager {

    private BufferedImage current = null;
    private BufferedImage previous = null;
    private BufferedImage source = null;
    private final List<ImageListener> listeners = new ArrayList<>();
    private final List<Filter> appliedFilters = new ArrayList<>();
    private Filter last = null;

    public BufferedImage getCurrent() {
        return current;
    }

    public BufferedImage getPrevious() {
        return previous;
    }

    public void addImageListener(ImageListener listener) {
        listeners.add(listener);
    }

    public void confirmFilter() {
        if(last != null) {
            appliedFilters.add(last);
            last = null;
            previous = current;
        }
    }

    public void applyFilter(Filter filter) {
        last = filter;
        current = filter.apply(source, current);
        for(var listener: listeners) {
            listener.onChange(current);
        }
    }

    public void loadImage(File file) throws IOException {
        source = ImageIO.read(file);
        current = deepCopy(source);
        previous = source;
        for(var listener: listeners) {
            listener.onSourceChange(current);
        }
    }

    public void exportImageToPng(File file) throws IOException {
        BufferedImage img = deepCopy(source);
        BufferedImage prev = source;
        for(var filter: appliedFilters) {
            img = filter.apply(prev, img);
        }
        ImageIO.write(img, "png", file);
    }

    private static BufferedImage deepCopy(BufferedImage image) {
        ColorModel model = image.getColorModel();
        boolean isAlphaPremultiplied = model.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(model, raster, isAlphaPremultiplied, null);
    }

}
