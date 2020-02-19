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

package com.enrico.windows.dialogs.overwrite;

import com.enrico.widgets.buttons.ProgramButton;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public final class OverwriteDialog extends JDialog {
    private JPanel contentPane;
    private ProgramButton buttonOK;
    private ProgramButton buttonCancel;

    private String filePath;

    public static final int CHOICE_OK = 0;
    public static final int CHOICE_NO = 1;

    public int choice = CHOICE_OK;

    public OverwriteDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Overwrite");

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        File file = new File(filePath);
        if (!file.delete()) {
            JOptionPane.showMessageDialog(this,
                    "Error in deletion, cannot delete file.",
                    "IO Error.",
                    JOptionPane.WARNING_MESSAGE);
        }

        dispose();
    }

    private void onCancel() {
        choice = CHOICE_NO;
        dispose();
    }

    public static void main(String[] args) {
        OverwriteDialog dialog = new OverwriteDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        buttonOK = new ProgramButton("OK");
        buttonCancel = new ProgramButton("Cancel");
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int showDialog() {
        pack();
        setVisible(true);

        return choice;
    }
}
