package com.enrico.windows.dialogs;

import com.enrico.windows.main.problems.chemistry.MolecularShapeProblemWindow;

import javax.swing.*;

final class ProblemListModel extends DefaultListModel {

    static final int CHEM_PROBLEM_TYPE = 0;
    static final int PHYS_PROBLEM_TYPE = 1;

    public static final String[] chemProblems = new String[] {
            MolecularShapeProblemWindow.MOLECULAR_RETURN_STATUS
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
