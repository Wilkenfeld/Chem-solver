package com.enrico.interfaces.windows;

import com.enrico.widgets.canvas.FileTypeFilter;
import com.enrico.widgets.canvas.GenericCanvas;
import com.enrico.widgets.canvas.ImageSaver;
import com.enrico.windows.dialogs.overwrite.OverwriteDialog;
import com.enrico.windows.dialogs.savedialog.SaveDialog;
import com.enrico.windows.main.problems.GenericProblemWindow;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public interface ImageSavingInterface {
    default void saveImage(GenericProblemWindow window, GenericCanvas canvas) {
        SaveDialog saveDialog = new SaveDialog(window);

        int selection = saveDialog.showDialog();
        boolean saveStatus = false;
        int imageFormat;

        if (selection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = saveDialog.getSelectedFile();

            // Check extension type.
            FileTypeFilter filter = saveDialog.getFileTypeFilter();
            if (filter.getExtension().equals(SaveDialog.PNG_EXTENSION))
                imageFormat = ImageSaver.IMAGE_PNG_FORMAT;
            else
                imageFormat = ImageSaver.IMAGE_JPG_FORMAT;

            int counter; // Used to count how many times the dialog is shown.

            for (counter = 0; counter < 3; counter++) {
                try {
                    ImageSaver saver = new ImageSaver(canvas);

                    saveStatus = saver.saveImage(fileToSave.getAbsolutePath(), imageFormat);

                    if (!saveStatus) {
                        OverwriteDialog dialog = new OverwriteDialog();
                        dialog.setFilePath(saver.getCompleteName());

                        if (dialog.showDialog() != OverwriteDialog.CHOICE_OK)
                            break;
                    } else {
                        break;
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(window,
                            e.getMessage(),
                            "IO Error.",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }

            // If the saving process has failed both times.
            if (!saveStatus && counter == 2) {
                JOptionPane.showMessageDialog(window,
                        "Error: cannot save the image.",
                        "IO Error.",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
