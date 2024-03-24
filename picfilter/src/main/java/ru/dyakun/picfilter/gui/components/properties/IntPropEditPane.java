package ru.dyakun.picfilter.gui.components.properties;

import ru.dyakun.picfilter.model.proprerty.IntegerProperty;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class IntPropEditPane implements DocumentListener, PropEditPane {

    private final IntegerProperty prop;
    private int current;
    private boolean updatingValue = false;
    private final JSlider slider;
    private final JTextField field;
    private final JLabel label;

    public IntPropEditPane(IntegerProperty prop) {
        this.prop = prop;

        label = new JLabel(prop.getName());
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        slider = new JSlider();
        slider.setMaximum(prop.getMax());
        slider.setMinimum(prop.getMin());
        slider.setMajorTickSpacing(prop.getMax() - prop.getMin());
        if(prop.mustBeOdd()) {
            slider.setMinorTickSpacing(2);
            slider.setSnapToTicks(true);
        }
        slider.setPaintLabels(true);

        field = new JTextField();
        PlainDocument doc = (PlainDocument) field.getDocument();
        doc.setDocumentFilter(new IntegerFilter());
        doc.addDocumentListener(this);
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') || c == '-' ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
        slider.addChangeListener(e -> {
            if (!updatingValue) {
                setValue(slider.getValue(), slider);
            }
        });

        setValue(prop.getVal(), null);
    }

    private void setValue(int val, Object source) {
        current = val;
        updatingValue = true;
        if(slider != source) {
            slider.setValue(current);
        }
        if (field != source) {
            field.setText(Integer.toString(current));
        }
        updatingValue = false;
    }

    @Override
    public List<Component> getComponents() {
        return List.of(label, field, slider);
    }

    @Override
    public void resetValue() {
        setValue(prop.getVal(), null);
        slider.setMinimum(prop.getMin());
        slider.setMaximum(prop.getMax());
        slider.setMajorTickSpacing(prop.getMax() - prop.getMin());
    }

    @Override
    public void updateValue() {
        prop.setVal(current);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        onTextFieldChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        onTextFieldChanged();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        onTextFieldChanged();
    }

    private void onTextFieldChanged() {
        if (!updatingValue) {
            String text = field.getText();
            int val = text.isEmpty() ? prop.getMin() : Integer.parseInt(text);
            setValue(val, field);
        }
    }

    @Override
    public String validateValue() {
        if (field.getText().isEmpty()) {
            return prop.getName() + " can not be empty";
        }
        if(prop.mustBeOdd() && current % 2 == 0) {
            return prop.getName() + " must be odd";
        }
        if (current < prop.getMin()) {
            return prop.getName() + " too small";
        } else if (current > prop.getMax()) {
            return prop.getName() + " too big";
        }
        return null;
    }

}