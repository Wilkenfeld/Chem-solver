package com.enrico.windows.main.problems.chemistry;

import com.enrico.chemistry.atoms.Atom;
import com.enrico.chemistry.formulaparser.FormulaParser;
import com.enrico.chemistry.molecule.Molecule;
import com.enrico.interfaces.FontInterface;
import com.enrico.widgets.canvas.Canvas;
import com.enrico.widgets.canvas.FileTypeFilter;
import com.enrico.widgets.canvas.ImageSaver;
import com.enrico.widgets.menu.ProblemWindowMenuBar;
import com.enrico.windows.BasicWindow;
import com.enrico.windows.dialogs.overwrite.OverwriteDialog;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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

            FormulaParser parser;
            Atom[] atomList;
            Molecule molecule;
            Atom centralAtom;

            try {
                parser = new FormulaParser(formula);

                atomList = parser.getAtoms();
                molecule = new Molecule(atomList, formula);
                centralAtom = molecule.getCentralAtom();

                molecule.calculateShape();
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this,
                        e.getMessage(),
                        "Formula error.",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            mainCanvas.setAtomList(atomList);
            mainCanvas.setCentralAtom(centralAtom);
            mainCanvas.setMolecule(molecule);
            
            mainCanvas.repaint();
        });

        JMenuItem saveImageItem = problemWindowMenuBar.problemMenu.add("Save image");
        saveImageItem.addActionListener(actionEvent -> saveImageProcedure());
        saveImageItem.setFont(menuBarFont);
    }

    public void createUIComponents() {
        mainCanvas = new Canvas();
    }

    private void saveImageProcedure() {
        JFileChooser fileChooser = new JFileChooser();

        // Add extension types.
        fileChooser.addChoosableFileFilter(new FileTypeFilter(".png", "PNG file"));
        fileChooser.addChoosableFileFilter(new FileTypeFilter(".jpg", "JPG file"));

        // Disable the possibility for user to save in some other format other than jpg and png.
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Save file.");

        int selection = fileChooser.showSaveDialog(this);
        boolean saveStatus = false;
        int imageFormat;

        if (selection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Check extension type.
            FileTypeFilter filter = (FileTypeFilter) fileChooser.getFileFilter();
            if (filter.getExtension().equals(".png"))
                imageFormat = ImageSaver.IMAGE_PNG_FORMAT;
            else
                imageFormat = ImageSaver.IMAGE_JPG_FORMAT;

            int counter; // Used to count how many times the dialog is shown.

            for (counter = 0; counter < 3; counter++) {
                try {
                    ImageSaver saver = new ImageSaver(mainCanvas);

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
}
