package com.enrico.interfaces.atoms;

import com.enrico.chemistry.atoms.GenericAtom;

public interface NitrogenAtomInterface {
    String ATOM_SYMBOL = "N";
    String ATOM_NAME = "Nitrogen";
    int ATOMIC_NUMBER = 7;
    int BINDING_ELECTRONS = 5;
    int IONIZATION_ENERGY = 1402;
    int DOUBLETS = 1;
    double ATOMIC_MASS = 14.01;
    double ELECTRONEGATIVITY = 3.04;
    GenericAtom.AtomClassType CLASS_TYPE = GenericAtom.AtomClassType.NotMetals;
}
