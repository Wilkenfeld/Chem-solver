package com.enrico.chemistry.atoms;

public class NitrogenAtom extends Atom {
    public static final String ATOM_SYMBOL = "N";
    public static final String ATOM_NAME = "Nitrogen";
    public static final int ATOMIC_NUMBER = 7;
    public static final int BINDING_ELECTRONS = 5;
    public static final int IONIZATION_ENERGY = 1402;
    public static final int DOUBLETS = 1;
    public static final double ATOMIC_MASS = 14.01;
    public static final double ELECTRONEGATIVITY = 3.04;

    public NitrogenAtom() {
        super(ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY,
                BINDING_ELECTRONS, DOUBLETS, IONIZATION_ENERGY, AtomClassType.NotMetals);
    }
}
