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

package com.enrico.drawing.graphicalAtoms.binding;

import com.enrico.drawing.graphicalAtoms.binding.singlebinding.SingleGraphicalBinding;

import java.util.ArrayList;

public final class GenericGraphicalBindingList<BindingType> {
    private ArrayList<BindingType> bindings = new ArrayList<>();
    private ArrayList<Edges> edges = new ArrayList<>();

    public enum Edges {
        Start,
        End
    }

    public GenericGraphicalBindingList() {
    }

    public void addBinding(BindingType binding, Edges edge) {
        bindings.add(binding);
        edges.add(edge);
    }

    public BindingType getBindingFromIndex(int index) {
        return bindings.get(index);
    }

    public Edges getEdgeFromIndex(int index) {
        return edges.get(index);
    }

    public int getNumberOfBindings() {
        return bindings.size();
    }

    public ArrayList<BindingType> getBindings() {
        return bindings;
    }

    public ArrayList<Edges> getBindingsEdges() {
        return edges;
    }
}
