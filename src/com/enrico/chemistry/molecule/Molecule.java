/*
 * Chem solver. A multi-platform chemistry and physics problem solver.
 *  Copyright (C) 2019  Giacalone Enrico
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.enrico.chemistry.molecule;

import com.enrico.chemistry.atoms.Atom;
import com.enrico.chemistry.atoms.HydrogenAtom;
import com.enrico.chemistry.molecule.exceptions.IllegalMoleculeException;

import java.util.ArrayList;

public class Molecule {
    private Atom[] atomList;
    private ShapeEnum moleculeShape;

    private final String formula;

    private String operationString = "";

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

    public String getOperationString() {
        return operationString;
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

        operationString = operationString.concat("Found central atom: " +
                                                 centralAtom.getSymbol() + "(" +
                                                 centralAtom.getCompleteName() + ")\n");
    }

    private void findDoublets() {
        doubletsNumber = centralAtom.getDoublets();
        operationString = operationString.concat("Found free doublets of central atom: " +
                                                 centralAtom.getDoublets() + "\n");
    }

    private void setBindedAtoms() {

        operationString = operationString.concat("Binded atoms:\n");

        // Check if molecule is Hydrogen molecule.
        if (atomList.length == 2 && centralAtom.getClass() == HydrogenAtom.class &&
            atomList[1].getClass() == HydrogenAtom.class) {
            bindedAtoms.add(atomList[1]);

            operationString = operationString.concat("H (Hydrogen)\n");

            return;
        }

        for (Atom atom : atomList) {
            Atom.checkIfUsable(atom);

            if (atom.getClass().equals(HydrogenAtom.class)) {
                hydrogenAtoms.add((HydrogenAtom) atom);
                continue;
            }

            if (!atom.equals(centralAtom)) {
                bindedAtoms.add(atom);
                operationString = operationString.concat(atom.getSymbol() + " (" + atom.getCompleteName() + ")\n");
            }
        }

        // All the binded atoms are Hydrogen atoms.
        if (bindedAtoms.size() == 0) {
            bindedAtoms.addAll(hydrogenAtoms);
            operationString = operationString.concat("added " + hydrogenAtoms.size() + " Hydrogen atoms\n");
        }
    }

    public void calculateShape() throws IllegalMoleculeException {
        operationString = operationString.concat("Found shape of molecule: ");

        if ((bindedAtoms.size() == 4 && doubletsNumber == 0) ||
            (bindedAtoms.size() == 4 && doubletsNumber == 2)) {
            moleculeShape = ShapeEnum.SquareShape;
            operationString = operationString.concat("Square shape.\n");
        } else if ((bindedAtoms.size() == 2 && doubletsNumber == 2) ||
                 (bindedAtoms.size() == 2 && doubletsNumber == 5) ||
                 (bindedAtoms.size() == 3 && doubletsNumber == 1)) {
            moleculeShape = ShapeEnum.PyramidShape;
            operationString = operationString.concat("Pyramid shape.\n");
        } else if ((bindedAtoms.size() == 2 && doubletsNumber == 0) ||
                 (bindedAtoms.size() == 1 && doubletsNumber == 0) ||
                 (bindedAtoms.size() == 2 && doubletsNumber == 1) ||
                 (bindedAtoms.size() == 1 && doubletsNumber == 3) ||
                 (bindedAtoms.size() == 1 && doubletsNumber == 2)) {
            moleculeShape = ShapeEnum.LineShape;
            operationString = operationString.concat("Line shape.\n");
        } else if ((bindedAtoms.size() == 3 && doubletsNumber == 2) ||
                 (bindedAtoms.size() == 3 && doubletsNumber == 0) ||
                 (bindedAtoms.size() == 3 && doubletsNumber == 3)) {
            moleculeShape = ShapeEnum.TriangularShape;
            operationString = operationString.concat("Triangular shape.\n");
        } else if ((bindedAtoms.size() == 5 && doubletsNumber == 2)) {
            moleculeShape = ShapeEnum.FivePointedStar;
            operationString = operationString.concat("Five pointed star shape.\n");
        } else if ((bindedAtoms.size() == 6 && doubletsNumber == 2)) {
            moleculeShape = ShapeEnum.SixPointedStar;
            operationString = operationString.concat("Six pointed star shape.\n");
        } else {
            throw new IllegalMoleculeException(this);
        }
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
