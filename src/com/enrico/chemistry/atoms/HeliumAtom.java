package com.enrico.chemistry.atoms;

import com.enrico.annotations.chemistry.UnusableAtom;

@UnusableAtom
public class HeliumAtom extends Atom {

    public static final String ATOM_SYMBOL = "He";
    public static final String ATOM_NAME = "Helium";
    public static final int ATOMIC_NUMBER = 2;
    public static final double ATOMIC_MASS = 4.003;
    public static final double ELECTRONEGATIVITY = 0;

    public HeliumAtom() {
        super(ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY,
              0, 0, AtomClassType.NobleGasses);
    }
}
