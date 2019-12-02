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
