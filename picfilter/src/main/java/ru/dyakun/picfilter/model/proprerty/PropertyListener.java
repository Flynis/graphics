package ru.dyakun.picfilter.model.proprerty;

@FunctionalInterface
public interface PropertyListener {

    void onChange(Object prop);

}
