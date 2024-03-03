package ru.dyakun.picfilter.gui.components;

import javax.swing.*;
import java.awt.*;

public class DisplayArea extends JPanel {

    public DisplayArea(int borderSize) {
        super(new BorderLayout());
        var dashed = BorderFactory.createDashedBorder(Colors.BORDER_COLOR, 5f, 2f);
        var empty = BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize);
        var compound = BorderFactory.createCompoundBorder(empty, dashed);
        setBorder(compound);
        setBackground(Colors.LIGHT_BACK_COLOR);
    }

}
