package com.enrico.windows.main.problems.chemistry.moleculebuilder;

import com.enrico.widgets.canvas.Canvas;
import com.enrico.widgets.menu.ProblemWindowMenuBar;
import com.enrico.windows.main.problems.GenericProblemWindow;

import javax.swing.*;
import java.awt.*;

public final class MoleculeBuilderWindow extends GenericProblemWindow {
    public static final String TITLE =
            "Molecule Builder";
    private Canvas canvas;
    private JPanel mainPanel;
    private JTabbedPane atomsPane;

    public MoleculeBuilderWindow() {
        super(TITLE);

        canvas.setPreferredSize(new Dimension(1000, 1000));

        setContentPane(mainPanel);

        // Minimum size.
        Dimension minWindowDimension = new Dimension(1000, 700);
        setMinimumSize(minWindowDimension);

        // Setting the window to fill the screen.
        Dimension fullScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(fullScreenDimension.width, fullScreenDimension.height);

        // Menu bar.
        ProblemWindowMenuBar menuBar = new ProblemWindowMenuBar(this);
        setJMenuBar(menuBar);
    }

    @Override
    public void solveProblem() {
    }

    @Override
    public void saveProject() {

    }
}
