package ru.dyakun.picfilter.gui.components.properties;

import ru.dyakun.picfilter.model.proprerty.DoubleProperty;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class DoublePropEditPane implements PropEditPane, DocumentListener {

    private final DoubleProperty prop;
    private double current;
    private final JTextField field;
    private final JLabel label;

    public DoublePropEditPane(DoubleProperty prop) {
        this.prop = prop;

        label = new JLabel(prop.getName());
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        field = new JTextField();
        PlainDocument doc = (PlainDocument) field.getDocument();
        doc.setDocumentFilter(new DoubleFilter());
        doc.addDocumentListener(this);
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) ||
                        c == '.' ||
                        c == KeyEvent.VK_DECIMAL ||
                        c == KeyEvent.VK_BACK_SPACE ||
                        c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });

        current = prop.getVal();
    }

    @Override
    public List<Component> getComponents() {
        return List.of(label, field);
    }

    @Override
    public void resetValue() {
        current = prop.getVal();
        field.setText(Double.toString(current));
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
        String text = field.getText();
        current = text.isEmpty() ? prop.getMin() : Double.parseDouble(text);
    }

    @Override
    public String validateValue() {
        if (field.getText().isEmpty()) {
            return prop.getName() + " can not be empty";
        }
        if (current < prop.getMin()) {
            return prop.getName() + " too small";
        } else if (current > prop.getMax()) {
            return prop.getName() + " too big";
        }
        return null;
    }

}
