package com.enrico.widgets.menu;

import javax.swing.*;

/**
 * This class is for the default menu bar used in all the program windows.
 */
public class MainMenuBar extends JMenuBar {

    public MainMenuBar() {
        JMenu programMenu = new JMenu("Program");

        // Items for "Program" menu.
        JMenuItem infoItem = programMenu.add("Info");
        JMenuItem exitItem = programMenu.add("Exit");

        infoItem.addActionListener(actionEvent -> {
            // TODO.
        });

        exitItem.addActionListener(actionEvent -> System.exit(0));

        add(programMenu);
    }

}
