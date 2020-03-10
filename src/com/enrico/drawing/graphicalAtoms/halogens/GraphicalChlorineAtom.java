package com.enrico.drawing.graphicalAtoms.halogens;

import com.enrico.drawing.graphicalAtoms.GenericGraphicalAtom;
import com.enrico.drawing.graphicalAtoms.binding.GenericGraphicalBindingList;
import com.enrico.interfaces.atoms.halogens.ChlorineAtomInterface;

public final class GraphicalChlorineAtom extends GenericGraphicalAtom implements ChlorineAtomInterface {
    public static final String IMAGE_PATH_STRING =
            "atom_icons/halogens/atom_icon_chlorine.png";

    public static final int STD_BINDINGS = 1;

    public GraphicalChlorineAtom(int startX, int startY, int endX, int endY, String atomId) {
        super (ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY, BINDING_ELECTRONS, DOUBLETS,
                IONIZATION_NUMBER, CLASS_TYPE, startX, startY, endX, endY, STD_BINDINGS, IMAGE_PATH_STRING, atomId);
    }
}
