package com.enrico.interfaces.atoms;

import com.enrico.chemistry.atoms.GenericAtom;

public interface ChlorineAtomInterface {
    String ATOM_SYMBOL = "Cl";
    String ATOM_NAME = "Chlorine";
    int ATOMIC_NUMBER = 17;
    int BINDING_ELECTRONS = 7;
    int IONIZATION_ENERGY = 1251;
    int DOUBLETS = 3;
    double ATOMIC_MASS = 35.45;
    double ELECTRONEGATIVITY = 3.16;
    GenericAtom.AtomClassType CLASS_TYPE = GenericAtom.AtomClassType.Halogens;
}
