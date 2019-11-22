package com.enrico.chemistry.molecule.shapedmolecule;

import com.enrico.chemistry.atoms.Atom;
import com.enrico.chemistry.atoms.HydrogenAtom;
import com.enrico.chemistry.molecule.Molecule;
import com.enrico.chemistry.molecule.atomgroup.AtomGroup;
import com.enrico.drawing.Line;

import java.util.ArrayList;

/**
 * This class represents an entire molecule, except that it keeps track of the position of every single
 * atom.
 *
 * This task is handled internally by the AtomGroup class that uses the ShapeMolecule internal class
 * "AtomPlaceCard".
 */
public final class ShapedMolecule {
    private ArrayList<AtomGroup> atomGroups = new ArrayList<>();
    private ArrayList<Line> lineGroups = new ArrayList<>();
    private final Molecule molecule;

    public ShapedMolecule(Molecule molecule, int xCenter, int yCenter) {

        ArrayList<HydrogenAtom> hydrogenAtoms = molecule.getHydrogenAtoms();
        int hydrogenAtomsSize = hydrogenAtoms.size();
        int hydrogenAtomIndex = 0;
        this.molecule = molecule;
        
        switch (molecule.getMoleculeShape()) {
            case SquareShape:

                ArrayList<Atom> bindedAtoms = molecule.getBindedAtoms();

                if (bindedAtoms.size() == 4) {
                    ArrayList<AtomPlaceCard> atoms = new ArrayList<>();
                    atoms.add(new AtomPlaceCard(molecule.getCentralAtom(), xCenter, yCenter, AtomPlaceCard.Positions.Center));

                    atoms.add(new AtomPlaceCard(bindedAtoms.get(0), xCenter - 20, yCenter, AtomPlaceCard.Positions.Left));
                    atoms.add(new AtomPlaceCard(bindedAtoms.get(1), xCenter + 20, yCenter, AtomPlaceCard.Positions.Right));
                    atoms.add(new AtomPlaceCard(bindedAtoms.get(2), xCenter, yCenter - 20, AtomPlaceCard.Positions.Top));
                    atoms.add(new AtomPlaceCard(bindedAtoms.get(3), xCenter, yCenter + 20, AtomPlaceCard.Positions.Bottom));

                    atomGroups.add(new AtomGroup(atoms));

                    ArrayList<AtomPlaceCard> placeCardCopy = new ArrayList<>(atoms);

                    if (hydrogenLoopCondition(hydrogenAtomsSize, bindedAtoms, hydrogenAtoms)) {
                        for (AtomPlaceCard placeCard : placeCardCopy) {
                            if (hydrogenAtomIndex > hydrogenAtomsSize - 1)
                                break;

                            // Coordinates of the central atom.
                            if (placeCard.x == xCenter && placeCard.y == yCenter)
                                continue;

                            if (placeCard.x - xCenter > 0 && placeCard.y - yCenter == 0) {           // Left.
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                      xCenter - 40, yCenter, AtomPlaceCard.Positions.Left));
                            } else if (placeCard.x < 0 && placeCard.y - yCenter == 0) {              // Right.
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                        xCenter + 40, yCenter, AtomPlaceCard.Positions.Right));
                            } else if (placeCard.x - xCenter == 0 && placeCard.y > yCenter) {        // Bottom.
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                        xCenter, yCenter + 40, AtomPlaceCard.Positions.Bottom));
                            } else {                                                                 // Top.
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                        xCenter, yCenter - 40, AtomPlaceCard.Positions.Top));
                            }

                            hydrogenAtomIndex++;
                        }
                    }

                    addLines(atoms);
                }
            break;

            case LineShape:
                ArrayList<Atom> bindedAtomsLine = molecule.getBindedAtoms();
                if (bindedAtomsLine.size() >= 1) {
                    ArrayList<AtomPlaceCard> atoms = new ArrayList<>();

                    atoms.add(new AtomPlaceCard(molecule.getCentralAtom(), xCenter, yCenter, AtomPlaceCard.Positions.Center));
                    atoms.add(new AtomPlaceCard(bindedAtomsLine.get(0), xCenter - 20, yCenter, AtomPlaceCard.Positions.Left));

                    if (bindedAtomsLine.size() >= 2)
                        atoms.add(new AtomPlaceCard(bindedAtomsLine.get(1), xCenter + 20, yCenter, AtomPlaceCard.Positions.Right));

                    atomGroups.add(new AtomGroup(atoms));

                    ArrayList<AtomPlaceCard> placeCardCopy = new ArrayList<>(atoms);

                    if (hydrogenLoopCondition(hydrogenAtomsSize, bindedAtomsLine, hydrogenAtoms)) {
                        for (AtomPlaceCard placeCard : placeCardCopy) {
                            if (hydrogenAtomIndex > hydrogenAtomsSize - 1)
                                break;

                            // Coordinates of the central atom.
                            if (placeCard.x == xCenter && placeCard.y == yCenter)
                                continue;

                            if (placeCard.x - xCenter > 0 && placeCard.y - yCenter == 0) {          // Left.
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                        xCenter - 40, yCenter, AtomPlaceCard.Positions.Left));
                            } else {                                                                // Right.
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                        xCenter + 40, yCenter, AtomPlaceCard.Positions.Right));
                            }

                            hydrogenAtomIndex++;
                        }
                    }
                }
            break;

            case PyramidShape:
                ArrayList<Atom> bindedAtomsPyramid = molecule.getBindedAtoms();

                if (bindedAtomsPyramid.size() >= 2) {
                    ArrayList<AtomPlaceCard> atoms = new ArrayList<>();
                    atoms.add(new AtomPlaceCard(molecule.getCentralAtom(), xCenter, yCenter, AtomPlaceCard.Positions.Center));

                    atoms.add(new AtomPlaceCard(bindedAtomsPyramid.get(0), xCenter - 20, yCenter - 20, AtomPlaceCard.Positions.TopRight));
                    atoms.add(new AtomPlaceCard(bindedAtomsPyramid.get(1), xCenter + 20, yCenter - 20, AtomPlaceCard.Positions.TopLeft));

                    if (bindedAtomsPyramid.size() >= 3) {
                        atoms.add(new AtomPlaceCard(bindedAtomsPyramid.get(2), xCenter, yCenter - 30, AtomPlaceCard.Positions.Bottom));
                    }

                    atomGroups.add(new AtomGroup(atoms));

                    ArrayList<AtomPlaceCard> placeCardCopy = new ArrayList<>(atoms);

                    if (hydrogenLoopCondition(hydrogenAtomsSize, bindedAtomsPyramid, hydrogenAtoms)) {
                        for (AtomPlaceCard placeCard : placeCardCopy) {
                            if (hydrogenAtomIndex > hydrogenAtomsSize - 1)
                                break;

                            // Coordinates of the central atom.
                            if (placeCard.x == xCenter && placeCard.y == yCenter)
                                continue;

                            // No distinction is made here for position.
                            atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                    placeCard.x, yCenter - 40, AtomPlaceCard.Positions.Bottom));

                            hydrogenAtomIndex++;
                        }
                    }
                    addLines(atoms);
                }
            break;

            case TriangularShape:
                ArrayList<Atom> bindedAtomsTriangular = molecule.getBindedAtoms();

                if (bindedAtomsTriangular.size() == 3) {
                    ArrayList<AtomPlaceCard> atoms = new ArrayList<>();
                    atoms.add(new AtomPlaceCard(molecule.getCentralAtom(), xCenter, yCenter, AtomPlaceCard.Positions.Center));

                    atoms.add(new AtomPlaceCard(bindedAtomsTriangular.get(0), xCenter - 20, yCenter - 20, AtomPlaceCard.Positions.TopLeft));
                    atoms.add(new AtomPlaceCard(bindedAtomsTriangular.get(1), xCenter + 20, yCenter - 20, AtomPlaceCard.Positions.TopRight));
                    atoms.add(new AtomPlaceCard(bindedAtomsTriangular.get(2), xCenter, yCenter + 30, AtomPlaceCard.Positions.Top));

                    atomGroups.add(new AtomGroup(atoms));

                    ArrayList<AtomPlaceCard> placeCardCopy = new ArrayList<>(atoms);

                    if (hydrogenLoopCondition(hydrogenAtomsSize, bindedAtomsTriangular, hydrogenAtoms)) {
                        for (AtomPlaceCard placeCard : placeCardCopy) {
                            if (hydrogenAtomIndex > hydrogenAtomsSize - 1)
                                break;

                            // Coordinates of the central atom.
                            if (placeCard.x == xCenter && placeCard.y == yCenter)
                                continue;

                            if (placeCard.x - (xCenter - 20) == 0 && placeCard.y - (yCenter - 20) == 0) {        // Top - right.
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                          placeCard.x - 20, placeCard.y, AtomPlaceCard.Positions.TopRight));
                            } else if (placeCard.x - (xCenter + 20) == 0 && placeCard.y - (yCenter - 20) == 0) { // Top - left.
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                        placeCard.x + 20, placeCard.y, AtomPlaceCard.Positions.TopLeft));
                            } else {
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),               // Center - bottom.
                                        placeCard.x, placeCard.y + 20, AtomPlaceCard.Positions.Bottom));
                            }

                            hydrogenAtomIndex++;
                        }
                    }
                    addLines(atoms);
                }
            break;

            case FivePointedStar:
                ArrayList<Atom> bindedAtomsFiveStar = molecule.getBindedAtoms();

                if (bindedAtomsFiveStar.size() == 5) {
                    ArrayList<AtomPlaceCard> atoms = new ArrayList<>();
                    atoms.add(new AtomPlaceCard(molecule.getCentralAtom(), xCenter, yCenter, AtomPlaceCard.Positions.Center));

                    atoms.add(new AtomPlaceCard(bindedAtomsFiveStar.get(0), xCenter, yCenter - 40, AtomPlaceCard.Positions.Top));
                    atoms.add(new AtomPlaceCard(bindedAtomsFiveStar.get(1), xCenter - 20, yCenter - 20, AtomPlaceCard.Positions.TopLeft));
                    atoms.add(new AtomPlaceCard(bindedAtomsFiveStar.get(2), xCenter - 20, yCenter + 20, AtomPlaceCard.Positions.BottomRight));
                    atoms.add(new AtomPlaceCard(bindedAtomsFiveStar.get(3), xCenter + 20, yCenter + 20, AtomPlaceCard.Positions.BottomLeft));
                    atoms.add(new AtomPlaceCard(bindedAtomsFiveStar.get(4), xCenter + 20, yCenter - 20, AtomPlaceCard.Positions.TopRight));

                    atomGroups.add(new AtomGroup(atoms));

                    ArrayList<AtomPlaceCard> placeCardCopy = new ArrayList<>(atoms);

                    if (hydrogenLoopCondition(hydrogenAtomsSize, bindedAtomsFiveStar, hydrogenAtoms)) {
                        for (AtomPlaceCard placeCard : placeCardCopy) {
                            if (hydrogenAtomIndex > hydrogenAtomsSize - 1)
                                break;

                            // Coordinates of the central atom.
                            if (placeCard.x == xCenter && placeCard.y == yCenter)
                                continue;

                            if (placeCard.x - xCenter == 0 && placeCard.y - (yCenter - 20) == 0) { // Top.
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                          placeCard.x, placeCard.y - 40, AtomPlaceCard.Positions.Top));
                            } else if ((placeCard.x - (xCenter - 20) == 0 && placeCard.y - (yCenter - 20) == 0) || // Top - right.
                                       (placeCard.x - (xCenter - 20) == 0 && placeCard.y - (yCenter + 20) == 0)) { // Bottom - right.
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                          placeCard.x - 20, placeCard.y, AtomPlaceCard.Positions.Right));
                            } else { // Top - left & Bottom - left.
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                        placeCard.x - 20, placeCard.y, AtomPlaceCard.Positions.Left));
                            }

                            hydrogenAtomIndex++;
                        }
                    }
                    addLines(atoms);
                }
            break;

            case SixPointedStar:
                ArrayList<Atom> bindedAtomsSixStar = molecule.getBindedAtoms();

                if (bindedAtomsSixStar.size() == 6) {
                    ArrayList<AtomPlaceCard> atoms = new ArrayList<>();
                    atoms.add(new AtomPlaceCard(molecule.getCentralAtom(), xCenter, yCenter, AtomPlaceCard.Positions.Center));

                    atoms.add(new AtomPlaceCard(bindedAtomsSixStar.get(0), xCenter, yCenter - 40, AtomPlaceCard.Positions.Top));
                    atoms.add(new AtomPlaceCard(bindedAtomsSixStar.get(1), xCenter, yCenter + 40, AtomPlaceCard.Positions.Bottom));
                    atoms.add(new AtomPlaceCard(bindedAtomsSixStar.get(2), xCenter - 20, yCenter - 20, AtomPlaceCard.Positions.TopLeft));
                    atoms.add(new AtomPlaceCard(bindedAtomsSixStar.get(3), xCenter - 20, yCenter + 20, AtomPlaceCard.Positions.BottomLeft));
                    atoms.add(new AtomPlaceCard(bindedAtomsSixStar.get(4), xCenter + 20, yCenter + 20, AtomPlaceCard.Positions.BottomRight));
                    atoms.add(new AtomPlaceCard(bindedAtomsSixStar.get(5), xCenter + 20, yCenter - 20, AtomPlaceCard.Positions.TopRight));

                    atomGroups.add(new AtomGroup(atoms));

                    ArrayList<AtomPlaceCard> placeCardCopy = new ArrayList<>(atoms);

                    if (hydrogenLoopCondition(hydrogenAtomsSize, bindedAtomsSixStar, hydrogenAtoms)) {
                        for (AtomPlaceCard placeCard : placeCardCopy) {
                            if (hydrogenAtomIndex > hydrogenAtomsSize - 1)
                                break;

                            // Coordinates of the central atom.
                            if (placeCard.x == xCenter && placeCard.y == yCenter)
                                continue;

                            if (placeCard.x - xCenter == 0 && placeCard.y - (yCenter - 40) == 0) { // Top.
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                        placeCard.x, placeCard.y - 40, AtomPlaceCard.Positions.Bottom));
                            } else if (placeCard.x - xCenter == 0 && placeCard.y - (yCenter + 40) == 0) { // Bottom.
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                        placeCard.x, placeCard.y + 40, AtomPlaceCard.Positions.Top));
                            } else if ((placeCard.x - (xCenter - 20) == 0 && placeCard.y - (yCenter - 20) == 0) || // Top - right.
                                    (placeCard.x - (xCenter - 20) == 0 && placeCard.y - (yCenter + 20) == 0)) { // Bottom - right.
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                        placeCard.x - 20, placeCard.y, AtomPlaceCard.Positions.Right));
                            } else { // Top - left & Bottom - left.
                                atoms.add(new AtomPlaceCard(hydrogenAtoms.get(hydrogenAtomIndex),
                                        placeCard.x - 20, placeCard.y, AtomPlaceCard.Positions.Left));
                            }

                            hydrogenAtomIndex++;
                        }
                    }
                    addLines(atoms);
                }
            break;
        }
    }

    private void addLines(ArrayList<AtomPlaceCard> atoms) {
        Atom moleculeCentralAtom = molecule.getCentralAtom();
        AtomPlaceCard currentCentralAtomPlaceCard;
        AtomPlaceCard lastPlaceCard = null;

        ArrayList<AtomPlaceCard> placeCardsForHydrogen = new ArrayList<>();

        if (!moleculeCentralAtom.getClass().equals(HydrogenAtom.class)) {
            currentCentralAtomPlaceCard = atoms.get(0);
        } else {
            lineGroups.add(new Line(atoms.get(0).x + 10, atoms.get(1).x - 10,
                                    atoms.get(0).y, atoms.get(0).y));
            return;
        }

        System.out.println("[LOG] Place card size: " + atoms.size());

        for (AtomPlaceCard placeCard : atoms) {
            if (lastPlaceCard == null)
                lastPlaceCard = placeCard;

            if (placeCard.getAtomSymbol().equals(HydrogenAtom.ATOM_SYMBOL)) {
                if (!molecule.isMoleculeSimple()) {
                    placeCardsForHydrogen.add(placeCard);
                    continue;
                }
            }


            if (placeCard.position == AtomPlaceCard.Positions.Center)
                continue;

            if (placeCard.position == AtomPlaceCard.Positions.Left)
                lineGroups.add(new Line(placeCard.x + 10, currentCentralAtomPlaceCard.x - 5,
                        currentCentralAtomPlaceCard.y - 4, currentCentralAtomPlaceCard.y - 4));

            else if (placeCard.position == AtomPlaceCard.Positions.Right)
                lineGroups.add(new Line(placeCard.x - 5, currentCentralAtomPlaceCard.x + 10,
                        currentCentralAtomPlaceCard.y - 4, currentCentralAtomPlaceCard.y - 4));

            else if (placeCard.position == AtomPlaceCard.Positions.Top)
                lineGroups.add(new Line(placeCard.x + 4, placeCard.x + 4,
                        currentCentralAtomPlaceCard.y - 12, placeCard.y + 4));

            else if (placeCard.position == AtomPlaceCard.Positions.Bottom)
                lineGroups.add(new Line(placeCard.x + 4, placeCard.x + 4, currentCentralAtomPlaceCard.y + 4,
                        placeCard.y - 12));

            else if (placeCard.position == AtomPlaceCard.Positions.TopRight)
                lineGroups.add(new Line(placeCard.x + 5, currentCentralAtomPlaceCard.x,
                        placeCard.y, currentCentralAtomPlaceCard.y - 10));

            else if (placeCard.position == AtomPlaceCard.Positions.BottomRight) {
                if (molecule.getMoleculeShape() == Molecule.ShapeEnum.FivePointedStar)
                    lineGroups.add(new Line(placeCard.x + 3, currentCentralAtomPlaceCard.x - 5,
                            placeCard.y - 10, currentCentralAtomPlaceCard.y + 3));
                else
                    lineGroups.add(new Line(placeCard.x - 2, currentCentralAtomPlaceCard.x + 5,
                            placeCard.y - 3, currentCentralAtomPlaceCard.y + 3));
            }

            else if (placeCard.position == AtomPlaceCard.Positions.BottomLeft) {
                if (molecule.getMoleculeShape() == Molecule.ShapeEnum.FivePointedStar)
                    lineGroups.add(new Line(placeCard.x - 3, currentCentralAtomPlaceCard.x + 5,
                            placeCard.y - 5, currentCentralAtomPlaceCard.y + 3));
                else
                    lineGroups.add(new Line(placeCard.x + 3, currentCentralAtomPlaceCard.x,
                            placeCard.y - 10, currentCentralAtomPlaceCard.y));
            }

            else
                lineGroups.add(new Line(placeCard.x, currentCentralAtomPlaceCard.x + 7,
                        placeCard.y, currentCentralAtomPlaceCard.y - 11));
        }
/*
        for (AtomPlaceCard placeCard : placeCardsForHydrogen) {
            for (AtomPlaceCard formulaPlaceCard : atoms) {
                if (formulaPlaceCard.getAtomSymbol().equals(HydrogenAtom.ATOM_SYMBOL)) {

                    if (formulaPlaceCard.position == AtomPlaceCard.Positions.Left ||
                        formulaPlaceCard.position == AtomPlaceCard.Positions.TopLeft ||
                        formulaPlaceCard.position == AtomPlaceCard.Positions.BottomLeft)
                        lineGroups.add(new Line(formulaPlaceCard.x + 10, placeCard.x - 5,
                                placeCard.y - 4, placeCard.y - 4));

                    else if (formulaPlaceCard.position == AtomPlaceCard.Positions.Right ||
                            formulaPlaceCard.position == AtomPlaceCard.Positions.TopRight ||
                            formulaPlaceCard.position == AtomPlaceCard.Positions.BottomRight)
                        lineGroups.add(new Line(formulaPlaceCard.x - 5, placeCard.x + 10,
                                placeCard.y - 4, placeCard.y - 4));

                    else if (formulaPlaceCard.position == AtomPlaceCard.Positions.Top)
                        lineGroups.add(new Line(formulaPlaceCard.x + 4, formulaPlaceCard.x + 4,
                                placeCard.y - 12, formulaPlaceCard.y + 4));

                    else if (placeCard.position == AtomPlaceCard.Positions.Bottom)
                        lineGroups.add(new Line(placeCard.x + 4, placeCard.x + 4, placeCard.y + 4,
                                placeCard.y - 12));
                }
            }
        }*/
    }

    private boolean hydrogenLoopCondition(int hydrogenAtomsSize, ArrayList<Atom> bindedList,
                                          ArrayList<HydrogenAtom> hydrogenAtoms) {
        return hydrogenAtomsSize > 0 && !bindedList.containsAll(hydrogenAtoms);
    }

    public ArrayList<AtomGroup> getAtomGroups() {
        return atomGroups;
    }

    public ArrayList<Line> getLineGroups() {
        return lineGroups;
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
        private Positions position;

        public enum Positions {
            Center,
            Bottom,
            Top,
            Left,
            Right,
            TopLeft,
            TopRight,
            BottomLeft,
            BottomRight
        }

        AtomPlaceCard(Atom atom, int x, int y, Positions position) {
            atomSymbol = atom.getSymbol();
            this.x = x;
            this.y = y;
            this.position = position;
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
