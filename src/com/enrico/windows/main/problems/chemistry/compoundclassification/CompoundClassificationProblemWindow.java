package com.enrico.windows.main.problems.chemistry.compoundclassification;

import com.enrico.chemistry.atoms.Atom;
import com.enrico.chemistry.formulaparser.FormulaParser;
import com.enrico.chemistry.molecule.Molecule;
import com.enrico.widgets.menu.ProblemWindowMenuBar;
import com.enrico.windows.BasicWindow;

import javax.swing.*;

public final class CompoundClassificationProblemWindow extends BasicWindow {
    public static final String TITLE = "Compound classification";
    
    private JTextField formulaField;
    private JLabel numberOfElementsLbl;
    private JLabel moleculeTypeLbl;
    private JPanel mainPane;

    public CompoundClassificationProblemWindow() {
        super(TITLE);

        ProblemWindowMenuBar problemWindowMenuBar = new ProblemWindowMenuBar(this);
        setJMenuBar(problemWindowMenuBar);

        problemWindowMenuBar.problemMenuItemSolve.addActionListener(ActionListener -> solveProblem());

        setResizable(false);

        setContentPane(mainPane);
    }

    private void solveProblem() {
        String formula = formulaField.getText();
        if (formula.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please insert a formula to evaluate.",
                    "No formula found.",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        FormulaParser parser;
        Atom[] atomList;
        Molecule molecule;

        try {
            parser = new FormulaParser(formula);

            atomList = parser.getAtoms();
            molecule = new Molecule(atomList, formula);
            molecule.findCompoundType();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Formula error.",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int elementsNum = molecule.getElementsNum();
        numberOfElementsLbl.setText(String.valueOf(elementsNum));
        moleculeTypeLbl.setText("Compound type: " + molecule.getCompoundType().toString());
    }
}
