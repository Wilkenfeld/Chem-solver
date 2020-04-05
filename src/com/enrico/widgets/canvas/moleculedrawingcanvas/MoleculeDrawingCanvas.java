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

package com.enrico.widgets.canvas.moleculedrawingcanvas;

import com.enrico.chemistry.atoms.GenericAtom;
import com.enrico.drawing.graphicalAtoms.GenericGraphicalAtom;
import com.enrico.drawing.graphicalAtoms.halogens.GraphicalFluorineAtom;
import com.enrico.widgets.canvas.GenericCanvas;
import com.enrico.widgets.menu.popupmenu.GraphicalAtomPopupMenu;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * This class represents a canvas where the user can interact to draw atoms and bond them.
 */
public final class MoleculeDrawingCanvas extends GenericCanvas {
    // These are all of the cursor types that can be used when inside of the canvas.
    private final Cursor drawingCursor;
    private final Cursor singleBondCursor;
    private final Cursor movingCursor;
    private final Cursor removeSingleBondCursor;
    private final Cursor doubleBondCursor;
    private final Cursor removeDoubleBondCursor;
    private final Cursor tripleBondCursor;
    private final Cursor removeTripleBondCursor;

    // This member keeps track of the type of cursor that we're using.
    private CursorStates cursorState;

    // This lists contains all of the atoms contained inside the canvas.
    private ArrayList<GenericGraphicalAtom> graphicalAtomsList = new ArrayList<>();

    // These lists contain all of the type of bonds inside the canvas.
    private ArrayList<com.enrico.drawing.graphicalAtoms.bond.singlebond.SingleGraphicalBond> singleGraphicalBondList = new ArrayList<>();
    private ArrayList<com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBond> doubleGraphicalBondList = new ArrayList<>();
    private ArrayList<com.enrico.drawing.graphicalAtoms.bond.triplebond.TripleGraphicalBond> tripleGraphicalBondList = new ArrayList<>();

    // This member keeps track of the last atom that we clicked.
    private GenericGraphicalAtom lastSelectedAtom;

    // This member contains the class path of the atom that we're currently drawing.
    private String currentClassPath;

    // This member is used only to generate atom IDs.
    private int atomsInserted = 0;

    /**
     * This enum contains all of the possible cursor states to keep track of them
     */
    public enum CursorStates {
        CursorSelecting,            // Normal arrow.
        CursorDrawing,              // Circle.
        CursorSingleBond,        // Bold circle.
        CursorDoubleBond,        // Double bold circle.
        CursorTripleBond,        // Triple bold circle.
        CursorRemoveSingleBond,  // Cross.
        CursorRemoveDoubleBond,  // Cross.
        CursorRemoveTripleBond,  // Cross.
        CursorMoving                // Normal hand.
    }

    /**
     * In this constructor we generate all of the cursor type and we set the predefined (the cursor to draw).
     */
    public MoleculeDrawingCanvas() {
        super();

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Image cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_insert_cursor.png"));
        Image cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        drawingCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_drawing_image");

        cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_single_bond.png"));
        cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        singleBondCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_bond_image");

        cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_remove_bond_cursor.png"));
        cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        removeSingleBondCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_bond_remove_image");

        cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_double_bond.png"));
        cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        doubleBondCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_double_bond_image");

        cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_remove_bond_cursor.png"));
        cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        removeDoubleBondCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_bond_remove_image");

        cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_triple_bond.png"));
        cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        tripleBondCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_triple_bond_image");

        cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_remove_bond_cursor.png"));
        cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        removeTripleBondCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_bond_remove_image");

        movingCursor = new Cursor(Cursor.MOVE_CURSOR);

        setCursor(drawingCursor);
        cursorState = CursorStates.CursorDrawing;

        currentClassPath = "";

        addMouseListener(new MouseListenerImpl());
        addMouseMotionListener(new MouseMotionAdapterImpl());
    }

    /**
     * This method sets the current class path to change the atom to draw, it works only if we're not in the selecting
     * mode.
     * @param currentClassPath the class path of the atom to draw
     */
    public void setCurrentClassPath(String currentClassPath) {
        if (cursorState == CursorStates.CursorSelecting)
            return;

        this.currentClassPath = currentClassPath;
    }

    public void setCursorState(CursorStates state) {
        cursorState = state;
    }

