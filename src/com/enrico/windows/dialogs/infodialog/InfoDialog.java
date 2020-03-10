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

package com.enrico.windows.dialogs.infodialog;

import com.enrico.programresources.FontResources;
import com.enrico.widgets.buttons.ProgramButton;
import com.enrico.widgets.label.ProgramLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InfoDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private ProgramLabel infoLbl;
    private ProgramLabel titleLbl;

    public InfoDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        titleLbl.setFont(FontResources.titleFont);
        infoLbl.setFont(FontResources.normalTextFont);

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setSize(new Dimension(500, 350));
        setResizable(false);
        setTitle("About ChemSolver");
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void showDialog() {
        setVisible(true);
        pack();
    }

    public static void main(String[] args) {
        InfoDialog dialog = new InfoDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        buttonOK = new ProgramButton("OK");
    }
}
