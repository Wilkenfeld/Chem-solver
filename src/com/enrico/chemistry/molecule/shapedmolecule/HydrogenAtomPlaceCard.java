package com.enrico.chemistry.molecule.shapedmolecule;

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
