package ru.dyakun.picfilter;

import ru.dyakun.picfilter.gui.MainFrame;
import ru.dyakun.picfilter.model.ImageManager;

public class Main {

    public static void main(String[] args) {
        var imageManager = new ImageManager();
        new MainFrame(imageManager);
    }

}
