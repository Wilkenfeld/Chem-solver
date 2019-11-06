package com.enrico.chemistry.molecule;

import com.enrico.chemistry.atoms.Atom;
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

    private void findCentralAtom() throws IllegalMoleculeException {
        for (Atom atom : atomList) {
            if (atom.getClassType() == Atom.AtomClassType.NotMetals) {
                centralAtom = atom;
                return;
            }
        }

        throw new IllegalMoleculeException(this);
    }

    private void findDoublets() {
        doubletsNumber = centralAtom.getDoublets();
    }

    private void setBindedAtoms() {
        for (Atom atom : atomList) {
            if (atom != centralAtom)
                bindedAtoms.add(atom);
        }
    }

    public void calculateShape() throws IllegalMoleculeException {
        if ((bindedAtoms.size() == 4 && doubletsNumber == 0) ||
            (bindedAtoms.size() == 3 && doubletsNumber == 0))
            moleculeShape = ShapeEnum.SquareShape;
        else if (bindedAtoms.size() == 3 && doubletsNumber == 1)
            moleculeShape = ShapeEnum.PyramidShape;
        else if (bindedAtoms.size() == 2 && doubletsNumber == 0)
            moleculeShape = ShapeEnum.LineShape;
        else
            throw new IllegalMoleculeException(this);
    }
}