    /**
     * This method removes an atom from the canvas.
     * @param atom the atom to remove.
     */
    @SuppressWarnings("unchecked")
    public void removeAtom(@NotNull GenericGraphicalAtom atom) {
        // First off, we get all of its bonds list.
        ArrayList<com.enrico.drawing.graphicalAtoms.bond.singlebond.SingleGraphicalBond> singleBonds = (ArrayList<com.enrico.drawing.graphicalAtoms.bond.singlebond.SingleGraphicalBond>) atom.getSingleBondList().getBonds().clone();

        ArrayList<com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBond> doubleBonds = null;
        if (atom.getDoubleBondList()  != null)
            doubleBonds = (ArrayList<com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBond>) atom.getDoubleBondList().getBonds().clone();

        ArrayList<com.enrico.drawing.graphicalAtoms.bond.triplebond.TripleGraphicalBond> tripleBonds = null;
        if (atom.getTripleBondList() != null)
            tripleBonds = (ArrayList<com.enrico.drawing.graphicalAtoms.bond.triplebond.TripleGraphicalBond>) atom.getTripleBondList().getBonds().clone();

        int index = 0;

        // We try to find this atom and we remove every single bond that we found.
        for (GenericGraphicalAtom bondedAtom : graphicalAtomsList) {
            if (singleBonds.size() > 0) {
                for (com.enrico.drawing.graphicalAtoms.bond.singlebond.SingleGraphicalBond bond : singleBonds) {
                    if (bondedAtom.hasAtomBond(bond.getID())) {
                        bondedAtom.removeSingleBond(bond.getID());
                        bond.markDeletion();
                        try {
                            singleGraphicalBondList.remove(index);
                        } catch (IndexOutOfBoundsException ignored) {
                        }
                    }
                    index++;
                }
            }

            // We now remove all of the double bonds.
            index = 0;
            if (doubleBonds != null && doubleGraphicalBondList.size() > 0) {
                for (com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBond bond : doubleBonds) {
                    if (bondedAtom.hasAtomBond(bond.getID())) {
                        bondedAtom.removeDoubleBond(bond.getID());
                        bond.markDeletion();
                        doubleGraphicalBondList.remove(index);
                    }
                    index++;
                }
            }

            // We now remove all of the triple bonds.
            index = 0;
            if (tripleBonds != null && tripleGraphicalBondList.size() > 0) {
                for (com.enrico.drawing.graphicalAtoms.bond.triplebond.TripleGraphicalBond bond : tripleBonds) {
                    if (bondedAtom.hasAtomBond(bond.getID())) {
                        bondedAtom.removeTripleBond(bond.getID());
                        bond.markDeletion();
                        tripleGraphicalBondList.remove(index);
                    }
                    index++;
                }
            }
        }

        // After having deleted all of the atom bonds from every atom that was bonded to the @atom, we remove the
        // atom itself from the canvas and we repaint everything.
        atom.removeAllBonds();
        graphicalAtomsList.remove(atom);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw single Bonds.
        if (singleGraphicalBondList.size() > 0) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(com.enrico.drawing.graphicalAtoms.bond.singlebond.SingleGraphicalBond.DEFAULT_COLOR);
            g2d.setStroke(com.enrico.drawing.graphicalAtoms.bond.singlebond.SingleGraphicalBond.DEFAULT_STROKE); // Set thickness of the line.

            sanitizeBonds();

            for (com.enrico.drawing.graphicalAtoms.bond.singlebond.SingleGraphicalBond bond : singleGraphicalBondList) {
                g.drawLine(bond.getStartX(), bond.getStartY(), bond.getEndX(), bond.getEndY());
            }
        }

