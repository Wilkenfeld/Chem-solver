package com.enrico.interfaces.atoms;

import com.enrico.chemistry.atoms.GenericAtom;

public interface HeliumAtomInterface {
    String ATOM_SYMBOL = "He";
    String ATOM_NAME = "Helium";
    int ATOMIC_NUMBER = 2;
    int BINDING_ELECTRONS = 0;
    int DOUBLETS = 0;
    int IONIZATION_ENERGY = 0;
    double ATOMIC_MASS = 4.003;
    double ELECTRONEGATIVITY = 0;
    GenericAtom.AtomClassType CLASS_TYPE = GenericAtom.AtomClassType.NobleGasses;
}
