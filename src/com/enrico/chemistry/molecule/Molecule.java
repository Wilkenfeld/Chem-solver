package com.enrico.chemistry.molecule;

import com.enrico.chemistry.atoms.Atom;
import com.enrico.chemistry.atoms.ChlorineAtom;
import com.enrico.chemistry.atoms.HydrogenAtom;
import com.enrico.chemistry.molecule.exceptions.IllegalMoleculeException;

import java.util.ArrayList;

public class Molecule {
    private Atom[] atomList;
    private ShapeEnum moleculeShape;

    private final String formula;

    // AXE Parameters.
    private Atom centralAtom;
    private ArrayList<Atom> bindedAtoms; // Atoms binded to the central atom.
    private int doubletsNumber; // Doublets of central atom.

    private ArrayList<HydrogenAtom> hydrogenAtoms;

    public enum ShapeEnum {
        SquareShape,
        PyramidShape,
        LineShape, // Eg: O = C = O
        TriangularShape,
        FivePointedStar,
        SixPointedStar
    }

    public Molecule(Atom[] atomList, String formula) throws IllegalMoleculeException {
        this.atomList = atomList;
        this.formula = formula;

        doubletsNumber = 0;

        bindedAtoms = new ArrayList<>();
        hydrogenAtoms = new ArrayList<>();

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

    public String getFormula() {
        return formula;
    }

    public ArrayList<HydrogenAtom> getHydrogenAtoms() {
        return hydrogenAtoms;
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

        // Check if molecule is Hydrogen molecule.
        if (atomList.length == 2 && centralAtom.getClass() == HydrogenAtom.class &&
            atomList[1].getClass() == HydrogenAtom.class) {
            bindedAtoms.add(atomList[1]);
            return;
        }

        for (Atom atom : atomList) {
            Atom.checkIfUsable(atom);

            if (atom.getClass().equals(HydrogenAtom.class)) {
                hydrogenAtoms.add((HydrogenAtom) atom);
                continue;
            }

            if (!atom.equals(centralAtom))
                bindedAtoms.add(atom);
        }

        // All the binded atoms are Hydrogen atoms.
        if (bindedAtoms.size() == 0) {
            bindedAtoms.addAll(hydrogenAtoms);
        }
    }

    public void calculateShape() throws IllegalMoleculeException {
        if ((bindedAtoms.size() == 4 && doubletsNumber == 0) ||
            (bindedAtoms.size() == 4 && doubletsNumber == 2))
            moleculeShape = ShapeEnum.SquareShape;
        else if ((bindedAtoms.size() == 2 && doubletsNumber == 2) ||
                 (bindedAtoms.size() == 2 && doubletsNumber == 5) ||
                 (bindedAtoms.size() == 3 && doubletsNumber == 1))
            moleculeShape = ShapeEnum.PyramidShape;
        else if ((bindedAtoms.size() == 2 && doubletsNumber == 0) ||
                 (bindedAtoms.size() == 1 && doubletsNumber == 0) ||
                 (bindedAtoms.size() == 2 && doubletsNumber == 1) ||
                 (bindedAtoms.size() == 1 && doubletsNumber == 3) ||
                 (bindedAtoms.size() == 1 && doubletsNumber == 2))
            moleculeShape = ShapeEnum.LineShape;
        else if ((bindedAtoms.size() == 3 && doubletsNumber == 2) ||
                 (bindedAtoms.size() == 3 && doubletsNumber == 0) ||
                 (bindedAtoms.size() == 3 && doubletsNumber == 3))
            moleculeShape = ShapeEnum.TriangularShape;
        else if ((bindedAtoms.size() == 5 && doubletsNumber == 2))
            moleculeShape = ShapeEnum.FivePointedStar;
        else if ((bindedAtoms.size() == 6 && doubletsNumber == 2))
            moleculeShape = ShapeEnum.SixPointedStar;
        else
            throw new IllegalMoleculeException(this);
    }

    public boolean isMoleculeSimple() {
        for (Atom atom : atomList) {
            if (atom.getSymbol().equals(centralAtom.getSymbol()))
                continue;

            if (!atom.getSymbol().equals(HydrogenAtom.ATOM_SYMBOL))
                return false;
        }
        return true;
    }

    public boolean containsHydrogen() {
        for (Atom atom : atomList)
            if (atom.getSymbol().equals(HydrogenAtom.ATOM_SYMBOL))
                return true;
        return false;
    }
}
