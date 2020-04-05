
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

package com.enrico.drawing.graphicalAtoms.transitionalmetals;

import com.enrico.chemistry.atoms.GenericAtom;
import com.enrico.drawing.graphicalAtoms.GenericGraphicalAtom;
import com.enrico.interfaces.atoms.transitionalmetals.ScandiumAtomInterface;

public final class GraphicalScandiumAtom extends GenericGraphicalAtom implements ScandiumAtomInterface {
    public static final String IMAGE_PATH_STRING =
            "atom_icons/transition_metals/atom_icon_scandium.png";

    public static final int STD_BONDS = 2;

    public GraphicalScandiumAtom(int startX, int startY, int endX, int endY, String atomId) {
        super (ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY, BONDING_ELECTRONS, DOUBLETS,
                IONIZATION_NUMBER, CLASS_TYPE, startX, startY, endX, endY, STD_BONDS, IMAGE_PATH_STRING, atomId);
    }
}
