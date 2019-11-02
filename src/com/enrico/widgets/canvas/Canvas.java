package com.enrico.widgets.canvas;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

    private boolean initialized = true;

    public Canvas() {
        super();

        setPreferredSize(new Dimension(100, 100));
        setBackground(new Color(0xC0C0C0));
        setOpaque(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (initialized) {
            g.setColor(Color.BLACK);
            for (int i = 0; i <= 5; i++) {
                g.drawRect(i, i, getWidth()-i, getHeight()-i);
            }
            initialized = false;
        }

    }

    public void createUIComponents() {
    }
}
