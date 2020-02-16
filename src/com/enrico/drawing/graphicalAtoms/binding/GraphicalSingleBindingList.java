package com.enrico.drawing.graphicalAtoms.binding;

import java.util.ArrayList;

public final class GraphicalSingleBindingList {
    private ArrayList<SingleGraphicalBinding> bindings = new ArrayList<>();
    private ArrayList<Edges> bindingsEdges = new ArrayList<>();

    public GraphicalSingleBindingList() {
    }

    public enum Edges {
        Start,
        End
    }

    public void addBinding(SingleGraphicalBinding binding, Edges edge) {
        bindings.add(binding);
        bindingsEdges.add(edge);
    }

    public SingleGraphicalBinding getBindingFromIndex(int index) {
        return bindings.get(index);
    }

    public Edges getEdgeFromIndex(int index) {
        return bindingsEdges.get(index);
    }

    public int getNumberOfBindings() {
        return bindings.size();
    }

    public ArrayList<SingleGraphicalBinding> getBindings() {
        return bindings;
    }

    public ArrayList<Edges> getBindingsEdges() {
        return bindingsEdges;
    }
}
