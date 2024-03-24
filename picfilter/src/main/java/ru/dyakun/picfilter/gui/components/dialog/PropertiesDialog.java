package ru.dyakun.picfilter.gui.components.dialog;

import net.miginfocom.swing.MigLayout;
import ru.dyakun.picfilter.gui.components.Colors;
import ru.dyakun.picfilter.gui.components.WidgetKit;
import ru.dyakun.picfilter.gui.components.properties.PropEditPane;
import ru.dyakun.picfilter.gui.components.properties.PropEditPaneFactory;
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
        errorLabel.setMinimumSize(new Dimension(60, 20));
        dialog.setMinimumSize(new Dimension(200, properties.size() * 20 + 30));
        dialog.setResizable(false);

        JPanel buttons = WidgetKit.createConfirmButtonsPane(e -> onOk(), e -> onCancel());

        JPanel gridPanel = new JPanel(new BorderLayout());
        gridPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel grid = new JPanel(new MigLayout("fillx", "5[center]5", "5[]5"));
        for(var prop: properties) {
            PropEditPane editPane = PropEditPaneFactory.create(prop);
            editPanes.add(editPane);
            var components = editPane.getComponents();
            grid.add(components.get(0), "align right");
            if(components.size() == 2) {
                grid.add(components.get(1), "wrap, growx, wmin 60");
            } else {
                grid.add(components.get(1), "growx");
                grid.add(components.get(2), "wrap, wmin 60");
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
        if(onConfirm != null) {
            onConfirm.actionPerformed(null);
        }
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