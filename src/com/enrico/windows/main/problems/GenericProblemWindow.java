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

package com.enrico.windows.main.problems;

import com.enrico.programresources.FontResources;
import com.enrico.widgets.menu.ProblemWindowMenuBar;
import com.enrico.windows.BasicWindow;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.concurrent.Callable;

public abstract class GenericProblemWindow extends BasicWindow {
    public GenericProblemWindow(String title) {
        super(title);
    }
    public abstract void solveProblem();
    public abstract void saveProject();

    protected final void addSaveImageItem(@NotNull ProblemWindowMenuBar menuBar, Callable<Void> saveMethod) {
        JMenuItem saveImageItem = menuBar.problemMenu.add("Save image");
        saveImageItem.addActionListener(actionEvent -> {
            try {
                saveMethod.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        saveImageItem.setFont(FontResources.menuBarFont);
        menuBar.saveMenuItem.addActionListener(actionEvent -> saveProject());
    }
}
