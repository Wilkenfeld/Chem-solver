package com.enrico.drawing.graphicalAtoms;

import com.enrico.chemistry.atoms.GenericAtom;

public abstract class GenericGraphicalAtom extends GenericAtom {
    public GenericGraphicalAtom(String symbol, String completeName, int atomicNumber, double atomicMass, double electronegativity,
                                int bindingElectronsNumber, int doublets, int ionizationEnergy, AtomClassType classType) { // PLACEHOLDER.
        super(symbol, completeName, atomicNumber, atomicMass, electronegativity, bindingElectronsNumber, doublets, ionizationEnergy, classType);
    }
}
