package ru.dyakun.picfilter.model;

public class ChannelsObserver {

    private static final ChannelsObserver observer = new ChannelsObserver();

    private ChannelsObserver() {}

    public static ChannelsObserver getInstance() {
        return observer;
    }

    private boolean blue = true;
    private boolean red = true;
    private boolean green = true;

    public boolean blue() {
        return blue;
    }

    public void setBlue(boolean blue) {
        this.blue = blue;
    }

    public boolean red() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    public boolean green() {
        return green;
    }

    public void setGreen(boolean green) {
        this.green = green;
    }

}
