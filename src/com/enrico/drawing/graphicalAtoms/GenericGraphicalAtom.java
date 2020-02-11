package com.enrico.drawing.graphicalAtoms;

import com.enrico.chemistry.atoms.GenericAtom;
import com.enrico.drawing.graphicalAtoms.binding.GraphicalBinding;
import com.enrico.drawing.graphicalAtoms.binding.GraphicalBindingList;

import java.util.ArrayList;

public abstract class GenericGraphicalAtom extends GenericAtom {
    protected int startX;
    protected int endX;
    protected int startY;
    protected int endY;

    private int bindingsRemaining; // How many binding the atom can still do.

    protected final String imagePath;
    
    private String atomId;

    private GraphicalBindingList bindingList = new GraphicalBindingList();

    public GenericGraphicalAtom(String symbol, String completeName, int atomicNumber, double atomicMass, double electronegativity,
                                int bindingElectronsNumber, int doublets, int ionizationEnergy, AtomClassType classType,
                                int startX, int startY, int endX, int endY, int bindingsRemaining, String imagePath,
                                String atomId) {
        super(symbol, completeName, atomicNumber, atomicMass, electronegativity, bindingElectronsNumber, doublets, ionizationEnergy, classType);

        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;

        this.imagePath = imagePath;
        this.atomId = atomId;

        this.bindingsRemaining = bindingsRemaining;
    }

    public int getStartX() {
        return startX;
    }

    // These functions will give the range where the atom can be selected by a mouse click.
    public int getEndX() {
        return endX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndY() {
        return endY;
    }

    public int getCenterX() {
        return startX + 23;
    }

    public int getCenterY() {
        return startY + 23;
    }

    public int getBindingsRemaining() {
        return bindingsRemaining;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public void setAtomId(String atomId) {
        this.atomId = atomId;
    }

    public void doBinding(GraphicalBinding binding, GraphicalBindingList.Edges edge) {
        if (bindingsRemaining > 0)
            bindingsRemaining--;

        bindingList.addBinding(binding, edge);
    }

    public void move(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;

        endX = startX + 23;
        endY = startY + 23;

        moveBindings();
    }

    public void reload() {
        move(getStartX(), getStartY());
    }

    private void moveBindings() {
        ArrayList<GraphicalBinding> bindings = bindingList.getBindings();
        ArrayList<GraphicalBindingList.Edges> edges = bindingList.getBindingsEdges();
        int bindingIndex = 0;

        for (GraphicalBinding binding : bindings) {
            if (edges.get(bindingIndex) == GraphicalBindingList.Edges.Start) {
                binding.setStartX(getCenterX());
                binding.setStartY(getCenterY());
            } else {
                binding.setEndX(getCenterX());
                binding.setEndY(getCenterY());
            }

            bindingIndex++;
        }
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getAtomId() {
        return atomId;
    }
}
