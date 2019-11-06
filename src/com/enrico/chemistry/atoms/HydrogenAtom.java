package com.enrico.chemistry.atoms;

public class HydrogenAtom extends Atom {

    public static final String ATOM_SYMBOL = "H";
    public static final String ATOM_NAME = "Hydrogen";
    public static final int ATOMIC_NUMBER = 1;
    public static final int BINDING_ELECTRONS = 1;
    public static final double ATOMIC_MASS = 1.008;
    public static final double ELECTRONEGATIVITY = 2.2;

    // NB: even if Hydrogen is not a metal, since it's located in that column it will be classified as one here.
    public HydrogenAtom() {
        super(ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY,
              BINDING_ELECTRONS, 0, AtomClassType.AlkalineMetals);
    }
}
