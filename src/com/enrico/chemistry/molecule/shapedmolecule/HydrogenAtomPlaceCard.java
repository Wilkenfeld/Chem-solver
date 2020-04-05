/*
 * Chem solver. A multi-platform chemistry and physics problem solver.
 *  Copyright (C) 2019 - 2020  Giacalone Enrico
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.enrico.chemistry.molecule.shapedmolecule;

/*
 * Since it's not rare that some hydrogen atoms are bonded to other atoms, the normal PlaceCard for the normal atom
 * is not enough to store the position and to do the arithmetic to draw the lines, so this PlaceCard is an extension
 * if the original.
 *
 * It will store the PlaceCard of the atom that is bonded to the Hydrogen to make the arithmetic to find the lines
 * spots easier.
 */
public final class HydrogenAtomPlaceCard extends ShapedMolecule.AtomPlaceCard {
    private ShapedMolecule.AtomPlaceCard bondedAtom;

    HydrogenAtomPlaceCard(ShapedMolecule.AtomPlaceCard hydrogenPlaceCard,
                          ShapedMolecule.AtomPlaceCard bondedAtom) {
        super(hydrogenPlaceCard.getAtomPlaceCardAtom(), hydrogenPlaceCard.getX(), hydrogenPlaceCard.getY(),
              hydrogenPlaceCard.getPosition());
        this.bondedAtom = bondedAtom;
    }

    public int getBindedAtomX() {
        return bondedAtom.getX();
    }

    public int getBindedAtomY() {
        return bondedAtom.getY();
    }

    public ShapedMolecule.AtomPlaceCard getBindedAtomPlaceCard() {
        return bondedAtom;
    }

    public String getBindedAtomSymbol() {
        return bondedAtom.getAtomSymbol();
    }

}
