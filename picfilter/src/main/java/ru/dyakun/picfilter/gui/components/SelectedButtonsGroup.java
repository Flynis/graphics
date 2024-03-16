package ru.dyakun.picfilter.gui.components;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SelectedButtonsGroup {

    private final List<AbstractButton> buttons = new ArrayList<>();
    private final boolean initial;

    public SelectedButtonsGroup(boolean initial) {
        this.initial = initial;
    }

    public void add(AbstractButton button) {
        button.setSelected(initial);
        buttons.add(button);
    }

    public void setSelected(boolean val) {
        for(var b : buttons) {
            b.setSelected(val);
        }
    }

}
