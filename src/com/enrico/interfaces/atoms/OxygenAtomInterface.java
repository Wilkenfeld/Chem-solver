package com.enrico.interfaces.atoms;

import com.enrico.chemistry.atoms.GenericAtom;

public interface OxygenAtomInterface {
    String ATOM_SYMBOL = "O";
    String ATOM_NAME = "Oxygen";
    int ATOMIC_NUMBER = 8;
    int BINDING_ELECTRONS = 6;
    int IONIZATION_ENERGY = 1314;
    int DOUBLETS = 2;
    double ATOMIC_MASS = 16;
    double ELECTRONEGATIVITY = 3.44;
    GenericAtom.AtomClassType CLASS_TYPE = GenericAtom.AtomClassType.NotMetals;
}
