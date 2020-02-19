/*
 * Chem solver. A multi-platform chemistry and physics problem solver.
 *  Copyright (C) 2019 - 2020  Giacalone Enrico
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
import com.enrico.windows.dialogs.infodialog.InfoDialog;

import javax.swing.*;

/**
 * This class is for the default menu bar used in all the program windows.
 */
public class MainMenuBar extends JMenuBar {

    public MainMenuBar() {
        JMenu programMenu = new JMenu("Program");
        programMenu.setFont(FontResources.menuBarFont);

        // Items for "Program" menu.
        JMenuItem infoItem = programMenu.add("Info");
        JMenuItem exitItem = programMenu.add("Exit");

        infoItem.setFont(FontResources.menuBarFont);
        exitItem.setFont(FontResources.menuBarFont);

        infoItem.addActionListener(actionEvent -> {
            InfoDialog dialog = new InfoDialog();
            dialog.showDialog();
        });

        exitItem.addActionListener(actionEvent -> System.exit(0));

        add(programMenu);
    }

}
