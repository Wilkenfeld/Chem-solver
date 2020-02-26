/*
 * Chem solver. A multi-platform chemistry and physics problem solver.
 *  Copyright (C) 2019 - 2020  Giacalone Enrico
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

package com.enrico.chemistry.formulaparser;

import com.enrico.chemistry.atoms.scientific.*;
import com.enrico.chemistry.formulaparser.exceptions.IllegalFormulaException;

import java.util.ArrayList;

public class FormulaParser {
    private String formula;

    public FormulaParser(String formula) {
        this.formula = formula;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    /*
     * How formulas are written:
     * Carbon monoxide:
     * C,02
     */
    public GenericScientificAtom[] getAtoms() throws IllegalArgumentException {
        ArrayList<GenericScientificAtom> GenericScientificAtomList = new ArrayList<>();

        String[] splittedFormula = formula.split(",");
        for (String atomName : splittedFormula) {
            if (atomName.contains(" "))
                throw new IllegalFormulaException(formula);

            // Get number of atoms and its symbol.
            String[] splittedAtomName = atomName.split("");
            String atomSymbol;
            String atomNumber;

            StringBuilder atomSymbolBuilder = new StringBuilder();
            StringBuilder atomNumberBuilder = new StringBuilder();
            for (String stringChar : splittedAtomName) {
                if (Character.isLetter(stringChar.charAt(0)))
                    atomSymbolBuilder.append(stringChar);
                else
                    atomNumberBuilder.append(stringChar);
            }
            atomNumber = atomNumberBuilder.toString();
            atomSymbol = atomSymbolBuilder.toString();

            if (atomNumber.isEmpty())
                atomNumber = null;

            if (atomName.equals(","))
                continue;

            GenericScientificAtom currentGenericScientificAtom;

            switch (atomSymbol) {
                case HydrogenScientificAtom.ATOM_SYMBOL:
                    currentGenericScientificAtom = new HydrogenScientificAtom();
                break;

                case HeliumScientificAtom.ATOM_SYMBOL:
                    currentGenericScientificAtom = new HeliumScientificAtom();
                break;

                case CarbonScientificAtom.ATOM_SYMBOL:
                    currentGenericScientificAtom = new CarbonScientificAtom();
                break;

                case OxygenScientificAtom.ATOM_SYMBOL:
                    currentGenericScientificAtom = new OxygenScientificAtom();
                break;

                case SulfurScientificAtom.ATOM_SYMBOL:
                    currentGenericScientificAtom = new SulfurScientificAtom();
                break;

                case NitrogenScientificAtom.ATOM_SYMBOL:
                    currentGenericScientificAtom = new NitrogenScientificAtom();
                break;

                case PhosphorusScientificAtom.ATOM_SYMBOL:
                    currentGenericScientificAtom = new PhosphorusScientificAtom();
                break;

                case ChlorineScientificAtom.ATOM_SYMBOL:
                    currentGenericScientificAtom = new ChlorineScientificAtom();
                break;

                case FluorineScientificAtom.ATOM_SYMBOL:
                    currentGenericScientificAtom = new FluorineScientificAtom();
                break;

                case SiliconScientificAtom.ATOM_SYMBOL:
                    currentGenericScientificAtom = new SiliconScientificAtom();
                break;

                default:
                    throw new IllegalArgumentException("Invalid atom: " + atomSymbol);
            }

            // There always will be at least a single atom.
            int atomNumberInteger = 1;
            if (atomNumber != null)
                atomNumberInteger = Integer.parseInt(atomNumber);


            for (int i = 1; i <= atomNumberInteger; i++)
                GenericScientificAtomList.add(currentGenericScientificAtom);
        }

        GenericScientificAtom[] GenericScientificAtomArray = new GenericScientificAtom[GenericScientificAtomList.size()];
        GenericScientificAtomArray = GenericScientificAtomList.toArray(GenericScientificAtomArray);

        return GenericScientificAtomArray;
    }
}
