package com.enrico.chemistry.molecule.shapedmolecule;

import com.enrico.chemistry.atoms.Atom;
import com.enrico.chemistry.atoms.HydrogenAtom;
import com.enrico.chemistry.molecule.Molecule;
import com.enrico.chemistry.molecule.atomgroup.AtomGroup;

import java.util.ArrayList;

/**
 * This class represents an entire molecule, except that it keeps track of the position of every single
 * atom.
 *
 * This task is handled internally by the AtomGroup class that uses the ShapeMolecule internal class
 * "AtomPlaceCard".
 */
public class ShapedMolecule {
    private ArrayList<AtomGroup> atomGroups = new ArrayList<>();

    public ShapedMolecule(Molecule molecule, int xCenter, int yCenter) {

        ArrayList<HydrogenAtom> hydrogenAtoms = molecule.getHydrogenAtoms();
        int hydrogenAtomsSize = hydrogenAtoms.size();
        int hydrogenAtomIndex = 0;
        
        switch (molecule.getMoleculeShape()) {
            case SquareShape:

                ArrayList<Atom> bindedAtoms = molecule.getBindedAtoms();

                if (bindedAtoms.size() == 4) {
                    ArrayList<AtomPlaceCard> atoms = new ArrayList<>();
                    atoms.add(new AtomPlaceCard(molecule.getCentralAtom(), xCenter, yCenter));

                    atoms.add(new AtomPlaceCard(bindedAtoms.get(0), xCenter - 20, yCenter));
                    atoms.add(new AtomPlaceCard(bindedAtoms.get(1), xCenter + 20, yCenter));
                    atoms.add(new AtomPlaceCard(bindedAtoms.get(2), xCenter, yCenter - 20));
                    atoms.add(new AtomPlaceCard(bindedAtoms.get(3), xCenter, yCenter + 20));

                    atomGroups.add(new AtomGroup(atoms));

                    ArrayList<AtomPlaceCard> placeCardCopy = new ArrayList<>(atoms);

                    if (hydrogenAtomsSize > 0 && !bindedAtoms.containsAll(hydrogenAtoms)) {
                        for (AtomPlaceCard placeCard : placeCardCopy) {
                            if (hydrogenAtomIndex > hydrogenAtomsSize - 1)
                                break;

                            // Coordinates of the central atom.
                            if (placeCard.x == xCenter && placeCard.y == yCenter)
                                continue;

                            if (placeCard.x - xCenter > 0 && placeCard.y - yCenter == 0) {
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                      xCenter - 40, yCenter));
                            } else if (placeCard.x < 0 && placeCard.y - yCenter == 0) {
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                        xCenter + 40, yCenter));
                            } else if (placeCard.x - xCenter == 0 && placeCard.y > yCenter) {
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                        xCenter, yCenter + 40));
                            } else {
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                        xCenter, yCenter - 40));
                            }

                            hydrogenAtomIndex++;
                        }
                    }
                }
            break;

            case LineShape:
                ArrayList<Atom> bindedAtomsLine = molecule.getBindedAtoms();
                if (bindedAtomsLine.size() >= 1) {
                    ArrayList<AtomPlaceCard> atoms = new ArrayList<>();

                    atoms.add(new AtomPlaceCard(molecule.getCentralAtom(), xCenter, yCenter));
                    atoms.add(new AtomPlaceCard(bindedAtomsLine.get(0), xCenter - 20, yCenter));

                    if (bindedAtomsLine.size() >= 2)
                        atoms.add(new AtomPlaceCard(bindedAtomsLine.get(1), xCenter + 20, yCenter));

                    atomGroups.add(new AtomGroup(atoms));
                }
            break;

            case PyramidShape:
                ArrayList<Atom> bindedAtomsPyramid = molecule.getBindedAtoms();

                if (bindedAtomsPyramid.size() >= 2) {
                    ArrayList<AtomPlaceCard> atoms = new ArrayList<>();
                    atoms.add(new AtomPlaceCard(molecule.getCentralAtom(), xCenter, yCenter));

                    atoms.add(new AtomPlaceCard(bindedAtomsPyramid.get(0), xCenter - 20, yCenter - 20));
                    atoms.add(new AtomPlaceCard(bindedAtomsPyramid.get(1), xCenter + 20, yCenter - 20));

                    if (bindedAtomsPyramid.size() >= 3) {
                        atoms.add(new AtomPlaceCard(bindedAtomsPyramid.get(2), xCenter, yCenter - 30));
                    }

                    atomGroups.add(new AtomGroup(atoms));
                }
            break;

            case TriangularShape:
                ArrayList<Atom> bindedAtomsTriangular = molecule.getBindedAtoms();

                if (bindedAtomsTriangular.size() == 3) {
                    ArrayList<AtomPlaceCard> atoms = new ArrayList<>();
                    atoms.add(new AtomPlaceCard(molecule.getCentralAtom(), xCenter, yCenter));

                    atoms.add(new AtomPlaceCard(bindedAtomsTriangular.get(0), xCenter - 20, yCenter - 20));
                    atoms.add(new AtomPlaceCard(bindedAtomsTriangular.get(1), xCenter + 20, yCenter - 20));
                    atoms.add(new AtomPlaceCard(bindedAtomsTriangular.get(2), xCenter, yCenter + 30));

                    atomGroups.add(new AtomGroup(atoms));

                    ArrayList<AtomPlaceCard> placeCardCopy = new ArrayList<>(atoms);

                    if (hydrogenAtomsSize > 0 && !bindedAtomsTriangular.containsAll(hydrogenAtoms)) {
                        for (AtomPlaceCard placeCard : placeCardCopy) {
                            if (hydrogenAtomIndex > hydrogenAtomsSize - 1)
                                break;

                            // Coordinates of the central atom.
                            if (placeCard.x == xCenter && placeCard.y == yCenter)
                                continue;

                            if (placeCard.x - (xCenter - 20) == 0 && placeCard.y - (yCenter - 20) == 0) {
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                          placeCard.x - 20, placeCard.y));
                            } else if (placeCard.x - (xCenter + 20) == 0 && placeCard.y - (yCenter - 20) == 0) {
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                        placeCard.x + 20, placeCard.y));
                            } else {
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                        placeCard.x, placeCard.y + 20));
                            }

                            hydrogenAtomIndex++;
                        }
                    }
                }
            break;
        }
    }

    public ArrayList<AtomGroup> getAtomGroups() {
        return atomGroups;
    }

    /**
     * This class represents an atom to be displayed on the canvas.
     *
     * Instead of the "Atom" class that is used to handle the operations for the physical part of the atom,
     * this class is only here to represent the Atom's symbol and its place on the Canvas.
     */
    public static final class AtomPlaceCard {
        private String atomSymbol;

        private int x, y;

        AtomPlaceCard(Atom atom, int x, int y) {
            atomSymbol = atom.getSymbol();
            this.x = x;
            this.y = y;
        }

        public String getAtomSymbol() {
            return atomSymbol;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
