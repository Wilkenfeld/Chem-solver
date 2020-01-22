/*
 * Chem solver. A multi-platform chemistry and physics problem solver.
 *  Copyright (C) 2019  Giacalone Enrico
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

package com.enrico.chemistry.atoms.scientific;

public class PhosphorScientificAtom extends GenericScientificAtom {
    public static final String ATOM_SYMBOL = "P";
    public static final String ATOM_NAME = "Phosphor";
    public static final int ATOMIC_NUMBER = 15;
    public static final int BINDING_ELECTRONS = 5;
    public static final int IONIZATION_ENERGY = 1012;
    public static final double ATOMIC_MASS = 30.97;
    public static final double ELECTRONEGATIVITY = 2.19;

    public PhosphorScientificAtom() {
        super(ATOM_SYMBOL, ATOM_NAME, ATOMIC_NUMBER, ATOMIC_MASS, ELECTRONEGATIVITY,
                BINDING_ELECTRONS, 2, IONIZATION_ENERGY, AtomClassType.NotMetals);
    }
}
