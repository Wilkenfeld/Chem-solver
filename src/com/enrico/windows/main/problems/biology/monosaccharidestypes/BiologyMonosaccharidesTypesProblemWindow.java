package com.enrico.windows.main.problems.biology.monosaccharidestypes;

import com.enrico.widgets.buttons.DefaultButton;
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
    private JComboBox molecularTypeComboBox;

    public static final String TITLE = "Monosaccharides types";
    public static final String BIOLOGY_MONOSACCHARIDES_PROBLEM_IDENTIFIER =
            "BIOLOGY_MONOSACCHARIDES_PROBLEM_IDENTIFIER";

    public BiologyMonosaccharidesTypesProblemWindow() {
        super(TITLE);

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
        moreInfoButton = new DefaultButton("More info");
    }

    private void calculateMonosaccharideType(String variableN) {
        int intN = Integer.parseInt(variableN);

        int carbonNumber, hydrogenNumber, oxygenNumber;
        carbonNumber = oxygenNumber = intN;
        hydrogenNumber = intN * 2;


    }
}
