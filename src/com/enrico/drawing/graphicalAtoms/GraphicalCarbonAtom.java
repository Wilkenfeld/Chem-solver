package com.enrico.drawing.graphicalAtoms;

import com.enrico.interfaces.GraphicalAtom;
import com.enrico.chemistry.atoms.CarbonAtom;

import java.net.URL;

public final class GraphicalCarbonAtom extends CarbonAtom implements GraphicalAtom {
    public URL getImagePath() {
        return getImageUrl("atom_icons/nonmetals/atom_icon_carbon.png");
    }
}
