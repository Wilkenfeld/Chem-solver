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

public interface AluminiumAtomInterface {
    String ATOM_SYMBOL = "Al";
    String ATOM_NAME = "Aluminium";
    int ATOMIC_NUMBER = 13;
    int BONDING_ELECTRONS = 3;
    int IONIZATION_NUMBER = 578;
    int DOUBLETS = 1;
    double ATOMIC_MASS = 26.98;
    double ELECTRONEGATIVITY = 1.61;
    GenericAtom.AtomClassType CLASS_TYPE = GenericAtom.AtomClassType.PBlockMetals;
}
