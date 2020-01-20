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

    // Transition metals buttons.
    private ImageButton cadmiumBtn;
    private ImageButton chopperBtn;
    private ImageButton chromiumBtn;
    private ImageButton cobaltBtn;
    private ImageButton goldBtn;
    private ImageButton hafniumBtn;
    private ImageButton iridiumBtn;
    private ImageButton ironBtn;
    private ImageButton manganeseBtn;
    private ImageButton mercuryBtn;
    private ImageButton molybdenumBtn;
    private ImageButton nickelBtn;
    private ImageButton niobiumBtn;
    private ImageButton osmiumBtn;
    private ImageButton palladiumBtn;
    private ImageButton platinumBtn;
    private ImageButton rheniumBtn;
    private ImageButton rhodiumBtn;
    private ImageButton rutheniumBtn;
    private ImageButton scandiumBtn;
    private ImageButton silverBtn;
    private ImageButton tantalumBtn;
    private ImageButton technetiumBtn;
    private ImageButton titaniumBtn;
    private ImageButton tungstenBtn;
    private ImageButton vanadiumBtn;
    private ImageButton yttriumBtn;
    private ImageButton zincBtn;
    private ImageButton zirconiumBtn;
    private ImageButton carbonBtn;
    private ImageButton hydrogenBtn;
    private ImageButton nitrogenBtn;
    private ImageButton oxygenBtn;
    private ImageButton phosphorusBtn;
    private ImageButton seleniumBtn;
    private ImageButton sulfurBtn;

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
            initializeTransitionMetalsWindow();
            initializeNonmetals();
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

    private void initializeTransitionMetalsWindow() throws IOException {
        URL imagePath;

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_cadmium.png");
        cadmiumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_chopper.png");
        chopperBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_chromium.png");
        chromiumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_cobalt.png");
        cobaltBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_gold.png");
        goldBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_hafnium.png");
        hafniumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_iridium.png");
        iridiumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_iron.png");
        ironBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_manganese.png");
        manganeseBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_mercury.png");
        mercuryBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_molybdenum.png");
        molybdenumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_nickel.png");
        nickelBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_niobium.png");
        niobiumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_osmium.png");
        osmiumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_palladium.png");
        palladiumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_platinum.png");
        platinumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_rhenium.png");
        rheniumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_rhodium.png");
        rhodiumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_ruthenium.png");
        rutheniumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_scandium.png");
        scandiumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_silver.png");
        silverBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_tantalum.png");
        tantalumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_technetium.png");
        technetiumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_titanium.png");
        titaniumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_tungsten.png");
        tungstenBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_vanadium.png");
        vanadiumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_yttrium.png");
        yttriumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_zinc.png");
        zincBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/transition_metals/atom_icon_zirconium.png");
        zirconiumBtn = new ImageButton(imagePath, null);
    }

    private void initializeNonmetals() throws IOException {
        URL imagePath;

        imagePath = getClass().getClassLoader().getResource("molecule_icons/nonmetals/atom_icon_carbon.png");
        carbonBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/nonmetals/atom_icon_hydrogen.png");
        hydrogenBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/nonmetals/atom_icon_nitrogen.png");
        nitrogenBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/nonmetals/atom_icon_oxygen.png");
        oxygenBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/nonmetals/atom_icon_phosphorus.png");
        phosphorusBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/nonmetals/atom_icon_selenium.png");
        seleniumBtn = new ImageButton(imagePath, null);

        imagePath = getClass().getClassLoader().getResource("molecule_icons/nonmetals/atom_icon_sulfur.png");
        sulfurBtn = new ImageButton(imagePath, null);
    }
}
