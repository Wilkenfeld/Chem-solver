package com.enrico.chemistry.molecule;

import com.enrico.chemistry.atoms.Atom;
import com.enrico.chemistry.atoms.HydrogenAtom;
import com.enrico.chemistry.molecule.exceptions.IllegalMoleculeException;

import java.util.ArrayList;

public class Molecule {
    private Atom[] atomList;
    private ShapeEnum moleculeShape;

    // AXE Parameters.
    private Atom centralAtom;
    private ArrayList<Atom> bindedAtoms; // Atoms binded to the central atom.
    private int doubletsNumber; // Doublets of central atom.

    public enum ShapeEnum {
        SquareShape,
        PyramidShape,
        LineShape // Eg: O = C = O
    }

    public Molecule(Atom[] atomList) throws IllegalMoleculeException {
        this.atomList = atomList;
        doubletsNumber = 0;

        bindedAtoms = new ArrayList<>();

        findCentralAtom();
        findDoublets();
        setBindedAtoms();
    }

    public ShapeEnum getMoleculeShape() {
        return moleculeShape;
    }

    public Atom getCentralAtom() {
        return centralAtom;
    }

    public ArrayList<Atom> getBindedAtoms() {
        return bindedAtoms;
    }

    private void findCentralAtom() throws IllegalArgumentException {
        Atom central = null;

        double currentElectronegativity;
        double minElectronegativity = 0.0;

        for (Atom atom : atomList) {
            Atom.checkIfUsable(atom);

            if (minElectronegativity == 0.0 && atom.getClass() != HydrogenAtom.class) {
                minElectronegativity = atom.getElectronegativity();
                central = atom;
                continue;
            }

            currentElectronegativity = atom.getElectronegativity();

            if (currentElectronegativity < minElectronegativity) {
                if (atom.getClass() == HydrogenAtom.class)
                    continue;

                central = atom;
                minElectronegativity = currentElectronegativity;
            }
        }

        // It is possible for Hydrogen to be the central atom only for the molecule H2.
        if (central == null) {
            if (atomList[0].getClass() == HydrogenAtom.class && atomList[1].getClass() == HydrogenAtom.class) {
                central = atomList[0];
            } else { // No valid molecule.
                throw new IllegalMoleculeException(this);
            }
        }

        centralAtom = central;
    }

    private void findDoublets() {
        doubletsNumber = centralAtom.getDoublets();
    }

    private void setBindedAtoms() {
        if (atomList.length == 2 && centralAtom.getClass() == HydrogenAtom.class &&
            atomList[1].getClass() == HydrogenAtom.class) {
            bindedAtoms.add(atomList[1]);
            return;
        }

        for (Atom atom : atomList) {

            Atom.checkIfUsable(atom);

            if (!atom.equals(centralAtom))
                bindedAtoms.add(atom);
        }
    }

    public void calculateShape() throws IllegalMoleculeException {
        if ((bindedAtoms.size() == 4 && doubletsNumber == 0) ||
            (bindedAtoms.size() == 3 && doubletsNumber == 0))
            moleculeShape = ShapeEnum.SquareShape;
        else if ((bindedAtoms.size() == 2 && doubletsNumber == 2) ||
                (bindedAtoms.size() == 2 && doubletsNumber == 5))
            moleculeShape = ShapeEnum.PyramidShape;
        else if ((bindedAtoms.size() == 2 && doubletsNumber == 0) ||
                 (bindedAtoms.size() == 1 && doubletsNumber == 0))
            moleculeShape = ShapeEnum.LineShape;
        else
            throw new IllegalMoleculeException(this);
    }
}
