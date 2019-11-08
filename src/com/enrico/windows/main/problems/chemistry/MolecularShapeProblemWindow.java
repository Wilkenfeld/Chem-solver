package com.enrico.windows.main.problems.chemistry;

import com.enrico.chemistry.atoms.Atom;
import com.enrico.chemistry.formulaparser.FormulaParser;
import com.enrico.chemistry.molecule.Molecule;
import com.enrico.interfaces.FontInterface;
import com.enrico.widgets.canvas.Canvas;
import com.enrico.widgets.menu.ProblemWindowMenuBar;
import com.enrico.windows.BasicWindow;

import javax.swing.*;
import java.awt.*;

public final class MolecularShapeProblemWindow extends BasicWindow implements FontInterface {
    private JPanel mainPanel;
    private Canvas mainCanvas;
    private JTextField textFieldFormula;
    private JTextPane dataPane;
    private JLabel formulaLbl;
    private JLabel resultLbl;

    public static final String MOLECULAR_RETURN_STATUS = "Molecular shape";
    public static final String MOLECULAR_SHAPE_WINDOW_IDENTIFIER = "MOLECULAR_SHAPE_WINDOW_IDENTIFIER";

    public MolecularShapeProblemWindow() {
        super(MOLECULAR_RETURN_STATUS);

        setContentPane(mainPanel);
        Dimension windowDimension = new Dimension(1000, 700);
        setPreferredSize(windowDimension);
        setMinimumSize(windowDimension);

        ProblemWindowMenuBar problemWindowMenuBar = new ProblemWindowMenuBar(this);
        setJMenuBar(problemWindowMenuBar);

        dataPane.setFont(normalTextFont);
        formulaLbl.setFont(normalTextFont);
        resultLbl.setFont(normalTextFont);

        problemWindowMenuBar.problemMenuItemSolve.addActionListener(ActionEvent -> {
            String formula = textFieldFormula.getText();
            if (formula.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                                     "Please insert a formula to evaluate.",
                                        "No formula found.",
                                             JOptionPane.ERROR_MESSAGE);
                return;
            }

            FormulaParser parser = new FormulaParser(formula);

            Atom[] atomList = parser.getAtoms();
            Molecule molecule = new Molecule(atomList);
            Atom centralAtom = molecule.getCentralAtom();

            molecule.calculateShape();

            mainCanvas.setAtomList(atomList);
            mainCanvas.setCentralAtom(centralAtom);
            mainCanvas.setMolecule(molecule);

            mainCanvas.repaint();
            //MoleculeDrawThread thread = new MoleculeDrawThread(mainCanvas);
            //thread.start();
        });
    }

    public void createUIComponents() {
        mainCanvas = new Canvas();
    }
}
