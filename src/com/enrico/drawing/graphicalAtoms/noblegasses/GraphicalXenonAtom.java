package com.enrico.drawing.graphicalAtoms.noblegasses;

import com.enrico.drawing.graphicalAtoms.GenericGraphicalAtom;
import com.enrico.interfaces.atoms.noblegasses.XenonAtomInterface;

public final class GraphicalXenonAtom extends GenericGraphicalAtom implements XenonAtomInterface {
    public static final String IMAGE_PATH_STRING =
            "atom_icons/noble_gasses/atom_icon_xenon.png";

    public static final int STD_BONDS = 8;

    public GraphicalXenonAtom(int startX, int startY, int endX, int endY, String atomId) {
        super (ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY, BONDING_ELECTRONS, DOUBLETS,
                IONIZATION_NUMBER, CLASS_TYPE, startX, startY, endX, endY, STD_BONDS, IMAGE_PATH_STRING, atomId);
    }
}
