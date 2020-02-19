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

package com.enrico.drawing.graphicalAtoms;

import com.enrico.chemistry.atoms.GenericAtom;
import com.enrico.drawing.graphicalAtoms.binding.GenericGraphicalBindingList;
import com.enrico.drawing.graphicalAtoms.binding.doublebinding.DoubleGraphicalBinding;
import com.enrico.drawing.graphicalAtoms.binding.singlebinding.SingleGraphicalBinding;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    private GenericGraphicalBindingList<SingleGraphicalBinding> singleBindingList = new GenericGraphicalBindingList<>();
    protected GenericGraphicalBindingList<DoubleGraphicalBinding> doubleBindingList = null;

    public GenericGraphicalAtom(String symbol, String completeName, int atomicNumber, double atomicMass, double electronegativity,
                                int bindingElectronsNumber, int doublets, int ionizationEnergy, AtomClassType classType,
                                int startX, int startY, int endX, int endY, int bindingsRemaining, String imagePath,
                                String atomId) {
        super(symbol, completeName, atomicNumber, atomicMass, electronegativity, bindingElectronsNumber, doublets,
              ionizationEnergy, classType);

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

    public void doSingleBinding(SingleGraphicalBinding binding, GenericGraphicalBindingList.Edges edge) {
        if (bindingsRemaining > 0)
            bindingsRemaining--;

        singleBindingList.addBinding(binding, edge);
    }
    
    public void doDoubleBinding(DoubleGraphicalBinding binding, GenericGraphicalBindingList.Edges edge) {
        if (bindingsRemaining > 0)
            bindingsRemaining -= 2;
        
        doubleBindingList.addBinding(binding, edge);
    }

    public void move(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;

        endX = startX + 23;
        endY = startY + 23;

        setSelectableCoordinates();

        moveSingleBindings();

        if (doubleBindingList != null)
            moveDoubleBindings();
    }

    /*
     * Since when we move an atom the binding won't be moving correctly between the two atoms, this functions reloads
     * the atom by moving it at the same position it was before, so that the binding will adjust itself.
     */
    public void reload() {
        move(getStartX(), getStartY());
    }

    private void moveSingleBindings() {
        ArrayList<SingleGraphicalBinding> bindings = singleBindingList.getBindings();
        ArrayList<GenericGraphicalBindingList.Edges> edges = singleBindingList.getBindingsEdges();
        int bindingIndex = 0;

        for (SingleGraphicalBinding binding : bindings) {
            if (edges.get(bindingIndex) == GenericGraphicalBindingList.Edges.Start) {
                binding.setStartX(getCenterX());
                binding.setStartY(getCenterY());
            } else {
                binding.setEndX(getCenterX());
                binding.setEndY(getCenterY());
            }

            bindingIndex++;
        }
    }

    private void moveDoubleBindings() {
        ArrayList<DoubleGraphicalBinding> bindings = doubleBindingList.getBindings();
        ArrayList<GenericGraphicalBindingList.Edges> edges = doubleBindingList.getBindingsEdges();
        int bindingIndex = 0;

        for (DoubleGraphicalBinding binding : bindings) {
            if (edges.get(bindingIndex) == GenericGraphicalBindingList.Edges.Start) {
                binding.setStartXL(getCenterX() - 10);
                binding.setStartXR(getCenterX() + 10);
                binding.setStartYL(getCenterY());
                binding.setStartYR(getCenterY());
            } else {
                binding.setEndXL(getCenterX() - 10);
                binding.setEndXR(getCenterX() + 10);
                binding.setEndYL(getCenterY());
                binding.setEndYR(getCenterY());
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
        ArrayList<SingleGraphicalBinding> singleBindings = singleBindingList.getBindings();
        ArrayList<DoubleGraphicalBinding> doubleBindings = doubleBindingList.getBindings();

        for (SingleGraphicalBinding singleBinding : singleBindings)
            if (singleBinding.getID().equals(bindingID))
                return true;

        for (DoubleGraphicalBinding doubleBinding : doubleBindings)
            if (doubleBinding.getID().equals(bindingID))
                return true;

        return false;
    }

    public boolean hasAtomCommonBindings(@NotNull GenericGraphicalAtom atom) {
        ArrayList<SingleGraphicalBinding> thisAtomSingleBindings = singleBindingList.getBindings();
        ArrayList<SingleGraphicalBinding> otherAtomSingleBindings = atom.getsingleBindingList().getBindings();

        ArrayList<DoubleGraphicalBinding> thisAtomDoubleBindings = doubleBindingList.getBindings();
        ArrayList<DoubleGraphicalBinding> otherAtomDoubleBindings = atom.getDoubleBindingList().getBindings();

        for (SingleGraphicalBinding thisSingleBinding : thisAtomSingleBindings)
            for (SingleGraphicalBinding otherSingleBinding : otherAtomSingleBindings)
                if (thisSingleBinding.getID().equals(otherSingleBinding.getID()))
                    return true;

        for (DoubleGraphicalBinding thisDoubleBinding : thisAtomDoubleBindings)
            for (DoubleGraphicalBinding otherDoubleBinding : otherAtomDoubleBindings)
                if (thisDoubleBinding.getID().equals(otherDoubleBinding.getID()))
                    return true;

        return false;
    }

    public void removeSingleBinding(String bindingID) {
        ArrayList<SingleGraphicalBinding> bindings = singleBindingList.getBindings();
        ArrayList<GenericGraphicalBindingList.Edges> edgesList = singleBindingList.getBindingsEdges();

        int index = 0;

        for (SingleGraphicalBinding binding : bindings) {
            if (binding.getID().equals(bindingID)) {
                bindings.remove(index);
                edgesList.remove(index);
                bindingsRemaining++;
                return;
            }

            index++;
        }
    }

    public void removeDoubleBinding(String bindingID) {
        ArrayList<DoubleGraphicalBinding> bindings = doubleBindingList.getBindings();
        ArrayList<GenericGraphicalBindingList.Edges> edgesList = doubleBindingList.getBindingsEdges();

        int index = 0;

        for (DoubleGraphicalBinding binding : bindings) {
            if (binding.getID().equals(bindingID)) {
                bindings.remove(index);
                edgesList.remove(index);
                bindingsRemaining += 2;
                return;
            }

            index++;
        }
    }

    @Nullable
    public DoubleGraphicalBinding getDoubleGraphicalBindingFromID(String ID) {
        for (DoubleGraphicalBinding binding : doubleBindingList.getBindings())
            if (binding.getID().equals(ID))
                return binding;

        return null;
    }

    @Nullable
    public GenericGraphicalBindingList.Edges getDoubleGraphicalBindingEdge(String ID) {
        int index = 0;

        for (DoubleGraphicalBinding binding : doubleBindingList.getBindings()) {
            if (binding.getID().equals(ID))
                return doubleBindingList.getEdgeFromIndex(index);

            index++;
        }

        return null;
    }

    public GenericGraphicalBindingList<SingleGraphicalBinding> getsingleBindingList() {
        return singleBindingList;
    }

    public GenericGraphicalBindingList<DoubleGraphicalBinding> getDoubleBindingList() {
        return doubleBindingList;
    }
}
