package com.enrico.chemistry.atoms;

public class CarbonAtom extends Atom {
    public static final String ATOM_SYMBOL = "C";
    public static final String ATOM_NAME = "Carbon";
    public static final int ATOMIC_NUMBER = 6;
    public static int BINDING_ELECTRONS = 6;
    public static final double ATOMIC_MASS = 12.01;
    public static final double ELECTRONEGATIVITY = 2.55;

    public CarbonAtom() {
        super(ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY,
              BINDING_ELECTRONS, 0, AtomClassType.NotMetals);
    }
}
