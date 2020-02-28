package com.enrico.drawing.graphicalAtoms.semimetals;

import com.enrico.drawing.graphicalAtoms.GenericGraphicalAtom;
import com.enrico.interfaces.atoms.semimetals.PoloniumAtomInterface;

public final class GraphicalPoloniumAtom extends GenericGraphicalAtom implements PoloniumAtomInterface {
    public static final String IMAGE_PATH_STRING =
            "atom_icons/semimetals/atom_icon_polonium.png";

    public static final int STD_BINDINGS = 6;

    public GraphicalPoloniumAtom(int startX, int startY, int endX, int endY, String atomId) {
        super (ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY, BINDING_ELECTRONS, DOUBLETS,
                IONIZATION_NUMBER, CLASS_TYPE, startX, startY, endX, endY, STD_BINDINGS, IMAGE_PATH_STRING, atomId);
    }
}
