package ru.dyakun.picfilter.gui.components.dialog;

import ru.dyakun.picfilter.model.proprerty.BooleanProperty;
import ru.dyakun.picfilter.model.proprerty.IntegerProperty;
import ru.dyakun.picfilter.model.proprerty.Property;

public class PropEditPaneFactory {

    private PropEditPaneFactory() {
        throw new AssertionError();
    }

    public static PropEditPane create(Property prop) {
        if(prop instanceof IntegerProperty integerProp) {
            return new IntPropEditPane(integerProp);
        } else if(prop instanceof BooleanProperty booleanProp) {
            return new BoolPropEditPane(booleanProp);
        } else {
            throw new IllegalArgumentException("Unknown property class");
        }
    }

}
