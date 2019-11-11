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

        for (Atom atom : atomList ) {
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

        if (central == null)
            throw new IllegalMoleculeException(this);

        centralAtom = central;
    }

    private void findDoublets() {
        doubletsNumber = centralAtom.getDoublets();
    }

    private void setBindedAtoms() {
        for (Atom atom : atomList) {

            Atom.checkIfStable(atom);

            if (atom != centralAtom)
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
        else if (bindedAtoms.size() == 2 && doubletsNumber == 0)
            moleculeShape = ShapeEnum.LineShape;
        else
            throw new IllegalMoleculeException(this);
    }
}