        // Draw double bonds.
        if (doubleGraphicalBondList.size() > 0) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBond.DEFAULT_COLOR);
            g2d.setStroke(com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBond.DEFAULT_STROKE);

            sanitizeBonds();

            for (com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBond bond : doubleGraphicalBondList) {
                g.drawLine(bond.getStartXL(), bond.getStartYL(), bond.getEndXL(), bond.getEndYL());
                g.drawLine(bond.getStartXR(), bond.getStartYR(), bond.getEndXR(), bond.getEndYR());
            }
        }

        // Draw triple bonds.
        if (tripleGraphicalBondList.size() > 0) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(com.enrico.drawing.graphicalAtoms.bond.triplebond.TripleGraphicalBond.DEFAULT_COLOR);
            g2d.setStroke(com.enrico.drawing.graphicalAtoms.bond.triplebond.TripleGraphicalBond.DEFAULT_STROKE);

            sanitizeBonds();

            for (com.enrico.drawing.graphicalAtoms.bond.triplebond.TripleGraphicalBond bond : tripleGraphicalBondList) {
                g.drawLine(bond.getStartCentralX(), bond.getStartCentralY(), bond.getEndCentralX(), bond.getEndCentralY());
                g.drawLine(bond.getStartLeftX(), bond.getStartLeftY(), bond.getEndLeftX(), bond.getEndLeftY());
                g.drawLine(bond.getStartRightX(), bond.getStartRightY(), bond.getEndRightX(), bond.getEndRightY());
            }
        }

        // Draw atoms.
        BufferedImage image;
        if (graphicalAtomsList.size() > 0) {
            for (GenericGraphicalAtom atom : graphicalAtomsList) {
                try {
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource(atom.getImagePath())));
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(this, ioe.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (image == null) {
                    JOptionPane.showMessageDialog(this, "Can't load atom " + atom.getImagePath() + " image.", "Drawing error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                atom.reload(); // Reloading the atom so the bonds will be moving automatically.

                g.drawImage(image, atom.getStartX(), atom.getStartY(), 50, 50, null);
            }
        }
    }

    public void createUIComponents() {
    }

    /**
     * This internal class handles the mouse inside the canvas.
     */
    private final class MouseListenerImpl extends MouseAdapter {
        /**
         * This method adds the atom to he canvas.
         * @param x the X of the mouse (startX of atom).
         * @param y the Y if the mouse (startY of atom).
         */
        private void addAtomEvent(int x, int y) {
            addNewAtom(x, y);
            repaint();
        }

        /**
         * This method creates the popup menu of the atom to view the actions.
         * @param x the clicked X coordinate.
         * @param y the clicked Y coordinate.
         */
        private void selectAtomEvent(int x, int y) {
            GenericGraphicalAtom atom = getGenericGraphicalAtom(x, y);
            if (atom == null)
                return;

            generatePopupMenuForAtom(atom);
        }

        /**
         * This method generates a single bond between two atoms.
         * @param lastSelectedAtom the atom that started the bond.
         * @param x the clicked X.
         * @param y the clicked Y.
         */
        private void singleBondEvent(GenericGraphicalAtom lastSelectedAtom, int x, int y) {
            GenericGraphicalAtom selectedAtom = getGenericGraphicalAtom(x, y);
            if (selectedAtom == null)
                return;

            if (checkIfBondPossible(selectedAtom, 1))
                return;

            if (lastSelectedAtom.getClassType() == GenericAtom.AtomClassType.AlkalineMetals ||
                lastSelectedAtom.getClassType() == GenericAtom.AtomClassType.AlkalineEarthMetals ||
                selectedAtom.getClassType() == GenericAtom.AtomClassType.AlkalineMetals ||
                selectedAtom.getClassType() == GenericAtom.AtomClassType.AlkalineEarthMetals) {

                ionicBondEvent(lastSelectedAtom, selectedAtom);
            } else {
                com.enrico.drawing.graphicalAtoms.bond.singlebond.SingleGraphicalBond bond = new com.enrico.drawing.graphicalAtoms.bond.singlebond.SingleGraphicalBond(selectedAtom.getCenterX(), lastSelectedAtom.getCenterX(),
                        selectedAtom.getCenterY(), lastSelectedAtom.getCenterY());

                singleGraphicalBondList.add(bond);

                lastSelectedAtom.doSingleBond(bond, com.enrico.drawing.graphicalAtoms.bond.GenericGraphicalBondList.Edges.Start);
                selectedAtom.doSingleBond(bond, com.enrico.drawing.graphicalAtoms.bond.GenericGraphicalBondList.Edges.End);
            }

            setCursor(Cursor.getDefaultCursor());
            cursorState = CursorStates.CursorSelecting;

            repaint();
        }

        /**
         * This method performs an ionic biding on two atoms.
         * @param lastSelectedAtom The atom to bond.
         * @param selectedAtom the other atom to bond.
         */
        private void ionicBondEvent(@NotNull GenericGraphicalAtom lastSelectedAtom, GenericGraphicalAtom selectedAtom) {
            if (selectedAtom == null)
                return;

            if (lastSelectedAtom.isAtomUpper(selectedAtom)) {
                lastSelectedAtom.move(selectedAtom.getStartX(), selectedAtom.getStartY() + 45);
            } else {
                lastSelectedAtom.move(selectedAtom.getStartX(), selectedAtom.getStartY() - 45);
            }

            lastSelectedAtom.performIonicBond(selectedAtom);
            selectedAtom.performIonicBond(lastSelectedAtom);
        }

        /**
         * This method generates a double bond between two atoms.
         * @param lastSelectedAtom the atom that started the bond.
         * @param x the clicked X.
         * @param y the clicked Y.
         */
        private void doubleBondEvent(GenericGraphicalAtom lastSelectedAtom, int x, int y) {
            GenericGraphicalAtom selectedAtom = getGenericGraphicalAtom(x, y);

            if (checkIfBondPossible(selectedAtom, 2))
                return;

            @SuppressWarnings("ConstantConditions")
            com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBond doubleBond = new com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBond(lastSelectedAtom.getCenterX() - 10,
                                                                              selectedAtom.getCenterX() - 10,
                                                                              lastSelectedAtom.getCenterY(),
                                                                              selectedAtom.getCenterY(),
                                                                              lastSelectedAtom.getCenterX() + 10,
                                                                              selectedAtom.getCenterX() + 10,
                                                                              lastSelectedAtom.getCenterY(),
                                                                              selectedAtom.getCenterY());

            doubleGraphicalBondList.add(doubleBond);

            lastSelectedAtom.doDoubleBond(doubleBond, com.enrico.drawing.graphicalAtoms.bond.GenericGraphicalBondList.Edges.Start);
            selectedAtom.doDoubleBond(doubleBond, com.enrico.drawing.graphicalAtoms.bond.GenericGraphicalBondList.Edges.End);

            setCursor(Cursor.getDefaultCursor());
            cursorState = CursorStates.CursorSelecting;

            repaint();
        }

        /**
         * This method generates a triple bond between two atoms.
         * @param lastSelectedAtom the atom that started the bond.
         * @param x the clicked X.
         * @param y the clicked Y.
         */
        private void tripleBondEvent(GenericGraphicalAtom lastSelectedAtom, int x, int y) {
            GenericGraphicalAtom selectedAtom = getGenericGraphicalAtom(x, y);

            if (checkIfBondPossible(selectedAtom, 3))
                return;

            @SuppressWarnings("ConstantConditions")
            com.enrico.drawing.graphicalAtoms.bond.triplebond.TripleGraphicalBond bond = new com.enrico.drawing.graphicalAtoms.bond.triplebond.TripleGraphicalBond(lastSelectedAtom.getCenterX(), selectedAtom.getCenterX(),
                                                                        lastSelectedAtom.getCenterY(), selectedAtom.getCenterY(),
                                                                        lastSelectedAtom.getCenterX() + 10, selectedAtom.getCenterX() + 10,
                                                                        lastSelectedAtom.getCenterY(), selectedAtom.getCenterY(),
                                                                        lastSelectedAtom.getCenterX() - 10, selectedAtom.getCenterX() - 10,
                                                                        lastSelectedAtom.getCenterY(), selectedAtom.getCenterY());
            tripleGraphicalBondList.add(bond);

            lastSelectedAtom.doTripleBond(bond, com.enrico.drawing.graphicalAtoms.bond.GenericGraphicalBondList.Edges.Start);
            selectedAtom.doTripleBond(bond, com.enrico.drawing.graphicalAtoms.bond.GenericGraphicalBondList.Edges.End);

            setCursor(Cursor.getDefaultCursor());
            cursorState = CursorStates.CursorSelecting;

            repaint();
        }

        /**
         * This method removes a single bond from an atom
         * @param lastSelectedAtom the atom that was last selected to remove the bond.
         * @param x the clicked X
         * @param y the clicked Y
         */
        private void singleBondRemoveEvent(GenericGraphicalAtom lastSelectedAtom, int x, int y) {
            GenericGraphicalAtom secondAtom = getGenericGraphicalAtom(x, y);
            if (secondAtom == null) {
                String msg = "No atom selected";
                JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (lastSelectedAtom.hasIonicBond() && secondAtom.hasIonicBond()) {
                ionicBondRemoveEvent(lastSelectedAtom, secondAtom);
            } else {
                if (secondAtom == lastSelectedAtom) {
                    String msg = "You can't unbond an atom from itself.";
                    JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Stream<com.enrico.drawing.graphicalAtoms.bond.singlebond.SingleGraphicalBond> graphicalBondStream = singleGraphicalBondList.stream();
                com.enrico.drawing.graphicalAtoms.bond.singlebond.SingleGraphicalBond bond = graphicalBondStream.filter(e -> lastSelectedAtom.hasAtomBond(e.getID()))
                        .filter(e -> secondAtom.hasAtomBond(e.getID()))
                        .findFirst().orElse(null);

                if (bond == null)
                    return;

                bond.markDeletion();
                lastSelectedAtom.removeSingleBond(bond.getID());
                secondAtom.removeSingleBond(bond.getID());
            }

            setCursorState(CursorStates.CursorSelecting);
            setCursor(Cursor.getDefaultCursor());

            repaint();
        }

        /**
         * This method removes an ionic bond.
         * @param lastSelectedAtom the atom to remove the biding.
         * @param secondAtom the other atom to remove the biding.
         */
        private void ionicBondRemoveEvent(GenericGraphicalAtom lastSelectedAtom, GenericGraphicalAtom secondAtom) {
            if (secondAtom == null) {
                String msg = "No atom selected";
                JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            lastSelectedAtom.removeIonicBond(secondAtom.getAtomId());
            secondAtom.removeIonicBond(lastSelectedAtom.getAtomId());
        }

        /**
         * This method removes a double bond from an atom
         * @param x the clicked X
         * @param y the clicked Y
         */
        private void doubleBondRemoveEvent(int x, int y) {
            GenericGraphicalAtom secondAtom = getGenericGraphicalAtom(x, y);
            if (secondAtom == null) {
                String msg = "No atom selected";
                JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (secondAtom == lastSelectedAtom) {
                String msg = "You can't unbond an atom from itself.";
                JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Stream<com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBond> graphicalBondStream = doubleGraphicalBondList.stream();
            com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBond bond = graphicalBondStream.filter(e -> lastSelectedAtom.hasAtomBond(e.getID()))
                    .filter(e -> secondAtom.hasAtomBond(e.getID()))
                    .findFirst().orElse(null);
            if (bond == null)
                return;

            bond.markDeletion();
            lastSelectedAtom.removeDoubleBond(bond.getID());
            secondAtom.removeDoubleBond(bond.getID());

            cursorState = CursorStates.CursorSelecting;
            setCursor(Cursor.getDefaultCursor());

            repaint();
        }

        /**
         * This method removes a triple bond from an atom
         * @param x the clicked X
         * @param y the clicked Y
         */
        private void tripleBondRemoveEvent(int x, int y) {
            GenericGraphicalAtom secondAtom = getGenericGraphicalAtom(x, y);

            if (secondAtom == null) {
                String msg = "No atom selected";
                JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (secondAtom == lastSelectedAtom) {
                String msg = "You can't unbond an atom from itself.";
                JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Stream<com.enrico.drawing.graphicalAtoms.bond.triplebond.TripleGraphicalBond> tripleBondStream = tripleGraphicalBondList.stream();
            com.enrico.drawing.graphicalAtoms.bond.triplebond.TripleGraphicalBond bond = tripleBondStream.filter(e -> lastSelectedAtom.hasAtomBond(e.getID()))
                    .filter(e -> secondAtom.hasAtomBond(e.getID()))
                    .findFirst().orElse(null);
            if (bond == null)
                return;

            bond.markDeletion();
            lastSelectedAtom.removeTripleBond(bond.getID());
            secondAtom.removeTripleBond(bond.getID());

            cursorState = CursorStates.CursorSelecting;
            setCursor(Cursor.getDefaultCursor());

            repaint();
        }

        private void mouseReleasedEvent(GenericGraphicalAtom lastSelectedAtom, int x, int y) {
            if (lastSelectedAtom == null || cursorState != CursorStates.CursorMoving)
                return;

            // Not overlapping two atoms when moving, so we check if in the released position there's an atom.
            GenericGraphicalAtom releasedPositionAtom = getGenericGraphicalAtom(x, y);
            if (releasedPositionAtom != null && releasedPositionAtom != lastSelectedAtom) {
                setCursorState(CursorStates.CursorSelecting);
                setCursor(Cursor.getDefaultCursor());
                return;
            }

            if (lastSelectedAtom.hasIonicBond() && releasedPositionAtom == null) {
                if (y - 5 < 5 || y + 5 > getHeight() - 15) {
                    setCursorState(CursorStates.CursorSelecting);
                    setCursor(Cursor.getDefaultCursor());
                    return;
                } else {
                    lastSelectedAtom.move(x, y);
                    lastSelectedAtom.getIonicBindedAtom().move(lastSelectedAtom.getStartX(), lastSelectedAtom.getStartY() + 45);
                }
            } else {
                if (!lastSelectedAtom.hasIonicBond())
                    lastSelectedAtom.move(x, y);
            }

            setCursorState(CursorStates.CursorSelecting);
            setCursor(Cursor.getDefaultCursor());

            if (lastSelectedAtom.getDoubleBondList() != null)
                checkDoubleBonds(lastSelectedAtom);

            repaint();
        }

        private void undefinedCursorModeEvent() {
            String msg = "Cursor mode: " + cursorState + "(" + cursorState.toString() + ") is not defined.";
            JOptionPane.showMessageDialog(null, msg, "Unknown cursor mode", JOptionPane.ERROR_MESSAGE);
        }

        /**
         * Checks if the atom can perform a certain bond type.
         * @param selectedAtom the atom to check.
         * @param bondNum the number of bonds on that atom
         * @return returns true if the bond can't be performed, false otherwise.
         */
        private boolean checkIfBondPossible(GenericGraphicalAtom selectedAtom, int bondNum) {
            if (selectedAtom == null) {
                String msg = "No atom was selected for bond.";
                JOptionPane.showMessageDialog(null, msg, "No atom selected", JOptionPane.ERROR_MESSAGE);
                return true;
            }

            if (lastSelectedAtom == null) {
                return true;
            }

            if (selectedAtom == lastSelectedAtom) {
                String msg = "You can't bond an atom to itself.";
                JOptionPane.showMessageDialog(null, msg, "No valid atom selected", JOptionPane.ERROR_MESSAGE);
                return true;
            }

            // Check if it's still possible to make bonds.
            if (lastSelectedAtom.getBondsRemaining() - bondNum < 0) {
                String msg = "Maximum number of bonds for atom " + lastSelectedAtom.getAtomId() + " has been reached.";
                JOptionPane.showMessageDialog(null, msg, "Maximum number of atoms reached.", JOptionPane.ERROR_MESSAGE);
                return true;
            }

            if (selectedAtom.getBondsRemaining() - bondNum < 0) {
                String msg = "Maximum number of bonds for the selected atom has been reached.";
                JOptionPane.showMessageDialog(null, msg, "Maximum number of atoms reached.", JOptionPane.ERROR_MESSAGE);
                return true;
            }

            if (cursorState == CursorStates.CursorDoubleBond) {
                if (selectedAtom.getDoubleBondList() == null) {
                    String msg = "Can't double bond " + lastSelectedAtom.getAtomId() + " to " + selectedAtom.getAtomId();
                    JOptionPane.showMessageDialog(null, msg, "Can't double bond atoms.", JOptionPane.ERROR_MESSAGE);
                    return true;
                }
            }

            if (cursorState == CursorStates.CursorTripleBond) {
                if (selectedAtom.getTripleBondList() == null) {
                    String msg = "Can't triple bond " + lastSelectedAtom.getAtomId() + " to " + selectedAtom.getAtomId();
                    JOptionPane.showMessageDialog(null, msg, "Can't triple bond atoms.", JOptionPane.ERROR_MESSAGE);
                    return true;
                }
            }

            // Check if it's trying to bond two metals.
            if ((selectedAtom.isMetal() && lastSelectedAtom.isMetal())) {
                String msg = "Can't bond two metals.";
                JOptionPane.showMessageDialog(null, msg, msg, JOptionPane.ERROR_MESSAGE);
                return true;
            }

            // Can't bond noble gasses if not to halogens.
            if ((selectedAtom.getClassType() == GenericAtom.AtomClassType.NobleGasses &&
                 !(lastSelectedAtom instanceof GraphicalFluorineAtom)) ||
                 (!(selectedAtom instanceof GraphicalFluorineAtom) &&
                  lastSelectedAtom.getClassType() == GenericAtom.AtomClassType.NobleGasses)) {
                String msg = "Noble gasses can only be bonded to Fluorine.";
                JOptionPane.showMessageDialog(null, msg, msg, JOptionPane.ERROR_MESSAGE);
                return true;
            }

            if (selectedAtom.getClassType() == GenericAtom.AtomClassType.NobleGasses && lastSelectedAtom.getClassType() == GenericAtom.AtomClassType.NobleGasses) {
                String msg = "Can't bond two noble gasses.";
                JOptionPane.showMessageDialog(null, msg, msg, JOptionPane.ERROR_MESSAGE);
                return true;
            }

            return false;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            GenericGraphicalAtom atom = getGenericGraphicalAtom(e.getX(), e.getY());
            if (SwingUtilities.isLeftMouseButton(e) && cursorState == CursorStates.CursorSelecting)
                return;

            switch (cursorState) {
                case CursorDrawing:
                    addAtomEvent(e.getX(), e.getY());
                break;

                case CursorSelecting:
                    selectAtomEvent(e.getX(), e.getY());
                break;

                case CursorSingleBond:
                    singleBondEvent(lastSelectedAtom, e.getX(), e.getY());
                break;

                case CursorDoubleBond:
                    doubleBondEvent(lastSelectedAtom, e.getX(), e.getY());
                break;

                case CursorTripleBond:
                    tripleBondEvent(lastSelectedAtom, e.getX(), e.getY());
                break;

                case CursorRemoveSingleBond:
                    singleBondRemoveEvent(lastSelectedAtom, e.getX(), e.getY());
                break;

                case CursorRemoveDoubleBond:
                    doubleBondRemoveEvent(e.getX(), e.getY());
                break;

                case CursorRemoveTripleBond:
                    tripleBondRemoveEvent(e.getX(), e.getY());
                break;

                case CursorMoving:
                break;

                default:
                    undefinedCursorModeEvent();
                break;
            }

            lastSelectedAtom = atom;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);

            switch (cursorState) {
                case CursorSingleBond:
                    setCursor(singleBondCursor);
                break;

                case CursorDoubleBond:
                    setCursor(doubleBondCursor);
                break;

                case CursorTripleBond:
                    setCursor(tripleBondCursor);
                break;

                case CursorDrawing:
                    setCursor(drawingCursor);
                break;

                case CursorSelecting:
                    setCursor(Cursor.getDefaultCursor());
                break;

                case CursorRemoveSingleBond:
                    setCursor(removeSingleBondCursor);
                break;

                case CursorRemoveDoubleBond:
                    setCursor(removeDoubleBondCursor);
                break;

                case CursorRemoveTripleBond:
                    setCursor(removeTripleBondCursor);
                break;

                case CursorMoving:
                break;

                default:
                    undefinedCursorModeEvent();
                break;
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            super.mouseExited(e);
            setCursor(Cursor.getDefaultCursor());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            mouseReleasedEvent(lastSelectedAtom, e.getX(), e.getY());
        }
    }

    /**
     * This class handles actions that the upper class can't like the movement of an atom.
     */
    private final class MouseMotionAdapterImpl extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);

            if (cursorState != CursorStates.CursorSelecting)
                return;

            GenericGraphicalAtom selectedAtom = getGenericGraphicalAtom(e.getX(), e.getY());

            if (selectedAtom == null)
                return;

            setCursor(movingCursor);
            setCursorState(CursorStates.CursorMoving);

            lastSelectedAtom = selectedAtom;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            super.mouseMoved(e);
        }
    }

    /**
     * This method add a new atom to the canvas.
     * @param x The clicked X (startX of the atom).
     * @param y The clicked Y (startY of the atom).
     */
    private void addNewAtom(int x, int y) {
        if (currentClassPath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select an atom.", "No atom selected", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // We check that the atom des not overlap other atoms.
        GenericGraphicalAtom overlappedAtom = getGenericGraphicalAtom(x, y);
        if (overlappedAtom != null)
            return;

        GenericGraphicalAtom atomToAdd = getAtomFromClassPath(x, y);

        if (atomToAdd != null) {
            graphicalAtomsList.add(atomToAdd);
            atomsInserted++;
        } else {
            JOptionPane.showMessageDialog(this,
                                          "Atom not found.",
                                          "Atom class: " + currentClassPath + " not found." ,
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method generates a popup menu for the atom which contains all of its properties.
     * @param atom The atom to show the properties.
     */
    private void generatePopupMenuForAtom(@NotNull GenericGraphicalAtom atom) {
        GraphicalAtomPopupMenu popupMenu = new GraphicalAtomPopupMenu(atom, this);

        popupMenu.show(this, atom.getStartX(), atom.getStartY());
    }

    /**
     * This method cleans up the list of the bonds between atoms, so that there won't be any bonds with only
     * one atom at one edge, or any bond without any atoms.
     */
    private void sanitizeBonds() {
        @SuppressWarnings("unchecked")
        ArrayList<com.enrico.drawing.graphicalAtoms.bond.singlebond.SingleGraphicalBond> singleGraphicalBondListClone = (ArrayList<com.enrico.drawing.graphicalAtoms.bond.singlebond.SingleGraphicalBond>) singleGraphicalBondList.clone();

        @SuppressWarnings("unchecked")
        ArrayList<com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBond> doubleGraphicalBondListClone = (ArrayList<com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBond>) doubleGraphicalBondList.clone();

        @SuppressWarnings("unchecked")
        ArrayList<com.enrico.drawing.graphicalAtoms.bond.triplebond.TripleGraphicalBond> tripleGraphicalBondListClone = (ArrayList<com.enrico.drawing.graphicalAtoms.bond.triplebond.TripleGraphicalBond>) tripleGraphicalBondList.clone();
        int index = 0;

        // Sanitizing single bonds.
        for (com.enrico.drawing.graphicalAtoms.bond.singlebond.SingleGraphicalBond bond : singleGraphicalBondListClone) {
            if (bond.getNumberOfAtomsBinded() == 0)
                singleGraphicalBondList.remove(index);

            index++;
        }

        // Sanitizing double bonds.
        index = 0;
        for (com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBond bond : doubleGraphicalBondListClone) {
            if (bond.getNumberOfAtomsBinded() == 0)
                doubleGraphicalBondList.remove(index);

            index++;
        }

        // Sanitizing triple bonds.
        index = 0;
        for (com.enrico.drawing.graphicalAtoms.bond.triplebond.TripleGraphicalBond bond : tripleGraphicalBondListClone) {
            if (bond.getNumberOfAtomsBinded() == 0)
                doubleGraphicalBondList.remove(index);

            index++;
        }

        for (GenericGraphicalAtom atom : graphicalAtomsList)
            if (atom.getDoubleBondList() != null)
                checkDoubleBonds(atom);
    }

    /**
     * This method gets a graphical atom from its position
     * @param x The clicked X
     * @param y The clicked Y
     * @return a GraphicalAtom from the list of the canvas if the clicked positions are inside the range of the atom,
     * or null otherwise.
     */
    @Nullable
    private GenericGraphicalAtom getGenericGraphicalAtom(final int x, final int y) {
        final int errorMargin = 40;

        Stream<GenericGraphicalAtom> atomStream = graphicalAtomsList.stream();
        return atomStream.filter(e -> x >= e.getStartX() - errorMargin)
                                              .filter(e -> x <= e.getEndX() + errorMargin)
                                              .filter(e -> y >= e.getStartY() - errorMargin)
                                              .filter(e -> y <= e.getStartY() + errorMargin)
                                              .findFirst().orElse(null);
    }

    /**
     * This method is used to move double bonds.
     * @param atom the atom to update the double bonds with.
     */
    private void checkDoubleBonds(@NotNull GenericGraphicalAtom atom) {
        GenericGraphicalAtom bondedAtom = null;

        com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBond atomBond = null;
        com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBond bondedAtomBond = null;

        for (GenericGraphicalAtom graphicalAtom : graphicalAtomsList) {
            for (com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBond bond : atom.getDoubleBondList().getBonds()) {
                if (graphicalAtom == atom)
                    continue;

                if (graphicalAtom.hasAtomBond(bond.getID())) {
                    bondedAtom = graphicalAtom;
                    atomBond = atom.getDoubleGraphicalBondFromID(bond.getID());
                    bondedAtomBond = bondedAtom.getDoubleGraphicalBondFromID(bond.getID());
                    break;
                }
            }
        }

        if (bondedAtom == null || atomBond == null || bondedAtomBond == null)
            return;

        if (atom.getCenterY() < 1) {
            if (atom.getDoubleGraphicalBondEdge(atomBond.getID()) == com.enrico.drawing.graphicalAtoms.bond.GenericGraphicalBondList.Edges.Start) {
                atomBond.setStartYL(atom.getCenterX() + 10);
                atomBond.setStartYR(atom.getCenterX() - 10);
                atomBond.setStartXL(atom.getCenterY() + 10);
                atomBond.setStartXR(atom.getCenterY() - 10);

                atomBond.setEndYL(bondedAtom.getCenterY() + 10);
                atomBond.setEndYR(bondedAtom.getCenterY() - 10);
                atomBond.setEndXL(bondedAtom.getCenterX() + 10);
                atomBond.setEndXR(bondedAtom.getCenterX() - 10);
            } else {
                atomBond.setStartYL(bondedAtom.getCenterY() + 10);
                atomBond.setStartYR(bondedAtom.getCenterY() - 10);
                atomBond.setStartXL(bondedAtom.getCenterX() + 10);
                atomBond.setStartXR(bondedAtom.getCenterX() - 10);

                atomBond.setEndYL(atom.getCenterY() + 10);
                atomBond.setEndYR(atom.getCenterY() - 10);
                atomBond.setEndXL(atom.getCenterX() + 10);
                atomBond.setEndXR(atom.getCenterX() - 10);
            }
        } else {
            atomBond.setStartYL(atom.getCenterY() - 10);
            atomBond.setStartYR(atom.getCenterY() + 10);
            atomBond.setStartXL(atom.getCenterX() - 10);
            atomBond.setStartXR(atom.getCenterX() + 10);

            atomBond.setEndYL(bondedAtom.getCenterY() - 10);
            atomBond.setEndYR(bondedAtom.getCenterY() + 10);
            atomBond.setEndXL(bondedAtom.getCenterX() - 10);
            atomBond.setEndXR(bondedAtom.getCenterX() + 10);
        }
    }

    /**
     * Using reflection we get the atom by using the class path, we get the constructor
     * and we generate a new instance to add.
     * @param x the startX of the atom.
     * @param y the startY of the atom.
     * @return returns the newly created atom on success, or false in case of error.
     */
    @Nullable
    private GenericGraphicalAtom getAtomFromClassPath(int x, int y) {
        try {
            Class<?> baseClass = Class.forName(currentClassPath);
            Constructor<?> constructor = baseClass.getConstructor(int.class, int.class, int.class, int.class, String.class);
            return (GenericGraphicalAtom) constructor.newInstance(x, y, x + 45, y + 45, "ATOM_" + atomsInserted);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
