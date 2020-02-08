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

package com.enrico.windows.main.problems.chemistry.molecularshape;

import com.enrico.chemistry.atoms.scientific.GenericScientificAtom;
import com.enrico.chemistry.formulaparser.FormulaParser;
import com.enrico.chemistry.molecule.Molecule;
import com.enrico.programresources.FontResources;
import com.enrico.project.saver.FormulaShapeProjectSaver;
import com.enrico.project.saver.OverwriteException;
import com.enrico.project.saver.ProjectSaver;
import com.enrico.widgets.canvas.moleculeshapecanvas.MoleculeShapeCanvas;
import com.enrico.widgets.canvas.FileTypeFilter;
import com.enrico.widgets.canvas.ImageSaver;
import com.enrico.widgets.menu.ProblemWindowMenuBar;
import com.enrico.windows.dialogs.overwrite.OverwriteDialog;
import com.enrico.windows.dialogs.savedialog.SaveDialog;
import com.enrico.windows.dialogs.savedialog.saveProject.ProjectSaveDialog;
import com.enrico.windows.main.problems.GenericProblemWindow;
import com.enrico.widgets.textfiled.ProgramTextField;
import com.enrico.widgets.label.ProgramLabel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class MolecularShapeProblemWindow extends GenericProblemWindow {
    private JPanel mainPanel;
    private MoleculeShapeCanvas mainMoleculeShapeCanvas;
    private ProgramTextField textFieldFormula;
    private JTextPane dataPane;
    private ProgramLabel formulaLbl;
    private ProgramLabel resultLbl;

    public static final String MOLECULAR_RETURN_STATUS = "Molecular shape";
    public static final String MOLECULAR_SHAPE_WINDOW_IDENTIFIER = "MOLECULAR_SHAPE_WINDOW_IDENTIFIER";
    public static final String PROJECT_ID = "molecular_shape";

    public MolecularShapeProblemWindow() {
        super(MOLECULAR_RETURN_STATUS);

        setContentPane(mainPanel);
        Dimension windowDimension = new Dimension(1000, 700);
        setPreferredSize(windowDimension);
        setMinimumSize(windowDimension);

        ProblemWindowMenuBar problemWindowMenuBar = new ProblemWindowMenuBar(this);
        setJMenuBar(problemWindowMenuBar);

        JMenuItem saveImageItem = problemWindowMenuBar.problemMenu.add("Save image");
        saveImageItem.addActionListener(actionEvent -> saveImageProcedure());
        saveImageItem.setFont(FontResources.menuBarFont);
        problemWindowMenuBar.saveMenuItem.addActionListener(actionEvent -> saveProject());
    }

    public void setFormulaOnTextField(String formula) {
        textFieldFormula.setText(formula);
    }

    @Override
    public void solveProblem() {
        String formula = textFieldFormula.getText();
        if (formula.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please insert a formula to evaluate.",
                    "No formula found.",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        FormulaParser parser;
        GenericScientificAtom[] GenericScientificAtomList;
        Molecule molecule;
        GenericScientificAtom centralGenericScientificAtom;

        try {
            parser = new FormulaParser(formula);

            GenericScientificAtomList = parser.getAtoms();
            molecule = new Molecule(GenericScientificAtomList, formula);
            centralGenericScientificAtom = molecule.getCentralGenericScientificAtom();

            molecule.calculateShape();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Formula error.",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        mainMoleculeShapeCanvas.setGenericScientificAtomList(GenericScientificAtomList);
        mainMoleculeShapeCanvas.setCentralGenericScientificAtom(centralGenericScientificAtom);
        mainMoleculeShapeCanvas.setMolecule(molecule);

        mainMoleculeShapeCanvas.repaint();

        dataPane.setText(molecule.getOperationString());
    }

    public void createUIComponents() {
        mainMoleculeShapeCanvas = new MoleculeShapeCanvas();
    }

    private void saveImageProcedure() {
        SaveDialog saveDialog = new SaveDialog(this);

        int selection = saveDialog.showDialog();
        boolean saveStatus = false;
        int imageFormat;

        if (selection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = saveDialog.getSelectedFile();

            // Check extension type.
            FileTypeFilter filter = saveDialog.getFileTypeFilter();
            if (filter.getExtension().equals(SaveDialog.PNG_EXTENSION))
                imageFormat = ImageSaver.IMAGE_PNG_FORMAT;
            else
                imageFormat = ImageSaver.IMAGE_JPG_FORMAT;

            int counter; // Used to count how many times the dialog is shown.

            for (counter = 0; counter < 3; counter++) {
                try {
                    ImageSaver saver = new ImageSaver(mainMoleculeShapeCanvas);

                    saveStatus = saver.saveImage(fileToSave.getAbsolutePath(), imageFormat);

                    if (!saveStatus) {
                        OverwriteDialog dialog = new OverwriteDialog();
                        dialog.setFilePath(saver.getCompleteName());

                        if (dialog.showDialog() != OverwriteDialog.CHOICE_OK)
                            break;
                    } else {
                        break;
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this,
                            e.getMessage(),
                            "IO Error.",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }

            // If the saving process has failed both times.
            if (!saveStatus && counter == 2) {
                JOptionPane.showMessageDialog(this,
                        "Error: cannot save the image.",
                        "IO Error.",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void saveProject() {
        ProjectSaveDialog dialog = new ProjectSaveDialog(this);

        int selection = dialog.showDialog();
        boolean overwrite = false;

        if (selection == JFileChooser.APPROVE_OPTION) {

            String path = dialog.getSelectedFile().getAbsolutePath();

            for (int counter = 0; counter < 3; counter++) {
                HashMap<String, String> projectMap = new HashMap<>();
                projectMap.put("formula", textFieldFormula.getText());

                try {
                    FormulaShapeProjectSaver saver = new FormulaShapeProjectSaver(projectMap, path, overwrite);
                    saver.saveProject(PROJECT_ID);
                    break;
                }  catch (OverwriteException oe) {
                    OverwriteDialog overwriteDialog = new OverwriteDialog();
                    overwriteDialog.setFilePath(path + ProjectSaver.CHEM_SOLVER_PROJECT_FILE_EXTENSION);
                    overwriteDialog.showDialog();

                    if (dialog.showDialog() != OverwriteDialog.CHOICE_OK)
                        break;
                    else
                        overwrite = true;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                            e.getMessage(),
                            "Unexpected error.",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        }
    }
}