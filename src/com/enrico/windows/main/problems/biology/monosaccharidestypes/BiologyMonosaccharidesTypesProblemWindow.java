package com.enrico.windows.main.problems.biology.monosaccharidestypes;

import com.enrico.interfaces.FontInterface;
import com.enrico.widgets.buttons.DefaultButton;
import com.enrico.widgets.combobox.DefaultComboBox;
import com.enrico.widgets.combobox.DefaultComboBoxItem;
import com.enrico.widgets.menu.ProblemWindowMenuBar;
import com.enrico.windows.BasicWindow;

import javax.swing.*;

public class BiologyMonosaccharidesTypesProblemWindow extends BasicWindow implements FontInterface {
    private JPanel mainPanel;
    private JTextField nVariableTxtField;
    private JLabel monosaccharideTypeLbl;
    private DefaultButton moreInfoButton;
    private DefaultComboBox moleculeTypeComboBox;
    private JLabel monosaccharideLbl;
    private JLabel nVarLbl;
    private JLabel molecularTypeLbl;

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

        problemWindowMenuBar.problemMenuItemSolve.addActionListener(actionEvent -> {
            String strVariableN = nVariableTxtField.getText();
            calculateMonosaccharideType(strVariableN);
        });

        setContentPane(mainPanel);

        monosaccharideTypeLbl.setFont(normalTextFont);
        monosaccharideLbl.setFont(normalTextFont);
        nVarLbl.setFont(normalTextFont);
        molecularTypeLbl.setFont(normalTextFont);
    }

    private void createUIComponents() {
        moleculeTypes[0] = new DefaultComboBoxItem(ALDOSE_TXT, ALDOSE_VAL);
        moleculeTypes[1] = new DefaultComboBoxItem(KETONE_TXT, KETONE_VAL);

        moreInfoButton = new DefaultButton("More info");
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
}