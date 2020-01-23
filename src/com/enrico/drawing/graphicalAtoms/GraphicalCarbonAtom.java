package com.enrico.drawing.graphicalAtoms;

import com.enrico.interfaces.atoms.CarbonAtomInterface;

public final class GraphicalCarbonAtom extends GenericGraphicalAtom implements CarbonAtomInterface {
    public GraphicalCarbonAtom() {
        super (ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY, BINDING_ELECTRONS, DOUBLETS, IONIZATION_NUMBER, CLASS_TYPE);
    }
}
