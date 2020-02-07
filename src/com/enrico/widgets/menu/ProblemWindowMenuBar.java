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

import com.enrico.programresources.FontResources;
import com.enrico.windows.main.MainWindow;
import com.enrico.windows.main.problems.GenericProblemWindow;

import javax.swing.*;

public final class ProblemWindowMenuBar extends MainMenuBar {

    public JMenuItem saveMenuItem;
    public JMenu problemMenu;

    public ProblemWindowMenuBar(GenericProblemWindow win) {
        super();

        problemMenu = new JMenu("Problem");
        problemMenu.setFont(FontResources.menuBarFont);

        JMenuItem problemMenuItemSolve = problemMenu.add("Solve");
        JMenuItem problemMenuItemStartAnother = problemMenu.add("Solve another problem");

        problemMenuItemSolve.setFont(FontResources.menuBarFont);
        problemMenuItemStartAnother.setFont(FontResources.menuBarFont);
        problemMenuItemSolve.addActionListener(ActionListener -> win.solveProblem());

        problemMenuItemStartAnother.addActionListener(actionEvent -> {
            win.dispose();
            MainWindow window = new MainWindow();
            window.showWindow();
        });
        saveMenuItem = problemMenu.add("Save");
        saveMenuItem.setFont(FontResources.menuBarFont);

        add(problemMenu);
    }

    public void createUIComponents() {
    }
}
