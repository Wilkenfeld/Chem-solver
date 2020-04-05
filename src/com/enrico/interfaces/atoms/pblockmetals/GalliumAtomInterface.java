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

package com.enrico.interfaces.atoms.pblockmetals;

import com.enrico.chemistry.atoms.GenericAtom;

public interface GalliumAtomInterface {
    String ATOM_SYMBOL = "Ga";
    String ATOM_NAME = "Gallium";
    int ATOMIC_NUMBER = 31;
    int BONDING_ELECTRONS = 3;
    int IONIZATION_NUMBER = 579;
    int DOUBLETS = 1;
    double ATOMIC_MASS = 69.2;
    double ELECTRONEGATIVITY = 1.81;
    GenericAtom.AtomClassType CLASS_TYPE = GenericAtom.AtomClassType.PBlockMetals;
}
