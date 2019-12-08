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

package com.enrico.windows.dialogs.savedialog.saveProject;

import com.enrico.project.saver.ProjectSaver;
import com.enrico.widgets.canvas.FileTypeFilter;
import com.enrico.windows.BasicWindow;

import javax.swing.*;

public final class ProjectSaveDialog extends JFileChooser {
    private final BasicWindow parentWindow;

    public ProjectSaveDialog(BasicWindow parentWindow) {
        this.parentWindow = parentWindow;

        addChoosableFileFilter(new FileTypeFilter(ProjectSaver.CHEM_SOLVER_PROJECT_FILE_EXTENSION,
                                        "Chem Solver File Project"));

        setAcceptAllFileFilterUsed(false);
        setDialogTitle("Save file");
    }

    public int showDialog() {
        return showSaveDialog(parentWindow);
    }
}
