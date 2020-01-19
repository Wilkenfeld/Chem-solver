package com.enrico.windows.main.problems.chemistry.moleculebuilder;

import com.enrico.widgets.canvas.Canvas;
import com.enrico.widgets.imagebutton.ImageButton;
import com.enrico.widgets.menu.ProblemWindowMenuBar;
import com.enrico.windows.main.problems.GenericProblemWindow;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public final class MoleculeBuilderWindow extends GenericProblemWindow {
    public static final String TITLE =
            "Molecule Builder";
    private Canvas canvas;
    private JPanel mainPanel;
    private JTabbedPane atomsPane;

    // Alkaline metals buttons.
    private ImageButton sodiumBtn;
    private ImageButton potassiumBtn;
    private ImageButton lithiumBtn;
    private ImageButton rubidiumBtn;
    private ImageButton cesiumBtn;
    private ImageButton franciumBtn;

    public MoleculeBuilderWindow() {
        super(TITLE);

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

    private void createUIComponents() {
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(1000, 1000));

        URL imagePath;

        try {
            // Alkaline metals button.
            imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_metals/atom_icon_lithium.png");
            lithiumBtn = new ImageButton(imagePath, ImageButton.IMAGE_BUTTON_DEFAULT_DIMENSION, ImageButton.IMAGE_BUTTON_DEFAULT_DIMENSION);

            imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_metals/atom_icon_sodium.png");
            sodiumBtn = new ImageButton(imagePath, ImageButton.IMAGE_BUTTON_DEFAULT_DIMENSION, ImageButton.IMAGE_BUTTON_DEFAULT_DIMENSION);

            imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_metals/atom_icon_potassium.png");
            potassiumBtn = new ImageButton(imagePath, ImageButton.IMAGE_BUTTON_DEFAULT_DIMENSION, ImageButton.IMAGE_BUTTON_DEFAULT_DIMENSION);

            imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_metals/atom_icon_rubidium.png");
            rubidiumBtn = new ImageButton(imagePath, ImageButton.IMAGE_BUTTON_DEFAULT_DIMENSION, ImageButton.IMAGE_BUTTON_DEFAULT_DIMENSION);

            imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_metals/atom_icon_cesium.png");
            cesiumBtn = new ImageButton(imagePath, ImageButton.IMAGE_BUTTON_DEFAULT_DIMENSION, ImageButton.IMAGE_BUTTON_DEFAULT_DIMENSION);

            imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_metals/atom_icon_francium.png");
            franciumBtn = new ImageButton(imagePath, ImageButton.IMAGE_BUTTON_DEFAULT_DIMENSION, ImageButton.IMAGE_BUTTON_DEFAULT_DIMENSION);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Can't find internal assets.", JOptionPane.ERROR_MESSAGE);
        }
    }
}
