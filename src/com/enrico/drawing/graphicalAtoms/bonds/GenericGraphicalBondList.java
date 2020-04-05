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

import java.util.ArrayList;

public final class GenericGraphicalBondList<BondType> {
    private ArrayList<BondType> bonds = new ArrayList<>();
    private ArrayList<Edges> edges = new ArrayList<>();

    public enum Edges {
        Start,
        End
    }

    public GenericGraphicalBondList() {
    }

    public void addBond(BondType bond, Edges edge) {
        bonds.add(bond);
        edges.add(edge);
    }

    public BondType getBondFromIndex(int index) {
        return bonds.get(index);
    }

    public Edges getEdgeFromIndex(int index) {
        return edges.get(index);
    }

    public int getNumberOfBonds() {
        return bonds.size();
    }

    public ArrayList<BondType> getBonds() {
        return bonds;
    }

    public ArrayList<Edges> getBondsEdges() {
        return edges;
    }
}
