package ru.dyakun.picfilter.gui;

import ru.dyakun.picfilter.gui.components.ImageViewer;
import ru.dyakun.picfilter.model.ImageListener;
import ru.dyakun.picfilter.model.ImageManager;
import ru.dyakun.picfilter.model.proprerty.BooleanProperty;
import ru.dyakun.picfilter.model.proprerty.EnumProperty;
import ru.dyakun.picfilter.model.proprerty.PropertyListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ImageController implements ImageListener, MouseListener, PropertyListener {

    private final ImageViewer viewer;
    private final BooleanProperty fitScreen = new BooleanProperty(false, "Fit screen");
    private final BooleanProperty showSource = new BooleanProperty(false, "Show source");
    private final EnumProperty interpolationMode;
    private final ImageManager manager;

    public ImageController(ImageViewer viewer, ImageManager manager) {
        this.viewer = viewer;
        this.manager = manager;
        manager.addImageListener(this);
        viewer.addMouseListener(this);
        Map<String, Object> interpolations = new HashMap<>();
        interpolations.put("Bilinear", RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        interpolations.put("Bicubic", RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        interpolations.put("Nearest neighbor", RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        interpolationMode = new EnumProperty("Bilinear", interpolations, "Interpolation");
        interpolationMode.addPropertyListener(this);
    }

    public EnumProperty getInterpolationMode() {
        return interpolationMode;
    }

    public void setFitScreen(boolean fitScreen) {
        this.fitScreen.setVal(fitScreen);
        if(fitScreen) {
            viewer.fitScreen();
        } else {
            viewer.realSize();
        }
    }

    public void showSource() {
        showSource.setVal(true);
        viewer.setImage(manager.getSource(), fitScreen.getVal());
    }

    public void hideSource() {
        showSource.setVal(false);
        viewer.setImage(manager.getCurrent(), fitScreen.getVal());
    }

    public void addFitScreenListener(PropertyListener listener) {
        fitScreen.addPropertyListener(listener);
    }

    public void addSourceModeListener(PropertyListener listener) {
        showSource.addPropertyListener(listener);
    }

    @Override
    public void onChange(BufferedImage image) {
        fitScreen.setVal(false);
        showSource.setVal(false);
        viewer.setImage(image, fitScreen.getVal());
    }

    @Override
    public void onSourceChange(BufferedImage image) {
        fitScreen.setVal(false);
        showSource.setVal(false);
        viewer.setImage(manager.getSource(), fitScreen.getVal());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        showSource.setVal(!showSource.getVal());
        if(showSource.getVal()) {
            showSource();
        } else {
            hideSource();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void onChange(Object prop) {
        viewer.setInterpolationMode(prop);
        System.out.println("Interpolation change to " + interpolationMode.getValKey());
    }

}
