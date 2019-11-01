package com.enrico.widgets.menu;

import com.enrico.windows.BasicWindow;

import javax.swing.*;

public final class ProblemWindowMenuBar extends MainMenuBar {

    public ProblemWindowMenuBar(BasicWindow win) {
        super();

        JMenu problemMenu = new JMenu("Problem");

        JMenuItem problemMenuItemSolve = problemMenu.add("Solve");
        JMenuItem problemMenuItemStartAnother = problemMenu.add("Solve another problem");

        problemMenuItemSolve.addActionListener(actionEvent -> {

        });

        problemMenuItemStartAnother.addActionListener(actionEvent -> {
            win.dispose();
        });

        add(problemMenu);
    }

    public void createUIComponents() {
    }
}
