package com.enrico.drawing.graphicalAtoms.semimetals;

import com.enrico.drawing.graphicalAtoms.GenericGraphicalAtom;
import com.enrico.interfaces.atoms.semimetals.AntimonyAtomInterface;

public final class GraphicalAntimonyAtom extends GenericGraphicalAtom implements AntimonyAtomInterface {
    public static final String IMAGE_PATH_STRING =
            "atom_icons/semimetals/atom_icon_antimony.png";

    public static final int STD_BINDINGS = 5;

    public GraphicalAntimonyAtom(int startX, int startY, int endX, int endY, String atomId) {
        super (ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY, BINDING_ELECTRONS, DOUBLETS,
                IONIZATION_NUMBER, CLASS_TYPE, startX, startY, endX, endY, STD_BINDINGS, IMAGE_PATH_STRING, atomId);
    }
}
