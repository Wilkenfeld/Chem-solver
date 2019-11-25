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
