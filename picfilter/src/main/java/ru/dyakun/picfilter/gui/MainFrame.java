package ru.dyakun.picfilter.gui;

import ru.dyakun.picfilter.gui.components.Colors;
import ru.dyakun.picfilter.gui.components.DisplayArea;
import ru.dyakun.picfilter.gui.components.ImageViewer;
import ru.dyakun.picfilter.model.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private final ImageManager imageManager;

    public MainFrame(ImageManager imageManager) {
        super("Picture filter");
        this.imageManager = imageManager;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createUI();
        setPreferredSize(new Dimension(1600, 900));
        setMinimumSize(new Dimension(640, 480));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createUI() {
        DisplayArea displayArea = new DisplayArea(5);
        JScrollPane scrollPane = new JScrollPane();
        displayArea.add(scrollPane);
        add(displayArea);

        ImageViewer viewer = new ImageViewer(scrollPane);
        ImageObserver observer = new ImageObserver(viewer, imageManager);

        ActionKit actionKit = new ActionKit();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.NORTH);

        var fileMenu = createFileMenuAndFillToolBar(actionKit, toolBar);
        menuBar.add(fileMenu);

        var filterMenu = createFilterMenuAndFillToolBar(actionKit, toolBar);
        menuBar.add(filterMenu);

        var help = createHelpMenuAndFillToolBar(actionKit, toolBar);
        menuBar.add(help);
    }

    private JMenu createFileMenuAndFillToolBar(ActionKit actionKit, JToolBar toolBar) {
        JMenu fileMenu = new JMenu("File");
        List<Action> fileActions = actionKit.createFileActions(this, imageManager);
        for(var action: fileActions) {
            fileMenu.add(action);
            toolBar.add(action);
        }
        fileMenu.add(new JSeparator());
        fileMenu.add(actionKit.createExitAction(this));
        toolBar.add(new JToolBar.Separator());
        return fileMenu;
    }

    private JMenu createFilterMenuAndFillToolBar(ActionKit actionKit, JToolBar toolBar) {
        JMenu filterMenu = new JMenu("Filters");

        return filterMenu;
    }

    private JMenu createHelpMenuAndFillToolBar(ActionKit actionKit, JToolBar toolBar) {
        JMenu help = new JMenu("Help");
        Action aboutAction = actionKit.createAboutAction(this);
        help.add(aboutAction);
        toolBar.add(aboutAction);
        toolBar.add(new JToolBar.Separator());
        return help;
    }

}
