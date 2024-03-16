package ru.dyakun.picfilter.gui;

import ru.dyakun.picfilter.gui.components.ImageViewer;
import ru.dyakun.picfilter.model.ImageListener;
import ru.dyakun.picfilter.model.ImageManager;

import java.awt.image.BufferedImage;

public class ImageObserver implements ImageListener {

    private final ImageViewer viewer;
    private boolean fitScreen = false;
    private final ImageManager manager;

    public ImageObserver(ImageViewer viewer, ImageManager manager) {
        this.viewer = viewer;
        this.manager = manager;
        manager.addImageListener(this);
    }

    public void setFitScreen(boolean fitScreen) {
        this.fitScreen = fitScreen;
        if(fitScreen) {
            viewer.fitScreen();
        } else {
            viewer.realSize();
        }
    }

    public void showSource() {
        viewer.setImage(manager.getSource(), fitScreen);
    }

    public void hideSource() {
        viewer.setImage(manager.getCurrent(), fitScreen);
    }

    @Override
    public void onChange(BufferedImage image) {
        viewer.repaint();
    }

    @Override
    public void onSourceChange(BufferedImage image) {
        fitScreen = false;
        viewer.setImage(manager.getSource(), fitScreen);
    }

}
