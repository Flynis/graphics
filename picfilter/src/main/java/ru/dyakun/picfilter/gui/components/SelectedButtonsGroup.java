package ru.dyakun.picfilter.gui.components;

import ru.dyakun.picfilter.model.proprerty.PropertyListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SelectedButtonsGroup implements PropertyListener {

    private final List<AbstractButton> buttons = new ArrayList<>();

    public void add(AbstractButton button) {
        buttons.add(button);
    }

    public void setSelected(boolean val) {
        for(var b : buttons) {
            b.setSelected(val);
        }
    }

    @Override
    public void onChange(Object prop) {
        if(prop instanceof Boolean val) {
            setSelected(val);
        }
    }

}
