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
import com.enrico.drawing.graphicalAtoms.bond.GenericGraphicalBindingList;
import com.enrico.drawing.graphicalAtoms.bond.doublebond.DoubleGraphicalBinding;
import com.enrico.drawing.graphicalAtoms.bond.singlebond.SingleGraphicalBinding;
import com.enrico.drawing.graphicalAtoms.bond.triplebond.TripleGraphicalBinding;
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
    private final Cursor singleBindingCursor;
    private final Cursor movingCursor;
    private final Cursor removeSingleBindingCursor;
    private final Cursor doubleBindingCursor;
    private final Cursor removeDoubleBindingCursor;
    private final Cursor tripleBindingCursor;
    private final Cursor removeTripleBindingCursor;

    // This member keeps track of the type of cursor that we're using.
    private CursorStates cursorState;

    // This lists contains all of the atoms contained inside the canvas.
    private ArrayList<GenericGraphicalAtom> graphicalAtomsList = new ArrayList<>();

    // These lists contain all of the type of bonds inside the canvas.
    private ArrayList<SingleGraphicalBinding> singleGraphicalBindingList = new ArrayList<>();
    private ArrayList<DoubleGraphicalBinding> doubleGraphicalBindingList = new ArrayList<>();
    private ArrayList<TripleGraphicalBinding> tripleGraphicalBindingList = new ArrayList<>();

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
        CursorSingleBinding,        // Bold circle.
        CursorDoubleBinding,        // Double bold circle.
        CursorTripleBinding,        // Triple bold circle.
        CursorRemoveSingleBinding,  // Cross.
        CursorRemoveDoubleBinding,  // Cross.
        CursorRemoveTripleBinding,  // Cross.
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
        singleBindingCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_bond_image");

        cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_remove_bond_cursor.png"));
        cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        removeSingleBindingCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_bond_remove_image");

        cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_double_bond.png"));
        cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        doubleBindingCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_double_bond_image");

        cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_remove_bond_cursor.png"));
        cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        removeDoubleBindingCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_bond_remove_image");

        cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_triple_bond.png"));
        cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        tripleBindingCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_triple_bond_image");

        cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_remove_bond_cursor.png"));
        cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        removeTripleBindingCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_bond_remove_image");

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
        ArrayList<SingleGraphicalBinding> singleBindings = (ArrayList<SingleGraphicalBinding>) atom.getSingleBindingList().getBindings().clone();

        ArrayList<DoubleGraphicalBinding> doubleBindings = null;
        if (atom.getDoubleBindingList()  != null)
            doubleBindings = (ArrayList<DoubleGraphicalBinding>) atom.getDoubleBindingList().getBindings().clone();

        ArrayList<TripleGraphicalBinding> tripleBindings = null;
        if (atom.getTripleBindingList() != null)
            tripleBindings = (ArrayList<TripleGraphicalBinding>) atom.getTripleBindingList().getBindings().clone();

        int index = 0;

        // We try to find this atom and we remove every single bond that we found.
        for (GenericGraphicalAtom bondedAtom : graphicalAtomsList) {
            if (singleBindings.size() > 0) {
                for (SingleGraphicalBinding bond : singleBindings) {
                    if (bondedAtom.hasAtomBinding(bond.getID())) {
                        bondedAtom.removeSingleBinding(bond.getID());
                        bond.markDeletion();
                        try {
                            singleGraphicalBindingList.remove(index);
                        } catch (IndexOutOfBoundsException ignored) {
                        }
                    }
                    index++;
                }
            }

            // We now remove all of the double bonds.
            index = 0;
            if (doubleBindings != null && doubleGraphicalBindingList.size() > 0) {
                for (DoubleGraphicalBinding bond : doubleBindings) {
                    if (bondedAtom.hasAtomBinding(bond.getID())) {
                        bondedAtom.removeDoubleBinding(bond.getID());
                        bond.markDeletion();
                        doubleGraphicalBindingList.remove(index);
                    }
                    index++;
                }
            }

            // We now remove all of the triple bonds.
            index = 0;
            if (tripleBindings != null && tripleGraphicalBindingList.size() > 0) {
                for (TripleGraphicalBinding bond : tripleBindings) {
                    if (bondedAtom.hasAtomBinding(bond.getID())) {
                        bondedAtom.removeTripleBinding(bond.getID());
                        bond.markDeletion();
                        tripleGraphicalBindingList.remove(index);
                    }
                    index++;
                }
            }
        }

        // After having deleted all of the atom bonds from every atom that was bonded to the @atom, we remove the
        // atom itself from the canvas and we repaint everything.
        atom.removeAllBindings();
        graphicalAtomsList.remove(atom);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw single Bindings.
        if (singleGraphicalBindingList.size() > 0) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(SingleGraphicalBinding.DEFAULT_COLOR);
            g2d.setStroke(SingleGraphicalBinding.DEFAULT_STROKE); // Set thickness of the line.

            sanitizeBindings();

            for (SingleGraphicalBinding bond : singleGraphicalBindingList) {
                g.drawLine(bond.getStartX(), bond.getStartY(), bond.getEndX(), bond.getEndY());
            }
        }

        // Draw double bonds.
        if (doubleGraphicalBindingList.size() > 0) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(DoubleGraphicalBinding.DEFAULT_COLOR);
            g2d.setStroke(DoubleGraphicalBinding.DEFAULT_STROKE);

            sanitizeBindings();

            for (DoubleGraphicalBinding bond : doubleGraphicalBindingList) {
                g.drawLine(bond.getStartXL(), bond.getStartYL(), bond.getEndXL(), bond.getEndYL());
                g.drawLine(bond.getStartXR(), bond.getStartYR(), bond.getEndXR(), bond.getEndYR());
            }
        }

        // Draw triple bonds.
        if (tripleGraphicalBindingList.size() > 0) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(TripleGraphicalBinding.DEFAULT_COLOR);
            g2d.setStroke(TripleGraphicalBinding.DEFAULT_STROKE);

            sanitizeBindings();

            for (TripleGraphicalBinding bond : tripleGraphicalBindingList) {
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
        private void singleBindingEvent(GenericGraphicalAtom lastSelectedAtom, int x, int y) {
            GenericGraphicalAtom selectedAtom = getGenericGraphicalAtom(x, y);
            if (selectedAtom == null)
                return;

            if (checkIfBindingPossible(selectedAtom, 1))
                return;

            if (lastSelectedAtom.getClassType() == GenericAtom.AtomClassType.AlkalineMetals ||
                lastSelectedAtom.getClassType() == GenericAtom.AtomClassType.AlkalineEarthMetals ||
                selectedAtom.getClassType() == GenericAtom.AtomClassType.AlkalineMetals ||
                selectedAtom.getClassType() == GenericAtom.AtomClassType.AlkalineEarthMetals) {

                ionicBindingEvent(lastSelectedAtom, selectedAtom);
            } else {
                SingleGraphicalBinding bond = new SingleGraphicalBinding(selectedAtom.getCenterX(), lastSelectedAtom.getCenterX(),
                        selectedAtom.getCenterY(), lastSelectedAtom.getCenterY());

                singleGraphicalBindingList.add(bond);

                lastSelectedAtom.doSingleBinding(bond, GenericGraphicalBindingList.Edges.Start);
                selectedAtom.doSingleBinding(bond, GenericGraphicalBindingList.Edges.End);
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
        private void ionicBindingEvent(@NotNull GenericGraphicalAtom lastSelectedAtom, GenericGraphicalAtom selectedAtom) {
            if (selectedAtom == null)
                return;

            if (lastSelectedAtom.isAtomUpper(selectedAtom)) {
                lastSelectedAtom.move(selectedAtom.getStartX(), selectedAtom.getStartY() + 45);
            } else {
                lastSelectedAtom.move(selectedAtom.getStartX(), selectedAtom.getStartY() - 45);
            }

            lastSelectedAtom.performIonicBinding(selectedAtom);
            selectedAtom.performIonicBinding(lastSelectedAtom);
        }

        /**
         * This method generates a double bond between two atoms.
         * @param lastSelectedAtom the atom that started the bond.
         * @param x the clicked X.
         * @param y the clicked Y.
         */
        private void doubleBindingEvent(GenericGraphicalAtom lastSelectedAtom, int x, int y) {
            GenericGraphicalAtom selectedAtom = getGenericGraphicalAtom(x, y);

            if (checkIfBindingPossible(selectedAtom, 2))
                return;

            @SuppressWarnings("ConstantConditions")
            DoubleGraphicalBinding doubleBinding = new DoubleGraphicalBinding(lastSelectedAtom.getCenterX() - 10,
                                                                              selectedAtom.getCenterX() - 10,
                                                                              lastSelectedAtom.getCenterY(),
                                                                              selectedAtom.getCenterY(),
                                                                              lastSelectedAtom.getCenterX() + 10,
                                                                              selectedAtom.getCenterX() + 10,
                                                                              lastSelectedAtom.getCenterY(),
                                                                              selectedAtom.getCenterY());

            doubleGraphicalBindingList.add(doubleBinding);

            lastSelectedAtom.doDoubleBinding(doubleBinding, GenericGraphicalBindingList.Edges.Start);
            selectedAtom.doDoubleBinding(doubleBinding, GenericGraphicalBindingList.Edges.End);

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
        private void tripleBindingEvent(GenericGraphicalAtom lastSelectedAtom, int x, int y) {
            GenericGraphicalAtom selectedAtom = getGenericGraphicalAtom(x, y);

            if (checkIfBindingPossible(selectedAtom, 3))
                return;

            @SuppressWarnings("ConstantConditions")
            TripleGraphicalBinding bond = new TripleGraphicalBinding(lastSelectedAtom.getCenterX(), selectedAtom.getCenterX(),
                                                                        lastSelectedAtom.getCenterY(), selectedAtom.getCenterY(),
                                                                        lastSelectedAtom.getCenterX() + 10, selectedAtom.getCenterX() + 10,
                                                                        lastSelectedAtom.getCenterY(), selectedAtom.getCenterY(),
                                                                        lastSelectedAtom.getCenterX() - 10, selectedAtom.getCenterX() - 10,
                                                                        lastSelectedAtom.getCenterY(), selectedAtom.getCenterY());
            tripleGraphicalBindingList.add(bond);

            lastSelectedAtom.doTripleBinding(bond, GenericGraphicalBindingList.Edges.Start);
            selectedAtom.doTripleBinding(bond, GenericGraphicalBindingList.Edges.End);

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
        private void singleBindingRemoveEvent(GenericGraphicalAtom lastSelectedAtom, int x, int y) {
            GenericGraphicalAtom secondAtom = getGenericGraphicalAtom(x, y);
            if (secondAtom == null) {
                String msg = "No atom selected";
                JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (lastSelectedAtom.hasIonicBinding() && secondAtom.hasIonicBinding()) {
                ionicBindingRemoveEvent(lastSelectedAtom, secondAtom);
            } else {
                if (secondAtom == lastSelectedAtom) {
                    String msg = "You can't unbond an atom from itself.";
                    JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Stream<SingleGraphicalBinding> graphicalBindingStream = singleGraphicalBindingList.stream();
                SingleGraphicalBinding bond = graphicalBindingStream.filter(e -> lastSelectedAtom.hasAtomBinding(e.getID()))
                        .filter(e -> secondAtom.hasAtomBinding(e.getID()))
                        .findFirst().orElse(null);

                if (bond == null)
                    return;

                bond.markDeletion();
                lastSelectedAtom.removeSingleBinding(bond.getID());
                secondAtom.removeSingleBinding(bond.getID());
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
        private void ionicBindingRemoveEvent(GenericGraphicalAtom lastSelectedAtom, GenericGraphicalAtom secondAtom) {
            if (secondAtom == null) {
                String msg = "No atom selected";
                JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            lastSelectedAtom.removeIonicBinding(secondAtom.getAtomId());
            secondAtom.removeIonicBinding(lastSelectedAtom.getAtomId());
        }

        /**
         * This method removes a double bond from an atom
         * @param x the clicked X
         * @param y the clicked Y
         */
        private void doubleBindingRemoveEvent(int x, int y) {
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

            Stream<DoubleGraphicalBinding> graphicalBindingStream = doubleGraphicalBindingList.stream();
            DoubleGraphicalBinding bond = graphicalBindingStream.filter(e -> lastSelectedAtom.hasAtomBinding(e.getID()))
                    .filter(e -> secondAtom.hasAtomBinding(e.getID()))
                    .findFirst().orElse(null);
            if (bond == null)
                return;

            bond.markDeletion();
            lastSelectedAtom.removeDoubleBinding(bond.getID());
            secondAtom.removeDoubleBinding(bond.getID());

            cursorState = CursorStates.CursorSelecting;
            setCursor(Cursor.getDefaultCursor());

            repaint();
        }

        /**
         * This method removes a triple bond from an atom
         * @param x the clicked X
         * @param y the clicked Y
         */
        private void tripleBindingRemoveEvent(int x, int y) {
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

            Stream<TripleGraphicalBinding> tripleBindingStream = tripleGraphicalBindingList.stream();
            TripleGraphicalBinding bond = tripleBindingStream.filter(e -> lastSelectedAtom.hasAtomBinding(e.getID()))
                    .filter(e -> secondAtom.hasAtomBinding(e.getID()))
                    .findFirst().orElse(null);
            if (bond == null)
                return;

            bond.markDeletion();
            lastSelectedAtom.removeTripleBinding(bond.getID());
            secondAtom.removeTripleBinding(bond.getID());

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

            if (lastSelectedAtom.hasIonicBinding() && releasedPositionAtom == null) {
                if (y - 5 < 5 || y + 5 > getHeight() - 15) {
                    setCursorState(CursorStates.CursorSelecting);
                    setCursor(Cursor.getDefaultCursor());
                    return;
                } else {
                    lastSelectedAtom.move(x, y);
                    lastSelectedAtom.getIonicBindedAtom().move(lastSelectedAtom.getStartX(), lastSelectedAtom.getStartY() + 45);
                }
            } else {
                if (!lastSelectedAtom.hasIonicBinding())
                    lastSelectedAtom.move(x, y);
            }

            setCursorState(CursorStates.CursorSelecting);
            setCursor(Cursor.getDefaultCursor());

            if (lastSelectedAtom.getDoubleBindingList() != null)
                checkDoubleBindings(lastSelectedAtom);

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
        private boolean checkIfBindingPossible(GenericGraphicalAtom selectedAtom, int bondNum) {
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
            if (lastSelectedAtom.getBindingsRemaining() - bondNum < 0) {
                String msg = "Maximum number of bonds for atom " + lastSelectedAtom.getAtomId() + " has been reached.";
                JOptionPane.showMessageDialog(null, msg, "Maximum number of atoms reached.", JOptionPane.ERROR_MESSAGE);
                return true;
            }

            if (selectedAtom.getBindingsRemaining() - bondNum < 0) {
                String msg = "Maximum number of bonds for the selected atom has been reached.";
                JOptionPane.showMessageDialog(null, msg, "Maximum number of atoms reached.", JOptionPane.ERROR_MESSAGE);
                return true;
            }

            if (cursorState == CursorStates.CursorDoubleBinding) {
                if (selectedAtom.getDoubleBindingList() == null) {
                    String msg = "Can't double bond " + lastSelectedAtom.getAtomId() + " to " + selectedAtom.getAtomId();
                    JOptionPane.showMessageDialog(null, msg, "Can't double bond atoms.", JOptionPane.ERROR_MESSAGE);
                    return true;
                }
            }

            if (cursorState == CursorStates.CursorTripleBinding) {
                if (selectedAtom.getTripleBindingList() == null) {
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

                case CursorSingleBinding:
                    singleBindingEvent(lastSelectedAtom, e.getX(), e.getY());
                break;

                case CursorDoubleBinding:
                    doubleBindingEvent(lastSelectedAtom, e.getX(), e.getY());
                break;

                case CursorTripleBinding:
                    tripleBindingEvent(lastSelectedAtom, e.getX(), e.getY());
                break;

                case CursorRemoveSingleBinding:
                    singleBindingRemoveEvent(lastSelectedAtom, e.getX(), e.getY());
                break;

                case CursorRemoveDoubleBinding:
                    doubleBindingRemoveEvent(e.getX(), e.getY());
                break;

                case CursorRemoveTripleBinding:
                    tripleBindingRemoveEvent(e.getX(), e.getY());
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
                case CursorSingleBinding:
                    setCursor(singleBindingCursor);
                break;

                case CursorDoubleBinding:
                    setCursor(doubleBindingCursor);
                break;

                case CursorTripleBinding:
                    setCursor(tripleBindingCursor);
                break;

                case CursorDrawing:
                    setCursor(drawingCursor);
                break;

                case CursorSelecting:
                    setCursor(Cursor.getDefaultCursor());
                break;

                case CursorRemoveSingleBinding:
                    setCursor(removeSingleBindingCursor);
                break;

                case CursorRemoveDoubleBinding:
                    setCursor(removeDoubleBindingCursor);
                break;

                case CursorRemoveTripleBinding:
                    setCursor(removeTripleBindingCursor);
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
    private void sanitizeBindings() {
        @SuppressWarnings("unchecked")
        ArrayList<SingleGraphicalBinding> singleGraphicalBindingListClone = (ArrayList<SingleGraphicalBinding>) singleGraphicalBindingList.clone();

        @SuppressWarnings("unchecked")
        ArrayList<DoubleGraphicalBinding> doubleGraphicalBindingListClone = (ArrayList<DoubleGraphicalBinding>) doubleGraphicalBindingList.clone();

        @SuppressWarnings("unchecked")
        ArrayList<TripleGraphicalBinding> tripleGraphicalBindingListClone = (ArrayList<TripleGraphicalBinding>) tripleGraphicalBindingList.clone();
        int index = 0;

        // Sanitizing single bonds.
        for (SingleGraphicalBinding bond : singleGraphicalBindingListClone) {
            if (bond.getNumberOfAtomsBinded() == 0)
                singleGraphicalBindingList.remove(index);

            index++;
        }

        // Sanitizing double bonds.
        index = 0;
        for (DoubleGraphicalBinding bond : doubleGraphicalBindingListClone) {
            if (bond.getNumberOfAtomsBinded() == 0)
                doubleGraphicalBindingList.remove(index);

            index++;
        }

        // Sanitizing triple bonds.
        index = 0;
        for (TripleGraphicalBinding bond : tripleGraphicalBindingListClone) {
            if (bond.getNumberOfAtomsBinded() == 0)
                doubleGraphicalBindingList.remove(index);

            index++;
        }

        for (GenericGraphicalAtom atom : graphicalAtomsList)
            if (atom.getDoubleBindingList() != null)
                checkDoubleBindings(atom);
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
    private void checkDoubleBindings(@NotNull GenericGraphicalAtom atom) {
        GenericGraphicalAtom bondedAtom = null;

        DoubleGraphicalBinding atomBinding = null;
        DoubleGraphicalBinding bondedAtomBinding = null;

        for (GenericGraphicalAtom graphicalAtom : graphicalAtomsList) {
            for (DoubleGraphicalBinding bond : atom.getDoubleBindingList().getBindings()) {
                if (graphicalAtom == atom)
                    continue;

                if (graphicalAtom.hasAtomBinding(bond.getID())) {
                    bondedAtom = graphicalAtom;
                    atomBinding = atom.getDoubleGraphicalBindingFromID(bond.getID());
                    bondedAtomBinding = bondedAtom.getDoubleGraphicalBindingFromID(bond.getID());
                    break;
                }
            }
        }

        if (bondedAtom == null || atomBinding == null || bondedAtomBinding == null)
            return;

        if (atom.getCenterY() < 1) {
            if (atom.getDoubleGraphicalBindingEdge(atomBinding.getID()) == GenericGraphicalBindingList.Edges.Start) {
                atomBinding.setStartYL(atom.getCenterX() + 10);
                atomBinding.setStartYR(atom.getCenterX() - 10);
                atomBinding.setStartXL(atom.getCenterY() + 10);
                atomBinding.setStartXR(atom.getCenterY() - 10);

                atomBinding.setEndYL(bondedAtom.getCenterY() + 10);
                atomBinding.setEndYR(bondedAtom.getCenterY() - 10);
                atomBinding.setEndXL(bondedAtom.getCenterX() + 10);
                atomBinding.setEndXR(bondedAtom.getCenterX() - 10);
            } else {
                atomBinding.setStartYL(bondedAtom.getCenterY() + 10);
                atomBinding.setStartYR(bondedAtom.getCenterY() - 10);
                atomBinding.setStartXL(bondedAtom.getCenterX() + 10);
                atomBinding.setStartXR(bondedAtom.getCenterX() - 10);

                atomBinding.setEndYL(atom.getCenterY() + 10);
                atomBinding.setEndYR(atom.getCenterY() - 10);
                atomBinding.setEndXL(atom.getCenterX() + 10);
                atomBinding.setEndXR(atom.getCenterX() - 10);
            }
        } else {
            atomBinding.setStartYL(atom.getCenterY() - 10);
            atomBinding.setStartYR(atom.getCenterY() + 10);
            atomBinding.setStartXL(atom.getCenterX() - 10);
            atomBinding.setStartXR(atom.getCenterX() + 10);

            atomBinding.setEndYL(bondedAtom.getCenterY() - 10);
            atomBinding.setEndYR(bondedAtom.getCenterY() + 10);
            atomBinding.setEndXL(bondedAtom.getCenterX() - 10);
            atomBinding.setEndXR(bondedAtom.getCenterX() + 10);
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
