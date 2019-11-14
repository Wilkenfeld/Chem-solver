package com.enrico.chemistry.atoms;

public class SiliconAtom extends Atom {
    public static final String ATOM_SYMBOL = "Si";
    public static final String ATOM_NAME = "Silicon";
    public static final int ATOMIC_NUMBER = 14;
    public static final int BINDING_ELECTRONS = 4;
    public static final int IONIZATION_ENERGY = 786;
    public static final double ATOMIC_MASS = 28.09;
    public static final double ELECTRONEGATIVITY = 1.9;

    public SiliconAtom() {
        super(ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY,
                BINDING_ELECTRONS, 2, IONIZATION_ENERGY, AtomClassType.SemiMetals);
    }
}
