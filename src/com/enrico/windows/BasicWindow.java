package com.enrico.windows;

import javax.swing.*;

public abstract class BasicWindow extends JFrame {

    public BasicWindow(String title) {
        super(title);

        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showWindow() {
        pack();
        setVisible(true);
    }
}
