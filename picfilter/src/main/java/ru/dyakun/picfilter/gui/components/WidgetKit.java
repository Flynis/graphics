package ru.dyakun.picfilter.gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

public class WidgetKit {

    private WidgetKit() {
        throw new AssertionError();
    }

    public static JToggleButton toolbarButtonFromAction(Action action) {
        JToggleButton button = new JToggleButton(action);
        button.setText("");
        return button;
    }

    public static JCheckBoxMenuItem menuButtonFromAction(Action action) {
        JCheckBoxMenuItem checkBox = new JCheckBoxMenuItem(action);
        checkBox.setIcon(null);
        return checkBox;
    }


    public static JPanel createConfirmButtonsPane(ActionListener ok) {
        return createConfirmButtonsPane(ok, null);
    }

    public static JPanel createConfirmButtonsPane(ActionListener ok, ActionListener cancel) {
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(ok);
        buttons.add(okButton);

        if (cancel != null) {
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(cancel);
            buttons.add(cancelButton);
        }

        return buttons;
    }

}
