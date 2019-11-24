package com.enrico.chemistry.molecule.shapedmolecule;

/*
 * Since it's not rare that some hydrogen atoms are binded to other atoms, the normal PlaceCard for the normal atom
 * is not enough to store the position and to do the arithmetic to draw the lines, so this PlaceCard is an extension
 * if the original.
 *
 * It will store the PlaceCard of the atom that is binded to the Hydrogen to make the arithmetic to find the lines
 * spots easier.
 */
public final class HydrogenAtomPlaceCard extends ShapedMolecule.AtomPlaceCard {
    private ShapedMolecule.AtomPlaceCard bindedAtom;

    HydrogenAtomPlaceCard(ShapedMolecule.AtomPlaceCard hydrogenPlaceCard,
                          ShapedMolecule.AtomPlaceCard bindedAtom) {
        super(hydrogenPlaceCard.getAtomPlaceCardAtom(), hydrogenPlaceCard.getX(), hydrogenPlaceCard.getY(),
              hydrogenPlaceCard.getPosition());
        this.bindedAtom = bindedAtom;
    }

    public int getBindedAtomX() {
        return bindedAtom.getX();
    }

    public int getBindedAtomY() {
        return bindedAtom.getY();
    }

    public ShapedMolecule.AtomPlaceCard getBindedAtomPlaceCard() {
        return bindedAtom;
    }

    public String getBindedAtomSymbol() {
        return bindedAtom.getAtomSymbol();
    }

}
