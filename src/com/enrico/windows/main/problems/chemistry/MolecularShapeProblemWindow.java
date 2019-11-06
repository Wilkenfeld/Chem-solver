package com.enrico.windows.main.problems.chemistry;

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
            System.out.println("DONE");
        });
    }

    public void createUIComponents() {
        mainCanvas = new Canvas();
    }
}
