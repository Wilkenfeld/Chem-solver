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
