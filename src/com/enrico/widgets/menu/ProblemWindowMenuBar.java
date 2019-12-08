/*
 * Chem solver. A multi-platform chemistry and physics problem solver.
 *  Copyright (C) 2019  Giacalone Enrico
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.enrico.widgets.menu;

import com.enrico.windows.BasicWindow;
import com.enrico.windows.main.MainWindow;

import javax.swing.*;

public final class ProblemWindowMenuBar extends MainMenuBar {

    public JMenuItem problemMenuItemSolve;
    public JMenuItem saveMenuItem;
    public JMenu problemMenu;

    public ProblemWindowMenuBar(BasicWindow win) {
        super();

        problemMenu = new JMenu("Problem");
        problemMenu.setFont(menuBarFont);

        problemMenuItemSolve = problemMenu.add("Solve");
        JMenuItem problemMenuItemStartAnother = problemMenu.add("Solve another problem");

        problemMenuItemSolve.setFont(menuBarFont);
        problemMenuItemStartAnother.setFont(menuBarFont);

        problemMenuItemStartAnother.addActionListener(actionEvent -> {
            win.dispose();
            MainWindow window = new MainWindow();
            window.showWindow();
        });
        saveMenuItem = problemMenu.add("Save");
        saveMenuItem.setFont(menuBarFont);

        add(problemMenu);
    }

    public void createUIComponents() {
    }
}
