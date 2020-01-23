package com.enrico.interfaces.atoms;

import com.enrico.chemistry.atoms.GenericAtom;

public interface HydrogenAtomInterface {
    String ATOM_SYMBOL = "H";
    String ATOM_NAME = "Hydrogen";
    int ATOMIC_NUMBER = 1;
    int BINDING_ELECTRONS = 1;
    int IONIZATION_ENERGY = 1312;
    int DOUBLETS = 0;
    double ATOMIC_MASS = 1.008;
    double ELECTRONEGATIVITY = 2.2;
    GenericAtom.AtomClassType CLASS_TYPE = GenericAtom.AtomClassType.NotMetals;
}
