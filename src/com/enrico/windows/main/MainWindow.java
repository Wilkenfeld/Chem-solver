package com.enrico.windows.main;

import com.enrico.interfaces.FontInterface;
import com.enrico.widgets.buttons.DefaultButton;
import com.enrico.widgets.menu.MainMenuBar;
import com.enrico.windows.BasicWindow;
import com.enrico.windows.dialogs.ProblemChooserDialog;
import com.enrico.windows.dialogs.ProblemListModel;
import com.enrico.windows.main.problems.biology.BiologyMonosaccharidesTypesProblemWindow;
import com.enrico.windows.main.problems.chemistry.MolecularShapeProblemWindow;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class MainWindow extends BasicWindow implements FontInterface {
    public JPanel mainPanel;
    private JLabel welcomeLbl;
    private JLabel subwelcomeLbl;
    private DefaultButton solveButton;

    private static final String title = "Chem solver";

    // Hash map to identify the various possible problems
    private HashMap<String, String> problemToWindowCodeHashMap = new HashMap<>();

    public MainWindow() {
        super(title);

        setContentPane(mainPanel);

        MainMenuBar menuBar = new MainMenuBar();
        setJMenuBar(menuBar);

        welcomeLbl.setFont(titleFont);
        subwelcomeLbl.setFont(subtitleFont);

        Dimension mainWinDimension = new Dimension(500, 350);
        setSize(mainWinDimension);
        setResizable(false);
        setPreferredSize(mainWinDimension);

        problemToWindowCodeHashMap.put(ProblemListModel.chemProblems[0], MolecularShapeProblemWindow.MOLECULAR_SHAPE_WINDOW_IDENTIFIER);
        problemToWindowCodeHashMap.put(ProblemListModel.chemProblems[1], BiologyMonosaccharidesTypesProblemWindow.BIOLOGY_MONOSACCHARIDES_PROBLEM_IDENTIFIER);
    }

    private void createUIComponents() {
        solveButton = new DefaultButton("Solve problem!");
        solveButton.addActionListener(actionEvent -> {
            ProblemChooserDialog problemChooserDialog = new ProblemChooserDialog();
            String res = problemChooserDialog.showDialog();
            System.out.println(res);

            for (Map.Entry<String, String> val : problemToWindowCodeHashMap.entrySet()) {
                if (res.equals(val.getKey())) {
                    res = val.getValue();
                }
            }

            BasicWindow win = null;

            switch (res) {
                case MolecularShapeProblemWindow.MOLECULAR_SHAPE_WINDOW_IDENTIFIER:
                    win = new MolecularShapeProblemWindow();
                break;

                case BiologyMonosaccharidesTypesProblemWindow.BIOLOGY_MONOSACCHARIDES_PROBLEM_IDENTIFIER:
                    win = new BiologyMonosaccharidesTypesProblemWindow();
                break;

                case ProblemChooserDialog.NO_PROBLEM_CHOOSED:
                    return;
            }

            if (win != null)
                win.showWindow();

            dispose();
        });
    }
}
