package ru.dyakun.picfilter.model.proprerty;

public class IntegerProperty extends Property {

    private int val;
    private final int max;
    private final int min;
    private boolean mustBeOdd = false;

    public IntegerProperty(int val, int min, int max, String name) {
        super(name);
        this.val = val;
        this.min = min;
        this.max = max;
    }

    public void setMustBeOdd(boolean val) {
        mustBeOdd = val;
    }

    public boolean mustBeOdd() {
        return mustBeOdd;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        if(mustBeOdd && val % 2 == 0) {
            return;
        }
        if(val < min) {
            val = min;
        }
        if(val > max) {
            val = max;
        }
        this.val = val;
        notify(val);
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    @Override
    protected Object getValue() {
        return val;
    }

}
