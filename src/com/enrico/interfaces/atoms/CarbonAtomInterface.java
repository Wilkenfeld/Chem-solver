package com.enrico.interfaces.atoms;

import com.enrico.chemistry.atoms.GenericAtom;

public interface CarbonAtomInterface {
    String ATOM_SYMBOL = "C";
    String ATOM_NAME = "Carbon";
    int ATOMIC_NUMBER = 6;
    int BINDING_ELECTRONS = 6;
    int IONIZATION_NUMBER = 1086;
    int DOUBLETS = 2;
    double ATOMIC_MASS = 12.01;
    double ELECTRONEGATIVITY = 2.55;
    GenericAtom.AtomClassType CLASS_TYPE = GenericAtom.AtomClassType.NotMetals;
}
