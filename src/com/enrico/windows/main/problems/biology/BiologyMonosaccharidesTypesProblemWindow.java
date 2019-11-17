package com.enrico.windows.main.problems.biology;

import com.enrico.widgets.menu.ProblemWindowMenuBar;
import com.enrico.windows.BasicWindow;

import javax.swing.*;

public class BiologyMonosaccharidesTypesProblemWindow extends BasicWindow {
    private JPanel mainPanel;

    public static final String TITLE = "Monosaccharides types";
    public static final String BIOLOGY_MONOSACCHARIDES_PROBLEM_IDENTIFIER =
            "BIOLOGY_MONOSACCHARIDES_PROBLEM_IDENTIFIER";

    public BiologyMonosaccharidesTypesProblemWindow() {
        super(TITLE);

        ProblemWindowMenuBar problemWindowMenuBar = new ProblemWindowMenuBar(this);
        setJMenuBar(problemWindowMenuBar);

        setContentPane(mainPanel);
    }
}
