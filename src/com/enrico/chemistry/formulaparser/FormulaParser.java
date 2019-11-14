package com.enrico.chemistry.formulaparser;

import com.enrico.chemistry.atoms.*;
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
    public Atom[] getAtoms() throws IllegalArgumentException {
        ArrayList<Atom> atomList = new ArrayList<>();

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

            Atom currentAtom;

            switch (atomSymbol) {
                case HydrogenAtom.ATOM_SYMBOL:
                    currentAtom = new HydrogenAtom();
                break;

                case HeliumAtom.ATOM_SYMBOL:
                    currentAtom = new HeliumAtom();
                break;

                case CarbonAtom.ATOM_SYMBOL:
                    currentAtom = new CarbonAtom();
                break;

                case OxygenAtom.ATOM_SYMBOL:
                    currentAtom = new OxygenAtom();
                break;

                case SulfurAtom.ATOM_SYMBOL:
                    currentAtom = new SulfurAtom();
                break;

                case NitrogenAtom.ATOM_SYMBOL:
                    currentAtom = new NitrogenAtom();
                break;

                case PhosphorAtom.ATOM_SYMBOL:
                    currentAtom = new PhosphorAtom();
                break;

                case ChlorineAtom.ATOM_SYMBOL:
                    currentAtom = new ChlorineAtom();
                break;

                case FluorineAtom.ATOM_SYMBOL:
                    currentAtom = new FluorineAtom();
                break;

                case SiliconAtom.ATOM_SYMBOL:
                    currentAtom = new SiliconAtom();
                break;

                default:
                    throw new IllegalArgumentException("No atom " + atomSymbol + " exists.");
            }

            // There always will be at least a single atom.
            int atomNumberInteger = 1;
            if (atomNumber != null)
                atomNumberInteger = Integer.parseInt(atomNumber);


            for (int i = 1; i <= atomNumberInteger; i++)
                atomList.add(currentAtom);
        }

        Atom[] atomArray = new Atom[atomList.size()];
        atomArray = atomList.toArray(atomArray);

        return atomArray;
    }
}
