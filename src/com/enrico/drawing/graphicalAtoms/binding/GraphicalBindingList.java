package com.enrico.drawing.graphicalAtoms.binding;

import java.util.ArrayList;

public final class GraphicalBindingList {
    private ArrayList<GraphicalBinding> bindings = new ArrayList<>();
    private ArrayList<Edges> bindingsEdges = new ArrayList<>();

    public GraphicalBindingList() {
    }

    public enum Edges {
        Start,
        End
    }

    public void addBinding(GraphicalBinding binding, Edges edge) {
        bindings.add(binding);
        bindingsEdges.add(edge);
    }

    public GraphicalBinding getBindingFromIndex(int index) {
        return bindings.get(index);
    }

    public Edges getEdgeFromIndex(int index) {
        return bindingsEdges.get(index);
    }
}
