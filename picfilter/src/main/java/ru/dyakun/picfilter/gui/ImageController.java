package ru.dyakun.picfilter.gui;

import ru.dyakun.picfilter.gui.components.ImageViewer;
import ru.dyakun.picfilter.gui.components.SelectedButtonsGroup;
import ru.dyakun.picfilter.model.ImageListener;
import ru.dyakun.picfilter.model.ImageManager;

import java.awt.image.BufferedImage;

public class ImageController implements ImageListener {

    private final ImageViewer viewer;
    private boolean fitScreen = false;
    private final ImageManager manager;
    private SelectedButtonsGroup fitScreenGroup = null;
    private SelectedButtonsGroup sourceModeGroup = null;

    public ImageController(ImageViewer viewer, ImageManager manager) {
        this.viewer = viewer;
        this.manager = manager;
        manager.addImageListener(this);
    }

    public void setFitScreen(boolean fitScreen) {
        fitScreenGroup.setSelected(fitScreen);
        this.fitScreen = fitScreen;
        if(fitScreen) {
            viewer.fitScreen();
        } else {
            viewer.realSize();
        }
    }

    public void showSource() {
        sourceModeGroup.setSelected(true);
        viewer.setImage(manager.getSource(), fitScreen);
    }

    public void hideSource() {
        sourceModeGroup.setSelected(false);
        viewer.setImage(manager.getCurrent(), fitScreen);
    }

    public void setFitScreenGroup(SelectedButtonsGroup fitScreenGroup) {
        if(this.fitScreenGroup == null) {
            this.fitScreenGroup = fitScreenGroup;
        } else {
            throw new IllegalStateException("Fit screen group is already initialized");
        }
    }

    public void setSourceModeGroup(SelectedButtonsGroup sourceModeGroup) {
        if(this.sourceModeGroup == null) {
            this.sourceModeGroup = sourceModeGroup;
        } else {
            throw new IllegalStateException("Source mode group is already initialized");
        }
    }

    @Override
    public void onChange(BufferedImage image) {
        sourceModeGroup.setSelected(false);
        viewer.setImage(manager.getCurrent(), fitScreen);
    }

    @Override
    public void onSourceChange(BufferedImage image) {
        fitScreen = false;
        fitScreenGroup.setSelected(false);
        sourceModeGroup.setSelected(false);
        viewer.setImage(manager.getSource(), fitScreen);
    }

}
