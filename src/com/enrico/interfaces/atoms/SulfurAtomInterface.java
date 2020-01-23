package com.enrico.interfaces.atoms;

import com.enrico.chemistry.atoms.GenericAtom;

public interface SulfurAtomInterface {
    String ATOM_SYMBOL = "S";
    String ATOM_NAME = "Sulfur";
    int ATOMIC_NUMBER = 16;
    int BINDING_ELECTRONS = 6;
    int IONIZATION_ENERGY = 1000;
    int DOUBLETS = 2;
    double ATOMIC_MASS = 32.07;
    double ELECTRONEGATIVITY = 2.58;
    GenericAtom.AtomClassType CLASS_TYPE = GenericAtom.AtomClassType.NotMetals;
}
