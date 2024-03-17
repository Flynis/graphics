package ru.dyakun.picfilter.model;

import ru.dyakun.picfilter.model.proprerty.BooleanProperty;
import ru.dyakun.picfilter.model.proprerty.PropertyListener;

public class ChannelsObserver {

    private static final ChannelsObserver observer = new ChannelsObserver();

    private ChannelsObserver() {}

    public static ChannelsObserver getInstance() {
        return observer;
    }

    private final BooleanProperty blue = new BooleanProperty(true, "Blue");
    private final BooleanProperty red = new BooleanProperty(true, "Red");
    private final BooleanProperty green = new BooleanProperty(true, "Green");

    public boolean blue() {
        return blue.getVal();
    }

    public void setBlue(boolean blue) {
        System.out.println("Set blue channel " + blue);
        this.blue.setVal(blue);
    }

    public void addBlueListener(PropertyListener listener) {
        blue.addPropertyListener(listener);
    }

    public boolean red() {
        return red.getVal();
    }

    public void setRed(boolean red) {
        System.out.println("Set red channel " + red);
        this.red.setVal(red);
    }

    public void addRedListener(PropertyListener listener) {
        red.addPropertyListener(listener);
    }

    public boolean green() {
        return green.getVal();
    }

    public void setGreen(boolean green) {
        System.out.println("Set green channel " + green);
        this.green.setVal(green);
    }

    public void addGreenListener(PropertyListener listener) {
        green.addPropertyListener(listener);
    }

}
