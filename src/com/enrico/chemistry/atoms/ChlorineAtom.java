package com.enrico.chemistry.atoms;

public class ChlorineAtom extends Atom {
    public static final String ATOM_SYMBOL = "Cl";
    public static final String ATOM_NAME = "Chlorine";
    public static final int ATOMIC_NUMBER = 17;
    public static final int BINDING_ELECTRONS = 7;
    public static final int IONIZATION_ENERGY = 1251;
    public static final double ATOMIC_MASS = 35.45;
    public static final double ELECTRONEGATIVITY = 3.16;

    public ChlorineAtom() {
        super(ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY,
                BINDING_ELECTRONS, 3, IONIZATION_ENERGY, AtomClassType.Halogens);
    }
}
