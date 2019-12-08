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

package com.enrico.windows.dialogs;

import com.enrico.windows.main.problems.biology.monosaccharidestypes.BiologyMonosaccharidesTypesProblemWindow;
import com.enrico.windows.main.problems.chemistry.MolecularShapeProblemWindow;

import javax.swing.*;

public final class ProblemListModel extends DefaultListModel {

    static final int CHEM_PROBLEM_TYPE = 0;
    static final int PHYS_PROBLEM_TYPE = 1;

    public static final String[] chemProblems = new String[] {
            MolecularShapeProblemWindow.MOLECULAR_RETURN_STATUS,
            BiologyMonosaccharidesTypesProblemWindow.TITLE
    };

    public static final String[] physProblems = new String[] {
            "None"
    };

    @SuppressWarnings("unchecked")
    public ProblemListModel(int problemType) {
        super();

        if (problemType == CHEM_PROBLEM_TYPE) {
            for (String chemProblem : chemProblems) {
                addElement(chemProblem);
            }
        } else {
            for (String physProblem : physProblems) {
                addElement(physProblem);
            }
        }
    }
}
