package ru.dyakun.picfilter.gui.components.dialog;

import java.awt.*;
import java.util.List;

public interface PropEditPane {

    List<Component> getComponents();

    void resetValue();

    void updateValue();

    String validateValue();

}
