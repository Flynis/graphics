package ru.dyakun.picfilter.gui;

import ru.dyakun.picfilter.gui.components.DisplayArea;
import ru.dyakun.picfilter.gui.components.ImageViewer;
import ru.dyakun.picfilter.gui.components.SelectedButtonsGroup;
import ru.dyakun.picfilter.gui.components.WidgetKit;
import ru.dyakun.picfilter.model.ChannelsObserver;
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
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);

        DisplayArea displayArea = new DisplayArea(5);
        displayArea.add(scrollPane);
        add(displayArea);

        ImageViewer viewer = new ImageViewer(scrollPane);
        ImageController controller = new ImageController(viewer, imageManager);
        TransformationManager transformationManager = new TransformationManager(imageManager, this, scrollPane);

        ActionKit actionKit = new ActionKit();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.NORTH);

        var fileMenu = createFileMenu(actionKit, toolBar);
        menuBar.add(fileMenu);

        var viewMenu = createViewMenu(actionKit, controller, toolBar);
        menuBar.add(viewMenu);

        var modifyMenu = createModifyMenu(transformationManager, actionKit, toolBar);
        menuBar.add(modifyMenu);

        var help = createHelpMenu(actionKit, toolBar);
        menuBar.add(help);
    }

    private JMenu createFileMenu(ActionKit actionKit, JToolBar toolBar) {
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

    private SelectedButtonsGroup insertToggleButton(JMenu menu, JToolBar toolBar, Action action) {
        JToggleButton toolbarBtn = WidgetKit.toolbarButtonFromAction(action);
        JCheckBoxMenuItem menuBtn = WidgetKit.menuButtonFromAction(action);
        SelectedButtonsGroup group = new SelectedButtonsGroup();
        group.add(toolbarBtn);
        group.add(menuBtn);
        menu.add(menuBtn);
        toolBar.add(toolbarBtn);
        return group;
    }

    private JMenu createModifyMenu(TransformationManager manager, ActionKit actionKit, JToolBar toolBar) {
        JMenu modifyMenu = new JMenu("Modify");
        ChannelsObserver observer = ChannelsObserver.getInstance();

        Action red = actionKit.createRedChannelAction();
        var group = insertToggleButton(modifyMenu, toolBar, red);
        observer.addRedListener(group);

        Action green = actionKit.createGreenChannelAction();
        group = insertToggleButton(modifyMenu, toolBar, green);
        observer.addGreenListener(group);

        Action blue = actionKit.createBlueChannelAction();
        group = insertToggleButton(modifyMenu, toolBar, blue);
        observer.addBlueListener(group);

        modifyMenu.add(new JSeparator());
        toolBar.add(new JToolBar.Separator());

        List<Action> actions = manager.getActions();
        for(var action : actions) {
            modifyMenu.add(action);
            toolBar.add(action);
        }

        toolBar.add(new JToolBar.Separator());
        return modifyMenu;
    }

    private JMenu createViewMenu(ActionKit actionKit, ImageController controller, JToolBar toolBar) {
        JMenu viewMenu = new JMenu("View");

        Action fitScreen = actionKit.createFitScreenAction(controller);
        var group = insertToggleButton(viewMenu, toolBar, fitScreen);
        controller.addFitScreenListener(group);

        Action showSource = actionKit.createShowSourceAction(controller);
        group = insertToggleButton(viewMenu, toolBar, showSource);
        controller.addSourceModeListener(group);

        // TODO setting btn

        toolBar.add(new JToolBar.Separator());
        return viewMenu;
    }

    private JMenu createHelpMenu(ActionKit actionKit, JToolBar toolBar) {
        JMenu help = new JMenu("Help");
        Action aboutAction = actionKit.createAboutAction(this);
        help.add(aboutAction);
        toolBar.add(aboutAction);
        toolBar.add(new JToolBar.Separator());
        return help;
    }

}
