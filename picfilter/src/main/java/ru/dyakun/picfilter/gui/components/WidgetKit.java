package ru.dyakun.picfilter.gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WidgetKit {

    private WidgetKit() {
        throw new AssertionError();
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
