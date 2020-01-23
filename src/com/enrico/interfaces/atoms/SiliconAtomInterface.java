package com.enrico.interfaces.atoms;

import com.enrico.chemistry.atoms.GenericAtom;

public interface SiliconAtomInterface {
    String ATOM_SYMBOL = "Si";
    String ATOM_NAME = "Silicon";
    int ATOMIC_NUMBER = 14;
    int BINDING_ELECTRONS = 4;
    int IONIZATION_ENERGY = 786;
    int DOUBLETS = 2;
    double ATOMIC_MASS = 28.09;
    double ELECTRONEGATIVITY = 1.9;
    GenericAtom.AtomClassType CLASS_TYPE = GenericAtom.AtomClassType.SemiMetals;
}
