package com.enrico.drawing.graphicalAtoms.alkalineearthmetals;

import com.enrico.drawing.graphicalAtoms.GenericGraphicalAtom;
import com.enrico.interfaces.atoms.alkalineearthmetals.MagnesiumAtomInterface;

public final class GraphicalMagnesiumAtom extends GenericGraphicalAtom implements MagnesiumAtomInterface {
    public static final String IMAGE_PATH_STRING =
            "atom_icons/alkaline_earth_metals/atom_icon_magnesium.png";

    public static final int STD_BINDINGS = 2;

    public GraphicalMagnesiumAtom(int startX, int startY, int endX, int endY, String atomId) {
        super (ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY, BINDING_ELECTRONS, DOUBLETS,
                IONIZATION_NUMBER, CLASS_TYPE, startX, startY, endX, endY, STD_BINDINGS, IMAGE_PATH_STRING, atomId);
    }
}
