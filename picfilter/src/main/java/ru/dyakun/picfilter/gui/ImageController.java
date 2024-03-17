package ru.dyakun.picfilter.gui;

import ru.dyakun.picfilter.gui.components.ImageViewer;
import ru.dyakun.picfilter.model.ImageListener;
import ru.dyakun.picfilter.model.ImageManager;
import ru.dyakun.picfilter.model.proprerty.BooleanProperty;
import ru.dyakun.picfilter.model.proprerty.PropertyListener;

import java.awt.image.BufferedImage;

public class ImageController implements ImageListener {

    private final ImageViewer viewer;
    private final BooleanProperty fitScreen = new BooleanProperty(false, "Fit screen");
    private final BooleanProperty showSource = new BooleanProperty(false, "Show source");
    private final ImageManager manager;

    public ImageController(ImageViewer viewer, ImageManager manager) {
        this.viewer = viewer;
        this.manager = manager;
        manager.addImageListener(this);
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
        showSource.setVal(false);
        viewer.setImage(manager.getCurrent(), fitScreen.getVal());
    }

    @Override
    public void onSourceChange(BufferedImage image) {
        fitScreen.setVal(false);
        showSource.setVal(false);
        viewer.setImage(manager.getSource(), fitScreen.getVal());
    }

}
