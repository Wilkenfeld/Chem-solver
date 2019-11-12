package com.enrico.widgets.canvas;

import com.enrico.chemistry.atoms.Atom;
import com.enrico.chemistry.molecule.Molecule;
import com.enrico.chemistry.molecule.atomgroup.AtomGroup;
import com.enrico.chemistry.molecule.shapedmolecule.ShapedMolecule;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canvas extends JPanel {
    private Atom[] atomList = null;
    private Atom centralAtom = null;
    private Molecule molecule = null;

    public Canvas() {
        super();

        setPreferredSize(new Dimension(100, 100));
        setBackground(new Color(0xC0C0C0));
        setOpaque(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int centerWidth = getWidth() / 2;
        int centerHeight = getHeight() / 2;

        if (atomList != null && centralAtom != null && molecule != null) {
            ShapedMolecule shapedMolecule = new ShapedMolecule(molecule, centerWidth, centerHeight);
            ArrayList<AtomGroup> atoms = shapedMolecule.getAtomGroups();

            for (AtomGroup atom : atoms) {
                ArrayList<ShapedMolecule.AtomPlaceCard> atomList = atom.getAtoms();
                for (ShapedMolecule.AtomPlaceCard atomListElement: atomList) {
                    g.drawString(atomListElement.getAtomSymbol(), atomListElement.getX(), atomListElement.getY());
                }
            }
        }

    }

    public void createUIComponents() {
    }

    public void setAtomList(Atom[] atomList) {
        this.atomList = atomList;
    }

    public void setCentralAtom(Atom centralAtom) {
        this.centralAtom = centralAtom;
    }

    public void setMolecule(Molecule molecule) {
        this.molecule = molecule;
    }

}
