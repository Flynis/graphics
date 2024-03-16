package ru.dyakun.picfilter.model.proprerty;

public class IntegerProperty extends Property {

    private int val;
    private final int max;
    private final int min;

    public IntegerProperty(int val, int min, int max, String name) {
        super(name);
        this.val = val;
        this.min = min;
        this.max = max;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        if(val < min) {
            val = min;
        }
        if(val > max) {
            val = max;
        }
        this.val = val;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

}
