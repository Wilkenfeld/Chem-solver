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
import com.enrico.chemistry.atoms.OxygenAtom;
import com.enrico.chemistry.molecule.exceptions.IllegalMoleculeException;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * This class represents generic molecule.
 */
public class Molecule {
    private Atom[] atomList;
    private ShapeEnum moleculeShape;
    private CompoundType compoundType;

    private final String formula;

    private String operationString = "";

    // AXE Parameters.
    private Atom centralAtom;
    private ArrayList<Atom> bindedAtoms; // Atoms binded to the central atom.
    private int doubletsNumber; // Doublets of central atom.

    private final int numberOfElements;

    private ArrayList<HydrogenAtom> hydrogenAtoms;

    public enum ShapeEnum {
        SquareShape,
        PyramidShape,
        LineShape, // Eg: O = C = O
        TriangularShape,
        FivePointedStar,
        SixPointedStar
    }

    public enum CompoundType {
        Hydride,
        BinaryAcid,
        Peroxide,
        BasicOxide,
        BinaryRooms,
        Anhydride,
        Oxoacid,
        Hydroxide,
        TernarySalt;

        @Override
        public String toString() {
            switch (this) {
                case Hydride:
                    return "Hydride";
                case Anhydride:
                    return "Anhydride";
                case Oxoacid:
                    return "Oxoacid";
                case Peroxide:
                    return "Peroxide";
                case Hydroxide:
                    return "Hydroxide";
                case BasicOxide:
                    return "Basic oxide";
                case BinaryAcid:
                    return "Binary acid";
                case BinaryRooms:
                    return "Binary rooms";
                case TernarySalt:
                    return "Ternary salt";
                default:
                    throw new IllegalArgumentException();
            }
        }
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

        numberOfElements = getNumberOfElements();
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

    public CompoundType getCompoundType() {
        return compoundType;
    }

    public int getElementsNum() {
        return numberOfElements;
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

    private boolean containsMetal() {
        Atom.AtomClassType atomType;

        for (Atom atom : atomList) {
            atomType = atom.getClassType();
            if (atomType == Atom.AtomClassType.AlkalineEarthMetals ||
                atomType == Atom.AtomClassType.AlkalineMetals ||
                atomType == Atom.AtomClassType.PBlockMetals ||
                atomType == Atom.AtomClassType.TransitionalMetals ||
                atomType == Atom.AtomClassType.SemiMetals)
                return true;
        }

        return false;
    }

    private boolean containsNonMetal() {
        Atom.AtomClassType atomType;

        for (Atom atom : atomList) {
            atomType = atom.getClassType();
            if (atomType == Atom.AtomClassType.NotMetals ||
                atomType == Atom.AtomClassType.NobleGasses)
                return true;
        }

        return false;
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

    private boolean containsOxygen() {
        for (Atom atom : atomList)
            if (atom.getSymbol().equals(OxygenAtom.ATOM_SYMBOL))
                return true;

        return false;
    }

    private boolean containsWater() {
        return containsOxygen() && containsHydrogen();
    }

    private boolean isPeroxide() {
        int oxygenNumber = 0;
        for (Atom atom : atomList) {
            if (atom.getSymbol().equals(OxygenAtom.ATOM_SYMBOL))
                oxygenNumber++;
        }

        return oxygenNumber == 2;
    }

    private int getNumberOfElements() {
        int elementsNum = 1;
        String[] atomSymbols = new String[118];
        atomSymbols[0] = centralAtom.getSymbol();

        if (containsHydrogen() && !isMoleculeSimple()) {
            atomSymbols[1] = HydrogenAtom.ATOM_SYMBOL;
            elementsNum++;
        }

        for (Atom bindedAtom : bindedAtoms) {
            if (!Arrays.asList(atomSymbols).contains(bindedAtom.getSymbol())) {
                atomSymbols[elementsNum] = bindedAtom.getSymbol();
                elementsNum++;
            }
        }

        return elementsNum;
    }

    public void findCompoundType() throws IllegalMoleculeException {
        if (numberOfElements == 2) {
            if (containsMetal() && containsHydrogen())
                compoundType = CompoundType.Hydride;
            else if (isPeroxide())
                compoundType = CompoundType.Peroxide;
            else if (containsNonMetal() && containsHydrogen())
                compoundType = CompoundType.BinaryAcid;
            else if (containsMetal() && containsOxygen())
                compoundType = CompoundType.BasicOxide;
            else if (containsNonMetal() && containsOxygen())
                compoundType = CompoundType.Anhydride;
            else if (containsMetal() && containsNonMetal())
                compoundType = CompoundType.BinaryRooms;
            else
                throw new IllegalMoleculeException(this);

        } else if (numberOfElements == 3 || numberOfElements == 4) {
            if (containsWater() && containsMetal())
                compoundType = CompoundType.Hydroxide;
            else if (containsWater() && containsNonMetal())
                compoundType = CompoundType.Oxoacid;
            else if (containsNonMetal() && containsMetal() && containsOxygen())
                compoundType = CompoundType.TernarySalt;
            else
                throw new IllegalMoleculeException(this);
        } else {
            throw new IllegalMoleculeException(this);
        }
    }
}
