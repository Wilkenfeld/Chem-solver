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

package com.enrico.widgets.canvas;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * This class is used to make easier the process of making a file filter for the JFileChooser.
 */
public final class FileTypeFilter extends FileFilter {
    private String extension;
    private String description;

    public FileTypeFilter(String extension, String description) {
        this.extension = extension;
        this.description = description;
    }

    public boolean accept(File file) {
        if (file.isDirectory())
            return true;
        return file.getName().endsWith(extension);
    }

    public String getDescription() {
        return description + String.format(" (*%s)", extension);
    }

    public String getExtension() {
        return extension;
    }
}
