package ru.dyakun.picfilter.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageManager {

    private BufferedImage current = null;
    private BufferedImage source = null;
    private BorderImage buffer = null;
    private boolean isEmptyCurrent = true;
    private final List<ImageListener> listeners = new ArrayList<>();

    public BufferedImage getCurrent() {
        return (isEmptyCurrent) ? source : current;
    }

    public BufferedImage getSource() {
        return source;
    }

    public void addImageListener(ImageListener listener) {
        listeners.add(listener);
    }

    public void transformImage(ImageTransformation transformation) {
        if(source == null) {
            return;
        }
        current = transformation.apply(buffer, current);
        isEmptyCurrent = false;
        for(var listener: listeners) {
            listener.onChange(current);
        }
    }

    public void loadImage(File file) throws IOException {
        source = ImageIO.read(file);
        current = new BufferedImage(source.getWidth(), source.getHeight(), Image.SCALE_DEFAULT);
        buffer = new BorderImage(source);
        System.out.println("Load image " + source.getWidth() + "x" + source.getHeight());
        isEmptyCurrent = true;
        for(var listener: listeners) {
            listener.onSourceChange(current);
        }
    }

    public void exportImageToPng(File file) throws IOException {
        if(source == null) {
            return;
        }
        BufferedImage img = (current == null) ? source : current;
        ImageIO.write(img, "png", file);
    }

}
