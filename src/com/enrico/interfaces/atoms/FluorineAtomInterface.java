package com.enrico.interfaces.atoms;

import com.enrico.chemistry.atoms.GenericAtom;

public interface FluorineAtomInterface {
    String ATOM_SYMBOL = "F";
    String ATOM_NAME = "Fluorine";
    int ATOMIC_NUMBER = 9;
    int BINDING_ELECTRONS = 5;
    int IONIZATION_ENERGY = 1681;
    int DOUBLETS = 2;
    double ATOMIC_MASS = 19.0;
    double ELECTRONEGATIVITY = 3.98;
    GenericAtom.AtomClassType CLASS_TYPE = GenericAtom.AtomClassType.Halogens;
}
