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
import com.enrico.drawing.graphicalAtoms.bond.GenericGraphicalBindingList;
import com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBinding;
import com.enrico.drawing.graphicalAtoms.bond.singlebond.SingleGraphicalBinding;
import com.enrico.drawing.graphicalAtoms.bond.triplebond.TripleGraphicalBinding;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 *  This class represents an atom drawn in a canvas.
 * This contains all of the methods to represent it and also to handle the bonds between atoms, by using lists.
 * By default, atoms can only do a single bond.
 */
public abstract class GenericGraphicalAtom extends GenericAtom {
    // The atom has a starting point and an end point to start drawing, it is not created by starting from a central
    // position.
    protected int startX;
    protected int endX;
    protected int startY;
    protected int endY;

    // These members represent the area where the mouse click has effect, it is usually slightly bigger than the
    // drawable X and Y so that it will be possible to select the atom even if we're not 100% accurate.
    protected int selectableStartX;
    protected int selectableStartY;
    protected int selectableEndX;
    protected int selectableEndY;

    // How many bond the atom can still do.
    private int bondsRemaining;

    // The path to the image to draw of the atom.
    protected final String imagePath;

    // The ID, unique identifier of the atom.
    private String atomId;

    // These lists represent all of the bonds that the atom has.
    // By default, every atom can only do single bonds, but double and triple bonds can be enabled by initializing them
    // in the sub-class constructor.
    private GenericGraphicalBindingList<SingleGraphicalBinding> singleBindingList = new GenericGraphicalBindingList<>();
    protected GenericGraphicalBindingList<DoubleGraphicalBinding> doubleBindingList = null;
    protected GenericGraphicalBindingList<TripleGraphicalBinding> tripleBindingList = null;

    private GenericGraphicalAtom ionicBindedAtom = null;

    /**
     * This is the only constructor of this class.
     * NB: many of these parameters are only to satisfy the super constructor.
     * @param symbol The symbol that represents this atom.
     * @param completeName The complete name fo the atom.
     * @param atomicNumber The atomic number of the atom.
     * @param atomicMass The atomic mass of the atom.
     * @param electronegativity The electronegativity of the atom.
     * @param bondElectronsNumber The bond electrons of the atom.
     * @param doublets The doublets of electrons that this atoms possess.
     * @param ionizationEnergy The ionization energy of this atom.
     * @param classType The type of class of this atom (nonmetal, halogen, semi-metal etc...).
     * @param startX The start X position of this atom.
     * @param startY The start Y position of this atom.
     * @param endX The end X position of this atom.
     * @param endY The end Y position of this atom.
     * @param bondsRemaining The number of bonds that the atom can still perform.
     * @param imagePath The path of the image to bre drawn on the canvas.
     * @param atomId The unique identifier of the atom.
     */
    public GenericGraphicalAtom(String symbol, String completeName, int atomicNumber, double atomicMass, double electronegativity,
                                int bondElectronsNumber, int doublets, int ionizationEnergy, AtomClassType classType,
                                int startX, int startY, int endX, int endY, int bondsRemaining, String imagePath,
                                String atomId) {
        super(symbol, completeName, atomicNumber, atomicMass, electronegativity, bondElectronsNumber, doublets,
              ionizationEnergy, classType);

        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;

        this.imagePath = imagePath;
        this.atomId = atomId;

        setSelectableCoordinates();

        this.bondsRemaining = bondsRemaining;
    }

    public int getStartX() {
        return startX;
    }

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
        return bondsRemaining;
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

