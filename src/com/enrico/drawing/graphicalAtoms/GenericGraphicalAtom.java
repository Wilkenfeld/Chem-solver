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

    protected int selectableStartX;
    protected int selectableStartY;
    protected int selectableEndX;
    protected int selectableEndY;

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

        setSelectableCoordinates();

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

    public int getSelectableEndX() {
        return selectableEndX;
    }

    public int getSelectableStartX() {
        return selectableStartX;
    }

    public int getSelectableEndY() {
        return selectableEndY;
    }

    public int getSelectableStartY() {
        return selectableStartY;
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

        setSelectableCoordinates();

        moveBindings();
    }

    /*
     * Since when we move an atom the binding won't be moving correctly between the two atoms, this functions reloads
     * the atom by moving it at the same position it was before, so that the binding will adjust itself.
     */
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

    private void setSelectableCoordinates() {
        selectableStartX = this.startX - 20;
        selectableStartY = this.startY - 20;
        selectableEndX = this.endX + 20;
        selectableEndY = this.endY + 20;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getAtomId() {
        return atomId;
    }

    public boolean hasAtomBinding(String bindingID) {
        ArrayList<GraphicalBinding> bindings = bindingList.getBindings();

        for (GraphicalBinding binding : bindings) {
            if (binding.getID().equals(bindingID)) {
                return true;
            }
        }

        return false;
    }

    public void removeBinding(String bindingID) {
        ArrayList<GraphicalBinding> bindings = bindingList.getBindings();
        int index = 0;

        for (GraphicalBinding binding : bindings) {
            if (binding.getID().equals(bindingID)) {
                bindings.remove(index);
                bindingsRemaining++;
                return;
            }

            index++;
        }
    }
}
