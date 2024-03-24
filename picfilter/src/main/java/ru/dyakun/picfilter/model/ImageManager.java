package ru.dyakun.picfilter.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class ImageManager {

    private BufferedImage current = null;
    private BufferedImage source = null;
    private BufferedImage rotatedImage = null;
    private BorderImage buffer = null;
    private boolean isEmptyCurrent = true;
    private boolean rotated = false;
    private final List<ImageListener> listeners = new ArrayList<>();

    public BufferedImage getCurrent() {
        if(isEmptyCurrent) {
            return source;
        }
        return (rotated) ? rotatedImage : current;
    }

    public BufferedImage getSource() {
        return source;
    }

    public void addImageListener(ImageListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners(BufferedImage image) {
        for(var listener: listeners) {
            listener.onChange(image);
        }
    }

    public void transformImage(ImageTransformation transformation) {
        if(source == null) {
            return;
        }
        current = transformation.apply(buffer, current);
        isEmptyCurrent = false;
        rotated = false;
        rotatedImage = null;
        notifyListeners(current);
    }

    public void loadImage(File file) throws IOException {
        source = ImageIO.read(file);
        current = new BufferedImage(source.getWidth(), source.getHeight(), Image.SCALE_DEFAULT);
        buffer = new BorderImage(source);
        System.out.println("Load image " + source.getWidth() + "x" + source.getHeight());
        isEmptyCurrent = true;
        rotated = false;
        rotatedImage = null;
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

    public void rotate(int degrees) {
        if(source == null) {
            return;
        }
        rotated = true;
        int sw = source.getWidth();
        int sh = source.getHeight();
        double a = degrees * PI / 180;
        int w = (int) Math.round(sw * abs(cos(a)) + sh * abs(sin(a)));
        int h = (int) Math.round(sw * abs(sin(a)) + sh * abs(cos(a)));
        BufferedImage img = new BufferedImage(w, h, Image.SCALE_DEFAULT);
        int ww = sw / 2;
        int hh = sh / 2;
        for(int y = 0; y < h; y++) {
            for(int x = 0; x < w; x++) {
                int xr = x - w / 2;
                int yr = y - h / 2;
                int xn = (int) Math.round(xr * cos(a) - yr * sin(a) + ww);
                int yn = (int) Math.round(xr * sin(a) + yr * cos(a) + hh);
                if(xn >= 0 && xn < sw && yn >= 0 && yn < sh) {
                    int c = source.getRGB(xn, yn);
                    img.setRGB(x, y, c);
                } else {
                    img.setRGB(x, y, Color.white.getRGB());
                }
            }
        }
        rotatedImage = img;
        notifyListeners(rotatedImage);
    }

}
