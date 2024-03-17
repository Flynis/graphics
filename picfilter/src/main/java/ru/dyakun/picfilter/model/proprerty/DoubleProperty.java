package ru.dyakun.picfilter.model.proprerty;

public class DoubleProperty extends Property {

    private double val;
    private final double max;
    private final double min;

    public DoubleProperty(double val, double min, double max, String name) {
        super(name);
        this.val = val;
        this.min = min;
        this.max = max;
    }

    public double getVal() {
        return val;
    }

    public void setVal(double val) {
        if(val < min) {
            val = min;
        }
        if(val > max) {
            val = max;
        }
        this.val = val;
        notify(val);
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    @Override
    protected Object getValue() {
        return val;
    }

}
