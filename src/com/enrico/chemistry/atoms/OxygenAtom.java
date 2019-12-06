package com.enrico.chemistry.atoms;

public class OxygenAtom extends Atom {

    public static final String ATOM_SYMBOL = "O";
    public static final String ATOM_NAME = "Oxygen";
    public static final int ATOMIC_NUMBER = 8;
    public static final int BINDING_ELECTRONS = 6;
    public static final int IONIZATION_ENERGY = 1314;
    public static final double ATOMIC_MASS = 16;
    public static final double ELECTRONEGATIVITY = 3.44;

    public OxygenAtom() {
        super(ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY,
              BINDING_ELECTRONS, 2, IONIZATION_ENERGY, AtomClassType.NotMetals);
    }
}