    public GenericGraphicalAtom getIonicBindedAtom() {
        return ionicBindedAtom;
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

    /**
     * This method performs a single bond on the current atom and sets its edge (beginning or end).
     * @param bond The single bond to add.
     * @param edge The edge to set (beginning or end).
     */
    public void doSingleBinding(SingleGraphicalBinding bond, GenericGraphicalBindingList.Edges edge) {
        if (bondsRemaining - 1 >= 0)
            bondsRemaining--;

        singleBindingList.addBinding(bond, edge);
    }

    /**
     * This method performs a double bond on the current atom and sets its edge (beginning or end).
     * @param bond The double bond to add.
     * @param edge The edge to set (beginning or end).
     */
    public void doDoubleBinding(DoubleGraphicalBinding bond, GenericGraphicalBindingList.Edges edge) {
        if (bondsRemaining - 2 >= 0)
            bondsRemaining -= 2;
        
        doubleBindingList.addBinding(bond, edge);
    }

    /**
     * This method performs a triple bond on the current atom and sets its edge (beginning or end).
     * @param bond The triple bond to add.
     * @param edge The edge to set (beginning or end).
     */
    public void doTripleBinding(TripleGraphicalBinding bond, GenericGraphicalBindingList.Edges edge) {
        if (bondsRemaining - 3 >= 0)
            bondsRemaining -= 3;

        tripleBindingList.addBinding(bond, edge);
    }

    /**
     * This method moves the atom to a specified X and Y position (start)
     * @param startX the new starting X position of the atom.
     * @param startY the new starting Y position of the atom.
     */
    public void move(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;

        endX = startX + 23;
        endY = startY + 23;

        setSelectableCoordinates();

        moveSingleBindings();

        if (doubleBindingList != null)
            moveDoubleBindings();

        if (tripleBindingList != null)
            moveTripleBindings();
    }

    /**
     * Since when we move an atom the bond won't be moving correctly between the two atoms, this functions reloads
     * the atom by moving it at the same position it was before, so that the bond will adjust itself.
     */
    public void reload() {
        move(getStartX(), getStartY());
    }

    /**
     * This method moves every single bond to the new atom position.
     * this method is usually called after moving an atom.
     */
    private void moveSingleBindings() {
        ArrayList<SingleGraphicalBinding> bonds = singleBindingList.getBindings();
        ArrayList<GenericGraphicalBindingList.Edges> edges = singleBindingList.getBindingsEdges();
        int bondIndex = 0;

        for (SingleGraphicalBinding bond : bonds) {
            if (edges.get(bondIndex) == GenericGraphicalBindingList.Edges.Start) {
                bond.setStartX(getCenterX());
                bond.setStartY(getCenterY());
            } else {
                bond.setEndX(getCenterX());
                bond.setEndY(getCenterY());
            }

            bondIndex++;
        }
    }

    /**
     * This method moves every double bond to the new atom position.
     * this method is usually called after moving an atom.
     */
    private void moveDoubleBindings() {
        ArrayList<DoubleGraphicalBinding> bonds = doubleBindingList.getBindings();
        ArrayList<GenericGraphicalBindingList.Edges> edges = doubleBindingList.getBindingsEdges();
        int bondIndex = 0;

        for (DoubleGraphicalBinding bond : bonds) {
            if (edges.get(bondIndex) == GenericGraphicalBindingList.Edges.Start) {
                bond.setStartXL(getCenterX() - 10);
                bond.setStartXR(getCenterX() + 10);
                bond.setStartYL(getCenterY());
                bond.setStartYR(getCenterY());
            } else {
                bond.setEndXL(getCenterX() - 10);
                bond.setEndXR(getCenterX() + 10);
                bond.setEndYL(getCenterY());
                bond.setEndYR(getCenterY());
            }

            bondIndex++;
        }
    }

    /**
     * This method moves every triple bond to the new atom position.
     * this method is usually called after moving an atom.
     */
    private void moveTripleBindings() {
        ArrayList<TripleGraphicalBinding> bonds = tripleBindingList.getBindings();
        ArrayList<GenericGraphicalBindingList.Edges> edges = tripleBindingList.getBindingsEdges();
        int bondIndex = 0;

        for (TripleGraphicalBinding bond : bonds) {
            if (edges.get(bondIndex) == GenericGraphicalBindingList.Edges.Start) {
                bond.setStartCentralX(getCenterX());
                bond.setStartCentralY(getCenterY());
                bond.setStartRightX(getCenterX() - 10);
                bond.setStartRightY(getCenterY());
                bond.setStartLeftX(getCenterX() + 10);
                bond.setStartLeftY(getCenterY());
            } else {
                bond.setEndCentralX(getCenterX());
                bond.setEndCentralY(getCenterY());
                bond.setEndRightX(getCenterX() - 10);
                bond.setEndRightY(getCenterY());
                bond.setEndLeftX(getCenterX() + 10);
                bond.setEndLeftY(getCenterY());
            }
        }
    }

    /**
     * This method updated the selectable coordinates after the atom being moved.
     */
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

    /**
     * This method checks if the current atom has a bond or not.
     * @param bondID The ID of the bond to check if is on this atom.
     * @return true if the bond is present, false otherwise.
     */
    public boolean hasAtomBinding(String bondID) {
        ArrayList<SingleGraphicalBinding> singleBindings = singleBindingList.getBindings();
        ArrayList<DoubleGraphicalBinding> doubleBindings = null;
        ArrayList<TripleGraphicalBinding> tripleBindings = null;

        if (doubleBindingList != null)
            doubleBindings = doubleBindingList.getBindings();

        if (tripleBindingList != null)
            tripleBindings = tripleBindingList.getBindings();

        for (SingleGraphicalBinding singleBinding : singleBindings)
            if (singleBinding.getID().equals(bondID))
                return true;

        if (doubleBindings != null)
            for (DoubleGraphicalBinding doubleBinding : doubleBindings)
                if (doubleBinding.getID().equals(bondID))
                    return true;

        if (tripleBindings != null)
            for (TripleGraphicalBinding tripleBinding : tripleBindings)
                if (tripleBinding.getID().equals(bondID))
                    return true;

        return false;
    }

    /**
     * This method checks if the current atom has common atoms with another atom.
     * @param atom The atom to check if has bonds in common with this atom.
     * @return true if the atom has common bonds with the current atom, false otherwise.
     */
    public boolean hasAtomCommonBindings(@NotNull GenericGraphicalAtom atom) {
        ArrayList<SingleGraphicalBinding> thisAtomSingleBindings = singleBindingList.getBindings();
        ArrayList<SingleGraphicalBinding> otherAtomSingleBindings = atom.getSingleBindingList().getBindings();

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

    /**
     * This method removes a single bond from this atom.
     * @param bondID The bond ID that represents the bond to remove from the atom.
     */
    public void removeSingleBinding(String bondID) {
        ArrayList<SingleGraphicalBinding> bonds = singleBindingList.getBindings();
        ArrayList<GenericGraphicalBindingList.Edges> edgesList = singleBindingList.getBindingsEdges();

        int index = 0;

        for (SingleGraphicalBinding bond : bonds) {
            if (bond.getID().equals(bondID)) {
                bonds.remove(index);
                edgesList.remove(index);
                break;
            }

            index++;
        }

        bondsRemaining++;
    }

    /**
     * This method removes a double bond from this atom.
     * @param bondID The bond ID that represents the bond to remove from the atom.
     */
    public void removeDoubleBinding(String bondID) {
        ArrayList<DoubleGraphicalBinding> bonds = doubleBindingList.getBindings();
        ArrayList<GenericGraphicalBindingList.Edges> edgesList = doubleBindingList.getBindingsEdges();

        int index = 0;

        for (DoubleGraphicalBinding bond : bonds) {
            if (bond.getID().equals(bondID)) {
                bonds.remove(index);
                edgesList.remove(index);
                break;
            }

            index++;
        }

        bondsRemaining += 2;
    }

    /**
     * This method removes a triple bond from this atom.
     * @param bondID The bond ID that represents the bond to remove from the atom.
     */
    public void removeTripleBinding(String bondID) {
        ArrayList<TripleGraphicalBinding> bonds = tripleBindingList.getBindings();
        ArrayList<GenericGraphicalBindingList.Edges> edgesList = tripleBindingList.getBindingsEdges();

        int index = 0;

        for (TripleGraphicalBinding bond: bonds) {
            if (bond.getID().equals(bondID)) {
                bonds.remove(index);
                edgesList.remove(index);
                break;
            }

            index++;
        }

        bondsRemaining += 3;
    }

    /**
     * This method removes all o the bonds of the atom.
     * This is usually called when the current atom is being deleted.
     */
    public void removeAllBindings() {
        for (SingleGraphicalBinding bond : singleBindingList.getBindings())
            bond.markDeletion();

        if (doubleBindingList != null)
            for (DoubleGraphicalBinding bond : doubleBindingList.getBindings())
                bond.markDeletion();

        if (tripleBindingList != null)
            for (TripleGraphicalBinding bond : tripleBindingList.getBindings())
                bond.markDeletion();
    }

    /**
     * This method returns a double bond starting from its ID.
     * @param ID The ID of the atom to check if is present.
     * @return returns the double bond if present, or null otherwise.
     */
    @Nullable
    public DoubleGraphicalBinding getDoubleGraphicalBindingFromID(String ID) {
        for (DoubleGraphicalBinding bond : doubleBindingList.getBindings())
            if (bond.getID().equals(ID))
                return bond;

        return null;
    }

    /**
     * Gets the edge of a double bond starting from its ID.
     * @param ID The ID of te atom to get the Edge from.
     * @return The Edge of the bond, or null otherwise.
     */
    @Nullable
    public GenericGraphicalBindingList.Edges getDoubleGraphicalBindingEdge(String ID) {
        int index = 0;

        for (DoubleGraphicalBinding bond : doubleBindingList.getBindings()) {
            if (bond.getID().equals(ID))
                return doubleBindingList.getEdgeFromIndex(index);

            index++;
        }

        return null;
    }

    public void performIonicBinding(GenericGraphicalAtom bondedAtom) {
        if (bondsRemaining < 0)
            return;

        ionicBindedAtom = bondedAtom;
        bondsRemaining--;
    }

    public void removeIonicBinding(String atomID) {
        if (ionicBindedAtom != null) {
            if (ionicBindedAtom.getAtomId().equals(atomID)) {
                ionicBindedAtom = null;
                bondsRemaining++;
            }
        }
    }

    public GenericGraphicalBindingList<SingleGraphicalBinding> getSingleBindingList() {
        return singleBindingList;
    }

    public GenericGraphicalBindingList<DoubleGraphicalBinding> getDoubleBindingList() {
        return doubleBindingList;
    }

    public GenericGraphicalBindingList<TripleGraphicalBinding> getTripleBindingList() {
        return tripleBindingList;
    }

    public boolean isAtomUpper(@NotNull GenericGraphicalAtom atom) {
        return getCenterY() > atom.getCenterY();
    }

    public boolean hasIonicBinding() {
        return ionicBindedAtom != null;
    }
}
