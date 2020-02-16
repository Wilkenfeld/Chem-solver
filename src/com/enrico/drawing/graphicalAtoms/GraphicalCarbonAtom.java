package com.enrico.drawing.graphicalAtoms;

import com.enrico.annotations.chemistry.DoubleBinding;
import com.enrico.annotations.chemistry.TripleBinding;
import com.enrico.interfaces.atoms.CarbonAtomInterface;

@DoubleBinding
@TripleBinding
public final class GraphicalCarbonAtom extends GenericGraphicalAtom implements CarbonAtomInterface {
    public static final String IMAGE_PATH_STRING =
            "atom_icons/nonmetals/atom_icon_carbon.png";

    public static final int CARBON_STD_BINDINGS = 4;

    public GraphicalCarbonAtom(int startX, int startY, int endX, int endY, String atomId) {
        super (ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY, BINDING_ELECTRONS, DOUBLETS,
               IONIZATION_NUMBER, CLASS_TYPE, startX, startY, endX, endY, CARBON_STD_BINDINGS, IMAGE_PATH_STRING, atomId);
    }
}
