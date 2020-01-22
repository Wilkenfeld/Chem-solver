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

import com.enrico.chemistry.atoms.GenericAtom;
import com.enrico.chemistry.atoms.scientific.GenericScientificAtom;
import com.enrico.chemistry.atoms.scientific.HydrogenScientificAtom;
import com.enrico.chemistry.atoms.scientific.OxygenScientificAtom;
import com.enrico.chemistry.molecule.exceptions.IllegalMoleculeException;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * This class represents generic molecule.
 */
public class Molecule {
    private GenericScientificAtom[] GenericScientificAtomList;
    private ShapeEnum moleculeShape;
    private CompoundType compoundType;

    private final String formula;

    private String operationString = "";

    // AXE Parameters.
    private GenericScientificAtom centralGenericScientificAtom;
    private ArrayList<GenericScientificAtom> bindedGenericScientificAtoms; // Atoms binded to the central atom.
    private int doubletsNumber; // Doublets of central atom.

    private final int numberOfElements;

    private ArrayList<HydrogenScientificAtom> hydrogenAtoms;

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

    public Molecule(GenericScientificAtom[] GenericScientificAtomList, String formula) throws IllegalMoleculeException {
        this.GenericScientificAtomList = GenericScientificAtomList;
        this.formula = formula;

        doubletsNumber = 0;

        bindedGenericScientificAtoms = new ArrayList<>();
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

    public GenericScientificAtom getCentralGenericScientificAtom() {
        return centralGenericScientificAtom;
    }

    public ArrayList<GenericScientificAtom> getBindedGenericScientificAtoms() {
        return bindedGenericScientificAtoms;
    }

    public String getFormula() {
        return formula;
    }

    public ArrayList<HydrogenScientificAtom> getHydrogenAtoms() {
        return hydrogenAtoms;
    }

    public CompoundType getCompoundType() {
        return compoundType;
    }

    public int getElementsNum() {
        return numberOfElements;
    }

    private void findCentralAtom() throws IllegalArgumentException {
        GenericScientificAtom central = null;

        double currentElectronegativity;
        double minElectronegativity = 0.0;

        for (GenericScientificAtom GenericScientificAtom : GenericScientificAtomList) {
            com.enrico.chemistry.atoms.scientific.GenericScientificAtom.checkIfUsable(GenericScientificAtom);

            if (minElectronegativity == 0.0 && GenericScientificAtom.getClass() != HydrogenScientificAtom.class) {
                minElectronegativity = GenericScientificAtom.getElectronegativity();
                central = GenericScientificAtom;
                continue;
            }

            currentElectronegativity = GenericScientificAtom.getElectronegativity();

            if (currentElectronegativity < minElectronegativity) {
                if (GenericScientificAtom.getClass() == HydrogenScientificAtom.class)
                    continue;

                central = GenericScientificAtom;
                minElectronegativity = currentElectronegativity;
            }
        }

        // It is possible for Hydrogen to be the central atom only for the molecule H2.
        if (central == null) {
            if (GenericScientificAtomList[0].getClass() == HydrogenScientificAtom.class && GenericScientificAtomList[1].getClass() == HydrogenScientificAtom.class) {
                central = GenericScientificAtomList[0];
            } else { // No valid molecule.
                throw new IllegalMoleculeException(this);
            }
        }

        centralGenericScientificAtom = central;

        operationString = operationString.concat("Found central atom: " +
                                                 centralGenericScientificAtom.getSymbol() + "(" +
                                                 centralGenericScientificAtom.getCompleteName() + ")\n");
    }

    private void findDoublets() {
        doubletsNumber = centralGenericScientificAtom.getDoublets();
        operationString = operationString.concat("Found free doublets of central atom: " +
                                                 centralGenericScientificAtom.getDoublets() + "\n");
    }

    private void setBindedAtoms() {

        operationString = operationString.concat("Binded atoms:\n");

        // Check if molecule is Hydrogen molecule.
        if (GenericScientificAtomList.length == 2 && centralGenericScientificAtom.getClass() == HydrogenScientificAtom.class &&
            GenericScientificAtomList[1].getClass() == HydrogenScientificAtom.class) {
            bindedGenericScientificAtoms.add(GenericScientificAtomList[1]);

            operationString = operationString.concat("H (Hydrogen)\n");

            return;
        }

        for (GenericScientificAtom GenericScientificAtom : GenericScientificAtomList) {
            com.enrico.chemistry.atoms.scientific.GenericScientificAtom.checkIfUsable(GenericScientificAtom);

            if (GenericScientificAtom.getClass().equals(HydrogenScientificAtom.class)) {
                hydrogenAtoms.add((HydrogenScientificAtom) GenericScientificAtom);
                continue;
            }

            if (!GenericScientificAtom.equals(centralGenericScientificAtom)) {
                bindedGenericScientificAtoms.add(GenericScientificAtom);
                operationString = operationString.concat(GenericScientificAtom.getSymbol() + " (" + GenericScientificAtom.getCompleteName() + ")\n");
            }
        }

        // All the binded atoms are Hydrogen atoms.
        if (bindedGenericScientificAtoms.size() == 0) {
            bindedGenericScientificAtoms.addAll(hydrogenAtoms);
            operationString = operationString.concat("added " + hydrogenAtoms.size() + " Hydrogen atoms\n");
        }
    }

    private boolean containsMetal() {
        GenericScientificAtom.AtomClassType atomType;

        for (GenericScientificAtom GenericScientificAtom : GenericScientificAtomList) {
            atomType = GenericScientificAtom.getClassType();
            if (atomType == GenericAtom.AtomClassType.AlkalineEarthMetals ||
                atomType == GenericAtom.AtomClassType.AlkalineMetals ||
                atomType == GenericAtom.AtomClassType.PBlockMetals ||
                atomType == GenericAtom.AtomClassType.TransitionalMetals ||
                atomType == GenericAtom.AtomClassType.SemiMetals)
                return true;
        }

        return false;
    }

    private boolean containsNonMetal() {
        GenericScientificAtom.AtomClassType atomType;

        for (GenericScientificAtom GenericScientificAtom : GenericScientificAtomList) {
            atomType = GenericScientificAtom.getClassType();
            if (atomType == GenericAtom.AtomClassType.NotMetals ||
                atomType == GenericAtom.AtomClassType.NobleGasses)
                return true;
        }

        return false;
    }

    public void calculateShape() throws IllegalMoleculeException {
        operationString = operationString.concat("Found shape of molecule: ");

        if ((bindedGenericScientificAtoms.size() == 4 && doubletsNumber == 0) ||
            (bindedGenericScientificAtoms.size() == 4 && doubletsNumber == 2)) {
            moleculeShape = ShapeEnum.SquareShape;
            operationString = operationString.concat("Square shape.\n");
        } else if ((bindedGenericScientificAtoms.size() == 2 && doubletsNumber == 2) ||
                 (bindedGenericScientificAtoms.size() == 2 && doubletsNumber == 5) ||
                 (bindedGenericScientificAtoms.size() == 3 && doubletsNumber == 1)) {
            moleculeShape = ShapeEnum.PyramidShape;
            operationString = operationString.concat("Pyramid shape.\n");
        } else if ((bindedGenericScientificAtoms.size() == 2 && doubletsNumber == 0) ||
                 (bindedGenericScientificAtoms.size() == 1 && doubletsNumber == 0) ||
                 (bindedGenericScientificAtoms.size() == 2 && doubletsNumber == 1) ||
                 (bindedGenericScientificAtoms.size() == 1 && doubletsNumber == 3) ||
                 (bindedGenericScientificAtoms.size() == 1 && doubletsNumber == 2)) {
            moleculeShape = ShapeEnum.LineShape;
            operationString = operationString.concat("Line shape.\n");
        } else if ((bindedGenericScientificAtoms.size() == 3 && doubletsNumber == 2) ||
                 (bindedGenericScientificAtoms.size() == 3 && doubletsNumber == 0) ||
                 (bindedGenericScientificAtoms.size() == 3 && doubletsNumber == 3)) {
            moleculeShape = ShapeEnum.TriangularShape;
            operationString = operationString.concat("Triangular shape.\n");
        } else if ((bindedGenericScientificAtoms.size() == 5 && doubletsNumber == 2)) {
            moleculeShape = ShapeEnum.FivePointedStar;
            operationString = operationString.concat("Five pointed star shape.\n");
        } else if ((bindedGenericScientificAtoms.size() == 6 && doubletsNumber == 2)) {
            moleculeShape = ShapeEnum.SixPointedStar;
            operationString = operationString.concat("Six pointed star shape.\n");
        } else {
            throw new IllegalMoleculeException(this);
        }
    }

    public boolean isMoleculeSimple() {
        for (GenericScientificAtom GenericScientificAtom : GenericScientificAtomList) {
            if (GenericScientificAtom.getSymbol().equals(centralGenericScientificAtom.getSymbol()))
                continue;

            if (!GenericScientificAtom.getSymbol().equals(HydrogenScientificAtom.ATOM_SYMBOL))
                return false;
        }
        return true;
    }

    public boolean containsHydrogen() {
        for (GenericScientificAtom GenericScientificAtom : GenericScientificAtomList)
            if (GenericScientificAtom.getSymbol().equals(HydrogenScientificAtom.ATOM_SYMBOL))
                return true;
        return false;
    }

    private boolean containsOxygen() {
        for (GenericScientificAtom GenericScientificAtom : GenericScientificAtomList)
            if (GenericScientificAtom.getSymbol().equals(OxygenScientificAtom.ATOM_SYMBOL))
                return true;

        return false;
    }

    private boolean containsWater() {
        return containsOxygen() && containsHydrogen();
    }

    private boolean isPeroxide() {
        int oxygenNumber = 0;
        for (GenericScientificAtom GenericScientificAtom : GenericScientificAtomList) {
            if (GenericScientificAtom.getSymbol().equals(OxygenScientificAtom.ATOM_SYMBOL))
                oxygenNumber++;
        }

        return oxygenNumber == 2;
    }

    private int getNumberOfElements() {
        int elementsNum = 1;
        String[] atomSymbols = new String[118];
        atomSymbols[0] = centralGenericScientificAtom.getSymbol();

        if (containsHydrogen() && !isMoleculeSimple()) {
            atomSymbols[1] = HydrogenScientificAtom.ATOM_SYMBOL;
            elementsNum++;
        }

        for (GenericScientificAtom bindedGenericScientificAtom : bindedGenericScientificAtoms) {
            if (!Arrays.asList(atomSymbols).contains(bindedGenericScientificAtom.getSymbol())) {
                atomSymbols[elementsNum] = bindedGenericScientificAtom.getSymbol();
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
