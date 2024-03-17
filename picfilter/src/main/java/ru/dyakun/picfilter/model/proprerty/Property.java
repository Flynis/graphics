package ru.dyakun.picfilter.model.proprerty;

import java.util.ArrayList;
import java.util.List;

public abstract class Property {

    private final String name;
    private final List<PropertyListener> listeners = new ArrayList<>();

    public Property(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected abstract Object getValue();

    public void addPropertyListener(PropertyListener listener) {
        listeners.add(listener);
        listener.onChange(getValue());
    }

    protected void notify(Object prop) {
        for(var listener : listeners) {
            listener.onChange(prop);
        }
    }

}
