package com.enrico.chemistry.molecule.atomgroup;

import com.enrico.chemistry.molecule.shapedmolecule.ShapedMolecule;

import java.util.ArrayList;

/**
 * This class represents a group of AtomPlaceCards. This makes the algorithm more flexible,
 * because every "group" of atom swill be stored in here and will be stored here.
 *
 * What I mean by "group" is something as suck molecule:
 *      H   H
 *      |   |
 * H----C---C----H
 *      |   |
 *      H   H
 *
 * To represent this we will make two groups: the both with 3 Hydrogen atoms and 1 carbon, eah with its
 * own coordinates.
 *
 * Then we will iterate inside an ArrayList of groups, and we will iterate
 * inside the AtomPlaceCard ArrayList to get the atoms properties to display and the position.
 */
public class AtomGroup {
    private ArrayList<ShapedMolecule.AtomPlaceCard> atoms;

    public AtomGroup(ArrayList<ShapedMolecule.AtomPlaceCard> atoms) {
        this.atoms = atoms;
    }

    public ArrayList<ShapedMolecule.AtomPlaceCard> getAtoms() {
        return atoms;
    }
}
