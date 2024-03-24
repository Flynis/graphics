package ru.dyakun.picfilter.gui.components.properties;

import ru.dyakun.picfilter.model.proprerty.EnumProperty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class EnumPropEditPane implements PropEditPane {
    private final EnumProperty prop;
    private String current;
    private final JLabel label;
    private final JComboBox<String> comboBox;

    public EnumPropEditPane(EnumProperty prop) {
        this.prop = prop;

        label = new JLabel(prop.getName());
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        comboBox = new JComboBox<>();
        for(var key: prop.getKeys()) {
            comboBox.addItem(key);
        }
        comboBox.setSelectedItem(prop.getValKey());
        comboBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current = (String) comboBox.getSelectedItem();
            }
        });

        current = prop.getValKey();
    }

    @Override
    public java.util.List<Component> getComponents() {
        return List.of(label, comboBox);
    }

    @Override
    public void resetValue() {
        current = prop.getValKey();
        comboBox.setSelectedItem(current);
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
