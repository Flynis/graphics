package ru.dyakun.picfilter.gui;

import ru.dyakun.picfilter.gui.components.dialog.AboutDialog;
import ru.dyakun.picfilter.gui.components.dialog.PropertiesDialog;
import ru.dyakun.picfilter.model.ChannelsObserver;
import ru.dyakun.picfilter.model.ImageManager;
import ru.dyakun.picfilter.model.proprerty.IntegerProperty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import static javax.swing.Action.*;
import static ru.dyakun.picfilter.gui.Icons.createIcon;
import static ru.dyakun.picfilter.gui.Icons.loadIcon;

public class ActionKit {

    public static void initAction(Action a, String name, String iconPath) {
        a.putValue(SHORT_DESCRIPTION, name);
        a.putValue(NAME, name);
        ImageIcon icon = loadIcon(iconPath);
        a.putValue(SMALL_ICON, icon);
        a.putValue(LARGE_ICON_KEY, icon);
    }

    public static void initAction(Action a, String name, Icon icon) {
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
        initAction(a, "Open", "/icons/open.png");
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
        initAction(a, "Save as", "/icons/save.png");
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
        initAction(a, "About program", "/icons/info.png");
        return a;
    }

    public Action createShowSourceAction(ImageController controller) {
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton abstractButton = (AbstractButton)e.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                if(selected) {
                    controller.showSource();
                } else {
                    controller.hideSource();
                }
            }
        };
        initAction(action, "Show source", "/icons/source_mode.png");
        return action;
    }

    public Action createFitScreenAction(ImageController controller) {
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton abstractButton = (AbstractButton)e.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                controller.setFitScreen(selected);
            }
        };
        initAction(action, "Fit screen", "/icons/fit_screen.png");
        return action;
    }

    public Action createInterpolationModeAction(ImageController controller, JFrame frame) {
        var prop = controller.getInterpolationMode();
        var settings = new PropertiesDialog(prop.getName(), frame, List.of(prop), null);
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.show();
            }
        };
        initAction(action, "Interpolation settings", "/icons/settings.png");
        return action;
    }

    public Action createRedChannelAction() {
        ChannelsObserver observer = ChannelsObserver.getInstance();
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton abstractButton = (AbstractButton)e.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                observer.setRed(selected);
            }
        };
        initAction(action, "Red channel", createIcon(Color.red));
        return action;
    }

    public Action createGreenChannelAction() {
        ChannelsObserver observer = ChannelsObserver.getInstance();
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton abstractButton = (AbstractButton)e.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                observer.setGreen(selected);
            }
        };
        initAction(action, "Green channel", createIcon(Color.green));
        return action;
    }

    public Action createBlueChannelAction() {
        ChannelsObserver observer = ChannelsObserver.getInstance();
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton abstractButton = (AbstractButton)e.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                observer.setBlue(selected);
            }
        };
        initAction(action, "Blue channel", createIcon(Color.blue));
        return action;
    }

    public Action createRotateAction(ImageManager manager, JFrame frame) {
        String name = "Rotate image";
        IntegerProperty degrees = new IntegerProperty(90, -180, 180, "Rotate");
        PropertiesDialog rotateDialog = new PropertiesDialog(name, frame, List.of(degrees), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.rotate(degrees.getVal());
            }
        });
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotateDialog.show();
            }
        };
        initAction(action, name, "/icons/rotate.png");
        return action;
    }

}
