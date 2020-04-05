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

package com.enrico.drawing.graphicalAtoms.bond;

import java.awt.*;

public abstract class GenericGraphicalBond {
    public static final Color DEFAULT_COLOR = Color.black;
    public static final BasicStroke DEFAULT_STROKE = new BasicStroke(5);

    private final String ID;

    private static int idCount = 0;

    private int atomsBinded = 2;

    public GenericGraphicalBond(String prefix) {
        ID = prefix + idCount;
        idCount++;
    }

    public final String getID() {
        return ID;
    }

    public final int getNumberOfAtomsBinded() {
        return atomsBinded;
    }

    public final void markDeletion() {
        atomsBinded = 0;
    }
}
