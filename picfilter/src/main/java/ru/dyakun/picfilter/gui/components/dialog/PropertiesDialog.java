package ru.dyakun.picfilter.gui.components.dialog;

import net.miginfocom.swing.MigLayout;
import ru.dyakun.picfilter.gui.components.Colors;
import ru.dyakun.picfilter.gui.components.WidgetKit;
import ru.dyakun.picfilter.model.proprerty.Property;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PropertiesDialog extends Dialog {

    protected final List<PropEditPane> editPanes = new ArrayList<>();
    protected final JLabel errorLabel = new JLabel("Error");
    protected final ActionListener onConfirm;

    public PropertiesDialog(String title, JFrame frame, List<Property> properties, ActionListener onConfirm) {
        super(title, frame);
        this.onConfirm = onConfirm;
        if(properties.isEmpty()) {
            throw new IllegalArgumentException("No properties for dialog");
        }
        dialog.setMinimumSize(new Dimension(400, properties.size() * 30 + 50));
        dialog.setResizable(false);

        JPanel buttons = WidgetKit.createConfirmButtonsPane(e -> onOk(), e -> onCancel());

        JPanel gridPanel = new JPanel(new BorderLayout());
        gridPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel grid = new JPanel(new MigLayout("fillx", "[right]5[50, fill]5[grow,fill]", ""));
        for(var prop: properties) {
            PropEditPane editPane = PropEditPaneFactory.create(prop);
            editPanes.add(editPane);
            var components = editPane.getComponents();
            switch (components.size()) {
                case 3 -> {
                    for(var c : components) {
                        grid.add(c);
                    }
                }
                case 2 -> {
                    grid.add(components.get(0));
                    grid.add(components.get(1), "wrap");
                }
            }
        }
        gridPanel.add(grid);

        errorLabel.setForeground(Colors.ERROR_COLOR);
        JPanel bottomPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPane.add(errorLabel);
        bottomPane.add(buttons);

        dialog.add(bottomPane, BorderLayout.SOUTH);
        dialog.add(gridPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        errorLabel.setVisible(false);
    }

    protected void onOk() {
        for(var pane: editPanes) {
            String error = pane.validateValue();
            if(error != null) {
                errorLabel.setText(error);
                errorLabel.setVisible(true);
                return;
            }
        }
        for(var pane: editPanes) {
            pane.updateValue();
        }
        errorLabel.setVisible(false);
        hide();
        onConfirm.actionPerformed(null);
    }

    protected void onCancel() {
        hide();
        errorLabel.setVisible(false);
    }

    @Override
    public void show() {
        for(var pane: editPanes) {
            pane.resetValue();
        }
        super.show();
    }

}