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

package com.enrico.windows.dialogs.savedialog;

import com.enrico.widgets.canvas.FileTypeFilter;
import com.enrico.windows.BasicWindow;

import javax.swing.*;

public final class SaveDialog extends JFileChooser {
    public static final String PNG_EXTENSION = ".png";
    private static final String JPG_EXTENSION = ".jpg";

    private final BasicWindow parentWindow;

    public SaveDialog(BasicWindow parentWindow) {
        this.parentWindow = parentWindow;

        // Add extension types.
        addChoosableFileFilter(new FileTypeFilter(PNG_EXTENSION, "PNG file"));
        addChoosableFileFilter(new FileTypeFilter(JPG_EXTENSION, "JPG file"));

        // Disable the possibility for user to save in some other format other than jpg and png.
        setAcceptAllFileFilterUsed(false);
        setDialogTitle("Save file.");
    }

    public FileTypeFilter getFileTypeFilter() {
        return (FileTypeFilter) getFileFilter();
    }

    public int showDialog() {
        return showSaveDialog(parentWindow);
    }

}
