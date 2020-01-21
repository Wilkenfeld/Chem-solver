package com.enrico.widgets.canvas;

import javax.swing.*;
import java.awt.*;

public abstract class GenericCanvas extends JPanel {
    public GenericCanvas() {
        super();

        setPreferredSize(new Dimension(100, 100));
        setBackground(new Color(0xC0C0C0));
        setOpaque(true);
    }

    public void createUIComponents() {
    }
}
