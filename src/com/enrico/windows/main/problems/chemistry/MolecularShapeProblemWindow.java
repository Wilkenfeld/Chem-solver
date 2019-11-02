package com.enrico.windows.main.problems.chemistry;

import com.enrico.interfaces.StatusInterface;
import com.enrico.widgets.menu.ProblemWindowMenuBar;
import com.enrico.windows.BasicWindow;

import javax.swing.*;

public final class MolecularShapeProblemWindow extends BasicWindow implements StatusInterface {
    private JPanel mainPanel;

    public static final String MOLECULAR_RETURN_STATUS = "Molecular shape";
    public static final String MOLECULAR_SHAPE_WINDOW_IDENTIFIER = "MOLECULAR_SHAPE_WINDOW_IDENTIFIER";

    public MolecularShapeProblemWindow() {
        super(MOLECULAR_RETURN_STATUS);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);

        ProblemWindowMenuBar problemWindowMenuBar = new ProblemWindowMenuBar(this);
        setJMenuBar(problemWindowMenuBar);
    }
}
