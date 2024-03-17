package ru.dyakun.picfilter.model.proprerty;

public class BooleanProperty extends Property {

    private boolean val;

    public BooleanProperty(boolean val, String name) {
        super(name);
        this.val = val;
    }

    public boolean getVal() {
        return val;
    }

    public void setVal(boolean val) {
        this.val = val;
        notify(val);
    }

    @Override
    protected Object getValue() {
        return val;
    }
}
