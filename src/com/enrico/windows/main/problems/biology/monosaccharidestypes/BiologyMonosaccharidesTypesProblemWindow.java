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

package com.enrico.windows.main.problems.biology.monosaccharidestypes;

import com.enrico.programresources.FontResources;
import com.enrico.widgets.buttons.ProgramButton;
import com.enrico.widgets.combobox.DefaultComboBox;
import com.enrico.widgets.combobox.DefaultComboBoxItem;
import com.enrico.widgets.menu.ProblemWindowMenuBar;
import com.enrico.windows.main.problems.GenericProblemWindow;
import com.enrico.widgets.label.ProgramLabel;
import com.enrico.widgets.textfiled.ProgramTextField;

import javax.swing.*;

public final class BiologyMonosaccharidesTypesProblemWindow extends GenericProblemWindow {
    private JPanel mainPanel;
    private ProgramTextField nVariableTxtField;
    private ProgramLabel monosaccharideTypeLbl;
    private JButton moreInfoButton;
    private DefaultComboBox moleculeTypeComboBox;
    private ProgramLabel monosaccharideLbl;
    private ProgramLabel nVarLbl;
    private ProgramLabel molecularTypeLbl;

    public static final String TITLE = "Monosaccharides types";
    public static final String BIOLOGY_MONOSACCHARIDES_PROBLEM_IDENTIFIER =
            "BIOLOGY_MONOSACCHARIDES_PROBLEM_IDENTIFIER";

    private static final String ALDOSE_TXT = "Aldose";
    private static final String ALDOSE_VAL = "ALDOSE_VAL";

    private static final String KETONE_TXT = "Ketone";
    private static final String KETONE_VAL = "KETONE_VAL";

    private static final DefaultComboBoxItem[] moleculeTypes = new DefaultComboBoxItem[2];

    public BiologyMonosaccharidesTypesProblemWindow() {
        super(TITLE);
        setResizable(false);

        ProblemWindowMenuBar problemWindowMenuBar = new ProblemWindowMenuBar(this);
        setJMenuBar(problemWindowMenuBar);

        setContentPane(mainPanel);
    }

    private void createUIComponents() {
        moleculeTypes[0] = new DefaultComboBoxItem(ALDOSE_TXT, ALDOSE_VAL);
        moleculeTypes[1] = new DefaultComboBoxItem(KETONE_TXT, KETONE_VAL);

        moreInfoButton = new ProgramButton("More info");
        moleculeTypeComboBox = new DefaultComboBox(moleculeTypes);
    }

    private void calculateMonosaccharideType(String variableN) {
        int intN = Integer.parseInt(variableN);

        int carbonNumber, hydrogenNumber, oxygenNumber;
        carbonNumber = oxygenNumber = intN;
        hydrogenNumber = intN * 2;

        if (carbonNumber == 6 &&
            hydrogenNumber == 12 &&
            oxygenNumber == 6) {
            if (moleculeTypeComboBox.getSelectedObjects()[0].toString().equals(ALDOSE_TXT)) {
                monosaccharideTypeLbl.setText("Glucose");
            } else {
                monosaccharideTypeLbl.setText("Fructose");
            }
        }
    }

    @Override
    public void saveProject() {
    }

    @Override
    public void solveProblem() {
        String strVariableN = nVariableTxtField.getText();
        calculateMonosaccharideType(strVariableN);
    }
}
