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
