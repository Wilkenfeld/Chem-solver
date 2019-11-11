package com.enrico.chemistry.atoms;

public class SulfurAtom extends Atom {
    public static final String ATOM_SYMBOL = "S";
    public static final String ATOM_NAME = "Sulfur";
    public static final int ATOMIC_NUMBER = 16;
    public static final int BINDING_ELECTRONS = 6;
    public static final int IONIZATION_ENERGY = 1000;
    public static final double ATOMIC_MASS = 32.07;
    public static final double ELECTRONEGATIVITY = 2.58;

    public SulfurAtom() {
        super(ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY,
                BINDING_ELECTRONS, 2, IONIZATION_ENERGY, AtomClassType.NotMetals);
    }
}
