package com.enrico.windows.main.problems.biology.monosaccharidestypes;

import com.enrico.widgets.buttons.DefaultButton;
import com.enrico.widgets.combobox.DefaultComboBox;
import com.enrico.widgets.combobox.DefaultComboBoxItem;
import com.enrico.widgets.menu.ProblemWindowMenuBar;
import com.enrico.windows.BasicWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BiologyMonosaccharidesTypesProblemWindow extends BasicWindow {
    private JPanel mainPanel;
    private JTextField nVariableTxtField;
    private JLabel monosaccharideTypeLbl;
    private DefaultButton moreInfoButton;
    private DefaultComboBox moleculeTypeComboBox;

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

        problemWindowMenuBar.problemMenuItemSolve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        setContentPane(mainPanel);
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
    }
}
