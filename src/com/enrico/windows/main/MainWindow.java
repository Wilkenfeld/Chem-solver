package com.enrico.windows.main;

import com.enrico.Main;
import com.enrico.interfaces.FontInterface;
import com.enrico.widgets.buttons.DefaultButton;
import com.enrico.widgets.menu.MainMenuBar;
import com.enrico.windows.BasicWindow;
import com.enrico.windows.dialogs.ProblemChooserDialog;

import javax.swing.*;
import java.awt.*;

public final class MainWindow extends BasicWindow implements FontInterface {
    public JPanel mainPanel;
    private JLabel welcomeLbl;
    private JLabel subwelcomeLbl;
    private DefaultButton solveButton;

    private final Dimension mainWinDimension = new Dimension(500, 350);
    private static final String title = "Chem solver";

    public MainWindow() {
        super(title, EXIT_ON_CLOSE_WINDOW);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        setTitle(title);

        MainMenuBar menuBar = new MainMenuBar();
        setJMenuBar(menuBar);

        welcomeLbl.setFont(titleFont);
        subwelcomeLbl.setFont(subtitleFont);

        pack();
        setSize(mainWinDimension);
        setResizable(false);
        setPreferredSize(mainWinDimension);
        setModal(true);
    }

    private void createUIComponents() {
        solveButton = new DefaultButton("Solve problem!");
        solveButton.addActionListener(actionEvent -> {
            ProblemChooserDialog problemChooserDialog = new ProblemChooserDialog();
            String res = problemChooserDialog.showDialog();
            System.out.println(res);

            retStatus = res;
            dispose();
        });
    }
}
