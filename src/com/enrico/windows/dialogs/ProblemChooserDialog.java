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

package com.enrico.windows.dialogs;

import com.enrico.programresources.FontResources;
import com.enrico.widgets.buttons.ProgramButton;

import javax.swing.*;
import java.awt.event.*;

public class ProblemChooserDialog extends JDialog {
    private JPanel contentPane;
    private ProgramButton buttonOK;
    private ProgramButton buttonCancel;
    private JList chemProblemList;
    private JList physProblemList;
    private JLabel chemTitleLbl;
    private JLabel physTitleLbl;

    public static final String NO_PROBLEM_CHOOSED = "NO_PROBLEM_CHOOSED";

    private String choosedProblem = NO_PROBLEM_CHOOSED;

    @SuppressWarnings("unchecked")
    public ProblemChooserDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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

        setTitle("Choose a problem");
        setResizable(false);

        ProblemListModel chemProblemListModel = new ProblemListModel(ProblemListModel.CHEM_PROBLEM_TYPE);
        ProblemListModel physProblemListModel = new ProblemListModel(ProblemListModel.PHYS_PROBLEM_TYPE);

        chemProblemList.setModel(chemProblemListModel);
        physProblemList.setModel(physProblemListModel);

        chemProblemList.setFont(FontResources.normalTextFont);
        physProblemList.setFont(FontResources.normalTextFont);

        chemTitleLbl.setFont(FontResources.normalTextFont);
        physTitleLbl.setFont(FontResources.normalTextFont);
    }

    private void onOK() {

        if (!chemProblemList.isSelectionEmpty()) {
            choosedProblem = (String) chemProblemList.getSelectedValue();
        } else if (!physProblemList.isSelectionEmpty()) {
            choosedProblem = (String) physProblemList.getSelectedValue();
        }

        dispose();
    }

    private void onCancel() {
        choosedProblem = NO_PROBLEM_CHOOSED;
        dispose();
    }

    public String showDialog() {
        pack();
        setVisible(true);

        return choosedProblem;
    }

    public void createUIComponents() {
        buttonOK = new ProgramButton("OK");
        buttonCancel = new ProgramButton("Cancel");
    }

    public static void main(String[] args) {
        ProblemChooserDialog dialog = new ProblemChooserDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
