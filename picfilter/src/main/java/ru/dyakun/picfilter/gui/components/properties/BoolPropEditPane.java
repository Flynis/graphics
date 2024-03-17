package ru.dyakun.picfilter.gui.components.properties;

import ru.dyakun.picfilter.model.proprerty.BooleanProperty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class BoolPropEditPane implements PropEditPane {

    private final BooleanProperty prop;
    private boolean current;
    private final JLabel label;
    private final JCheckBox checkBox;

    public BoolPropEditPane(BooleanProperty prop) {
        this.prop = prop;

        label = new JLabel(prop.getName());
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        checkBox = new JCheckBox();
        checkBox.setHorizontalAlignment(SwingConstants.LEFT);
        checkBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox checkbox = (JCheckBox) e.getSource();
                current = checkbox.isSelected();
            }
        });

        current = prop.getVal();
    }

    @Override
    public List<Component> getComponents() {
        return List.of(label, checkBox);
    }

    @Override
    public void resetValue() {
        current = prop.getVal();
        checkBox.setSelected(current);
    }

    @Override
    public void updateValue() {
        prop.setVal(current);
    }

    @Override
    public String validateValue() {
        return null;
    }

}
