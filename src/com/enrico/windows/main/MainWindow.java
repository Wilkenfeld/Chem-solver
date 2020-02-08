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

/*
Chem solver. A multi-platform chemistry and physics problem solver.
 <br>  Copyright (C) 2019  Giacalone Enrico
 <br>
 <br>  This program is free software: you can redistribute it and/or modify
 <br>  it under the terms of the GNU General Public License as published by
 <br>  the Free Software Foundation, either version 3 of the License, or
 <br>  (at your option) any later version.
 <br>
 <br>  This program is distributed in the hope that it will be useful,
 <br>  but WITHOUT ANY WARRANTY; without even the implied warranty of
 <br>  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 <br>  GNU General Public License for more details.
 <br>
 <br>  You should have received a copy of the GNU General Public License
 <br>  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.enrico.windows.main;

import com.enrico.programresources.FontResources;
import com.enrico.widgets.buttons.ProgramButton;
import com.enrico.widgets.menu.MainMenuBar;
import com.enrico.windows.BasicWindow;
import com.enrico.windows.dialogs.ProblemChooserDialog;
import com.enrico.windows.dialogs.ProblemListModel;
import com.enrico.windows.main.problems.biology.monosaccharidestypes.BiologyMonosaccharidesTypesProblemWindow;
import com.enrico.windows.main.problems.chemistry.compoundclassification.CompoundClassificationProblemWindow;
import com.enrico.windows.main.problems.chemistry.molecularshape.MolecularShapeProblemWindow;
import com.enrico.windows.main.problems.chemistry.moleculebuilder.MoleculeBuilderWindow;
import com.enrico.widgets.label.ProgramLabel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class MainWindow extends BasicWindow {
    public JPanel mainPanel;
    private ProgramLabel welcomeLbl;
    private ProgramLabel subwelcomeLbl;
    private ProgramButton solveButton;

    private static final String title = "Chem solver";

    // Hash map to identify the various possible problems
    private HashMap<String, String> problemToWindowCodeHashMap = new HashMap<>();

    public MainWindow() {
        super(title);

        setContentPane(mainPanel);

        MainMenuBar menuBar = new MainMenuBar();
        setJMenuBar(menuBar);
        

        Dimension mainWinDimension = new Dimension(500, 350);
        setSize(mainWinDimension);
        setResizable(false);
        setPreferredSize(mainWinDimension);

        problemToWindowCodeHashMap.put(ProblemListModel.chemProblems[0], MolecularShapeProblemWindow.MOLECULAR_SHAPE_WINDOW_IDENTIFIER);
        problemToWindowCodeHashMap.put(ProblemListModel.chemProblems[1], BiologyMonosaccharidesTypesProblemWindow.BIOLOGY_MONOSACCHARIDES_PROBLEM_IDENTIFIER);
    }

    private void createUIComponents() {
        solveButton = new ProgramButton("Solve problem!");
        solveButton.addActionListener(actionEvent -> {
            ProblemChooserDialog problemChooserDialog = new ProblemChooserDialog();
            String res = problemChooserDialog.showDialog();
            System.out.println(res);

            for (Map.Entry<String, String> val : problemToWindowCodeHashMap.entrySet()) {
                if (res.equals(val.getKey())) {
                    res = val.getValue();
                }
            }

            BasicWindow win = null;

            switch (res) {
                case MolecularShapeProblemWindow.MOLECULAR_SHAPE_WINDOW_IDENTIFIER:
                    win = new MolecularShapeProblemWindow();
                break;

                case BiologyMonosaccharidesTypesProblemWindow.BIOLOGY_MONOSACCHARIDES_PROBLEM_IDENTIFIER:
                    win = new BiologyMonosaccharidesTypesProblemWindow();
                break;

                case CompoundClassificationProblemWindow.TITLE:
                    win = new CompoundClassificationProblemWindow();
                break;

                case MoleculeBuilderWindow.TITLE:
                    win = new MoleculeBuilderWindow();
                break;

                case ProblemChooserDialog.NO_PROBLEM_CHOOSED:
                    return;
            }

            if (win != null)
                win.showWindow();

            dispose();
        });
    }
}
