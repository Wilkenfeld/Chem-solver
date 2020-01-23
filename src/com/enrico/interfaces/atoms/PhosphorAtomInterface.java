package com.enrico.interfaces.atoms;

import com.enrico.chemistry.atoms.GenericAtom;

public interface PhosphorAtomInterface {
    String ATOM_SYMBOL = "P";
    String ATOM_NAME = "Phosphor";
    int ATOMIC_NUMBER = 15;
    int BINDING_ELECTRONS = 5;
    int IONIZATION_ENERGY = 1012;
    int DOUBLETS = 2;
    double ATOMIC_MASS = 30.97;
    double ELECTRONEGATIVITY = 2.19;
    GenericAtom.AtomClassType CLASS_TYPE = GenericAtom.AtomClassType.NotMetals;
}
