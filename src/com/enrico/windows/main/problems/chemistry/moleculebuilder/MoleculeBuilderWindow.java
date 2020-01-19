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

    // Alkaline earth metals buttons.
    private ImageButton berylliumBtn;
    private ImageButton magnesiumBtn;
    private ImageButton calciumBtn;
    private ImageButton strontiumBtn;
    private ImageButton bariumBtn;
    private ImageButton radiumBtn;

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

        // Initializing image buttons.
        try {
            initializeAlkalineButtons();
            initializeAlkalineEarthButtons();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Can't find internal assets.", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeAlkalineButtons() throws IOException {
        URL imagePath;

        imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_metals/atom_icon_lithium.png");
        lithiumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_metals/atom_icon_sodium.png");
        sodiumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_metals/atom_icon_potassium.png");
        potassiumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_metals/atom_icon_rubidium.png");
        rubidiumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_metals/atom_icon_cesium.png");
        cesiumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_metals/atom_icon_francium.png");
        franciumBtn = new ImageButton(imagePath, null);
    }

    private void initializeAlkalineEarthButtons() throws IOException {
        URL imagePath;

        imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_earth_metals/atom_icon_beryllium.png");
        berylliumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_earth_metals/atom_icon_magnesium.png");
        magnesiumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_earth_metals/atom_icon_calcium.png");
        calciumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_earth_metals/atom_icon_strontium.png");
        strontiumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_earth_metals/atom_icon_barium.png");
        bariumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/alkaline_earth_metals/atom_icon_radium.png");
        radiumBtn = new ImageButton(imagePath, null);
    }
}
