package com.enrico.chemistry.atoms;

public class PhosphorAtom extends Atom {
    public static final String ATOM_SYMBOL = "P";
    public static final String ATOM_NAME = "Phosphor";
    public static final int ATOMIC_NUMBER = 15;
    public static final int BINDING_ELECTRONS = 5;
    public static final int IONIZATION_ENERGY = 1012;
    public static final double ATOMIC_MASS = 30.97;
    public static final double ELECTRONEGATIVITY = 2.19;

    public PhosphorAtom() {
        super(ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY,
                BINDING_ELECTRONS, 2, IONIZATION_ENERGY, AtomClassType.NotMetals);
    }
}
