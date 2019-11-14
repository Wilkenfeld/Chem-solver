package com.enrico.chemistry.atoms;

public class FluorineAtom extends Atom {
    public static final String ATOM_SYMBOL = "F";
    public static final String ATOM_NAME = "Fluorine";
    public static final int ATOMIC_NUMBER = 9;
    public static final int BINDING_ELECTRONS = 5;
    public static final int IONIZATION_ENERGY = 1681;
    public static final double ATOMIC_MASS = 19.0;
    public static final double ELECTRONEGATIVITY = 3.98;

    public FluorineAtom() {
        super(ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY,
                BINDING_ELECTRONS, 2, IONIZATION_ENERGY, AtomClassType.Halogens);
    }
}
