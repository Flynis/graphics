package ru.dyakun.picfilter.gui.components;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SelectedButtonsGroup {

    private final List<JToggleButton> buttons = new ArrayList<>();
    private final boolean initial;

    public SelectedButtonsGroup(boolean initial) {
        this.initial = initial;
    }

    public void add(JToggleButton toggleButton) {
        toggleButton.setSelected(initial);
        buttons.add(toggleButton);
    }

    public void setSelected(boolean val) {
        for(var b : buttons) {
            b.setSelected(val);
        }
    }

}
