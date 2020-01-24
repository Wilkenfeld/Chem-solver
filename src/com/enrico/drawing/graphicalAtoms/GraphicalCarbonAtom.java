package com.enrico.drawing.graphicalAtoms;

import com.enrico.interfaces.atoms.CarbonAtomInterface;

public final class GraphicalCarbonAtom extends GenericGraphicalAtom implements CarbonAtomInterface {
    public static final String IMAGE_PATH_STRING =
            "atom_icons/nonmetals/atom_icon_carbon.png";

    public GraphicalCarbonAtom(int x, int y) {
        super (ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY, BINDING_ELECTRONS, DOUBLETS,
               IONIZATION_NUMBER, CLASS_TYPE, x, y, IMAGE_PATH_STRING);
    }
}
