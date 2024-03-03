package ru.dyakun.picfilter.gui;

import ru.dyakun.picfilter.gui.components.AboutDialog;
import ru.dyakun.picfilter.model.ImageManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import static javax.swing.Action.*;
import static ru.dyakun.picfilter.gui.Icons.loadIcon;

public class ActionKit {

    private void initAction(Action a, String name, ImageIcon icon) {
        a.putValue(SHORT_DESCRIPTION, name);
        a.putValue(NAME, name);
        a.putValue(SMALL_ICON, icon);
        a.putValue(LARGE_ICON_KEY, icon);
    }

    public Action createExitAction(JFrame frame) {
        Action a = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        };
        String name = "Exit";
        a.putValue(SHORT_DESCRIPTION, name);
        a.putValue(NAME, name);
        return a;
    }

    public List<Action> createFileActions(JFrame frame, ImageManager manager) {
        JFileChooser fileChooser = new JFileChooser();
        Action openAction = createOpenAction(frame, manager, fileChooser);
        Action saveAction = createSaveAction(frame, manager, fileChooser);
        return List.of(openAction, saveAction);
    }

    private Action createOpenAction(JFrame frame, ImageManager manager, JFileChooser fileChooser) {
        String desc = "*.jpg, *.jpeg, *.bpm, *.png, *.gif";
        var openFilter = new ExtensionFileFilter(desc, List.of("jpg", "jpeg", "bmp", "png", "gif"));
        Action a = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Choose file");
                fileChooser.setFileFilter(openFilter);
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        manager.loadImage(fileChooser.getSelectedFile());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Failed to open file", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
        initAction(a, "Open", loadIcon("/icons/open.png"));
        return a;
    }

    private Action createSaveAction(JFrame frame, ImageManager manager, JFileChooser fileChooser) {
        var saveFilter = new ExtensionFileFilter("*.png", List.of("png"));
        Action a = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Save file");
                fileChooser.setFileFilter(saveFilter);
                int result = fileChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        manager.exportImageToPng(fileChooser.getSelectedFile());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Failed to open file", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
        initAction(a, "Save as", loadIcon("/icons/save.png"));
        return a;
    }

    public Action createAboutAction(JFrame frame) {
        AboutDialog dialog = new AboutDialog(frame);
        Action a = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.show();
            }
        };
        initAction(a, "About program", loadIcon("/icons/info.png"));
        return a;
    }

}
