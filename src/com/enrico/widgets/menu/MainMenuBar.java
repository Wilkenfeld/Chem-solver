package com.enrico.widgets.menu;

import com.enrico.interfaces.FontInterface;

import javax.swing.*;

/**
 * This class is for the default menu bar used in all the program windows.
 */
public class MainMenuBar extends JMenuBar implements FontInterface {

    public MainMenuBar() {
        JMenu programMenu = new JMenu("Program");
        programMenu.setFont(menuBarFont);

        // Items for "Program" menu.
        JMenuItem infoItem = programMenu.add("Info");
        JMenuItem exitItem = programMenu.add("Exit");

        infoItem.setFont(menuBarFont);
        exitItem.setFont(menuBarFont);

        infoItem.addActionListener(actionEvent -> {
            // TODO.
        });

        exitItem.addActionListener(actionEvent -> System.exit(0));

        add(programMenu);
    }

}
