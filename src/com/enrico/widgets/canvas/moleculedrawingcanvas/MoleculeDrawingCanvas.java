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

import com.enrico.drawing.graphicalAtoms.GenericGraphicalAtom;
import com.enrico.drawing.graphicalAtoms.GraphicalCarbonAtom;
import com.enrico.drawing.graphicalAtoms.binding.GenericGraphicalBindingList;
import com.enrico.drawing.graphicalAtoms.binding.doublebinding.DoubleGraphicalBinding;
import com.enrico.drawing.graphicalAtoms.binding.singlebinding.SingleGraphicalBinding;
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
import java.util.ArrayList;
import java.util.Objects;

public final class MoleculeDrawingCanvas extends GenericCanvas {
    private final Cursor drawingCursor;
    private final Cursor singleBindingCursor;
    private final Cursor movingCursor;
    private final Cursor removeSingleBindingCursor;
    private final Cursor doubleBindingCursor;
    private final Cursor removeDoubleBindingCursor;

    private CursorStates cursorState;

    private ArrayList<GenericGraphicalAtom> graphicalAtomsList = new ArrayList<>();
    private ArrayList<SingleGraphicalBinding> singleGraphicalBindingList = new ArrayList<>();
    private ArrayList<DoubleGraphicalBinding> doubleGraphicalBindingList = new ArrayList<>();

    private GenericGraphicalAtom lastSelectedAtom;

    private String currentAtomSymbol;

    private int atomsInserted = 0;

    public enum CursorStates {
        CursorSelecting,            // Normal arrow.
        CursorDrawing,              // Circle.
        CursorSingleBinding,        // Bold circle.
        CursorDoubleBinding,        // Double bold circle.
        CursorRemoveSingleBinding,  // Removing the single binding.
        CursorRemoveDoubleBinding,  // Removing the double binding.
        CursorMoving                // Normal hand.
    }

    public MoleculeDrawingCanvas() {
        super();

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Image cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_insert_cursor.png"));
        Image cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        drawingCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_drawing_image");

        cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_single_binding.png"));
        cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        singleBindingCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_binding_image");

        cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_remove_bindings_cursor.png"));
        cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        removeSingleBindingCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_binding_remove_image");

        cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_double_binding.png"));
        cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        doubleBindingCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_double_binding_image");

        cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_remove_bindings_cursor.png"));
        cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        removeDoubleBindingCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_binding_remove_image");

        movingCursor = new Cursor(Cursor.MOVE_CURSOR);

        setCursor(drawingCursor);
        cursorState = CursorStates.CursorDrawing;

        currentAtomSymbol = "";

        addMouseListener(new MouseListenerImpl());
        addMouseMotionListener(new MouseMotionAdapterImpl());
    }

    public void setCurrentAtomSymbol(String currentAtomSymbol) {
        if (cursorState == CursorStates.CursorSelecting)
            return;

        this.currentAtomSymbol = currentAtomSymbol;
    }

    public void setCursorState(CursorStates state) {
        cursorState = state;
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

            for (SingleGraphicalBinding binding : singleGraphicalBindingList) {
                g.drawLine(binding.getStartX(), binding.getStartY(), binding.getEndX(), binding.getEndY());
            }
        }

        // Draw double bindings.
        if (doubleGraphicalBindingList.size() > 0) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(SingleGraphicalBinding.DEFAULT_COLOR);
            g2d.setStroke(DoubleGraphicalBinding.DEFAULT_STROKE);

            sanitizeBindings();

            for (DoubleGraphicalBinding binding : doubleGraphicalBindingList) {
                g.drawLine(binding.getStartXL(), binding.getStartYL(), binding.getEndXL(), binding.getEndYL());
                g.drawLine(binding.getStartXR(), binding.getStartYR(), binding.getEndXR(), binding.getEndYR());
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

                atom.reload(); // Reloading the atom so the bindings will be moving automatically.

                g.drawImage(image, atom.getStartX(), atom.getStartY(), 50, 50, null);
            }
        }
    }

    public void createUIComponents() {
    }

    private final class MouseListenerImpl extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            for (GenericGraphicalAtom atom : graphicalAtomsList) {
                if ((e.getX() >= atom.getSelectableStartX() && e.getX() <= atom.getSelectableEndX()) &&
                        (e.getY() >= atom.getSelectableStartY() && e.getY() <= atom.getSelectableEndY())) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        lastSelectedAtom = atom;
                    }
                }
            }

            switch (cursorState) {
                case CursorDrawing:
                    addNewAtom(e.getX(), e.getY());
                    repaint();
                break;

                case CursorSelecting:
                    GenericGraphicalAtom atom = getGenericGraphicalAtom(e.getX(), e.getY());
                    generatePopupMenuForAtom(atom);
                break;

                case CursorSingleBinding:
                    GenericGraphicalAtom originAtom = lastSelectedAtom;
                    GenericGraphicalAtom selectedAtom = getGenericGraphicalAtom(e.getX(), e.getY());

                    if (selectedAtom == null) {
                        String msg = "No atom was selected for binding.";
                        JOptionPane.showMessageDialog(null, msg, "No atom selected", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Checking if these two atoms have already been binded.
                    if (lastSelectedAtom.hasAtomCommonBindings(selectedAtom)) {
                        String msg = "These two atoms have already been binded.";
                        JOptionPane.showMessageDialog(null, msg, "Atoms already binded", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Check if it's still possible to make bindings.
                    if (originAtom.getBindingsRemaining() == 0) {
                        String msg = "Maximum number of bindings for atom " + originAtom.getAtomId() + " has been reached.";
                        JOptionPane.showMessageDialog(null, msg, "Maximum number of atoms reached.", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (selectedAtom.getBindingsRemaining() == 0) {
                        String msg = "Maximum number of bindings for the selected atom has been reached.";
                        JOptionPane.showMessageDialog(null, msg, "Maximum number of atoms reached.", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    SingleGraphicalBinding binding = new SingleGraphicalBinding(selectedAtom.getCenterX(), lastSelectedAtom.getCenterX(),
                                                                    selectedAtom.getCenterY(), lastSelectedAtom.getCenterY());

                    singleGraphicalBindingList.add(binding);

                    originAtom.doSingleBinding(binding, GenericGraphicalBindingList.Edges.Start);
                    selectedAtom.doSingleBinding(binding, GenericGraphicalBindingList.Edges.End);

                    setCursor(Cursor.getDefaultCursor());
                    cursorState = CursorStates.CursorSelecting;

                    repaint();
                break;

                case CursorDoubleBinding:
                    originAtom = lastSelectedAtom;
                    selectedAtom = getGenericGraphicalAtom(e.getX(), e.getY());

                    if (selectedAtom == null)
                        return;

                    DoubleGraphicalBinding doubleBinding = new DoubleGraphicalBinding(originAtom.getCenterX() - 10,
                                                                                       selectedAtom.getCenterX() - 10,
                                                                                      originAtom.getCenterY(),
                                                                                       selectedAtom.getCenterY(),

                                                                                    originAtom.getCenterX() + 10,
                                                                                     selectedAtom.getCenterX() + 10,
                                                                                     originAtom.getCenterY(),
                                                                                     selectedAtom.getCenterY());

                    doubleGraphicalBindingList.add(doubleBinding);

                    originAtom.doDoubleBinding(doubleBinding, GenericGraphicalBindingList.Edges.Start);
                    selectedAtom.doDoubleBinding(doubleBinding, GenericGraphicalBindingList.Edges.End);

                    setCursor(Cursor.getDefaultCursor());
                    cursorState = CursorStates.CursorSelecting;

                    repaint();
                break;

                case CursorRemoveSingleBinding:
                    GenericGraphicalAtom secondAtom = getGenericGraphicalAtom(e.getX(), e.getY());
                    if (secondAtom == null) {
                        String msg = "No atom selected";
                        JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    for (SingleGraphicalBinding bind : singleGraphicalBindingList) {
                        if (lastSelectedAtom.hasAtomBinding(bind.getID()) && secondAtom.hasAtomBinding(bind.getID())) {
                            bind.markDeletion();
                            lastSelectedAtom.removeSingleBinding(bind.getID());
                            secondAtom.hasAtomBinding(bind.getID());
                        }
                    }

                    setCursorState(CursorStates.CursorSelecting);
                    setCursor(Cursor.getDefaultCursor());

                    repaint();
                break;

                case CursorRemoveDoubleBinding:
                    secondAtom = getGenericGraphicalAtom(e.getX(), e.getY());
                    if (secondAtom == null) {
                        String msg = "No atom selected";
                        JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    for (DoubleGraphicalBinding bind : doubleGraphicalBindingList) {
                        if (lastSelectedAtom.hasAtomBinding(bind.getID()) && secondAtom.hasAtomBinding(bind.getID())) {
                            bind.markDeletion();
                            lastSelectedAtom.removeDoubleBinding(bind.getID());

                            setCursorState(CursorStates.CursorSelecting);
                            setCursor(Cursor.getDefaultCursor());

                            repaint();
                        }
                    }
                break;

                case CursorMoving:
                break;

                default:
                    String msg = "Cursor mode: " + cursorState + "(" + cursorState.toString() + ") is not defined.";
                    JOptionPane.showMessageDialog(null, msg, "Unknown cursor mode", JOptionPane.ERROR_MESSAGE);
                break;
            }
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

                case CursorMoving:
                break;

                default:
                    String msg = "Cursor mode: " + cursorState + "(" + cursorState.toString() + ") is not defined.";
                    JOptionPane.showMessageDialog(null, msg, "Unknown cursor mode", JOptionPane.ERROR_MESSAGE);
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

            if (lastSelectedAtom == null || cursorState != CursorStates.CursorMoving)
                return;

            // Not overlapping two atoms when moving, so we check if in the released position there's an atom.
            GenericGraphicalAtom releasedPositionAtom = getGenericGraphicalAtom(e.getX(), e.getY());
            if (releasedPositionAtom != null && releasedPositionAtom != lastSelectedAtom) {
                setCursorState(CursorStates.CursorSelecting);
                setCursor(Cursor.getDefaultCursor());
                return;
            }

            lastSelectedAtom.move(e.getX(), e.getY());

            setCursorState(CursorStates.CursorSelecting);
            setCursor(Cursor.getDefaultCursor());

            if (lastSelectedAtom.getDoubleBindingList() != null)
                checkDoubleBindings(lastSelectedAtom);

            lastSelectedAtom = null;

            repaint();
        }
    }

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

    private void addNewAtom(int x, int y) {
        if (currentAtomSymbol.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select an atom.", "No atom selected", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (currentAtomSymbol) {
            case GraphicalCarbonAtom.ATOM_SYMBOL:
                graphicalAtomsList.add(new GraphicalCarbonAtom(x, y, x + 45, y + 45, "ATOM_" + atomsInserted));
            break;
        }

        atomsInserted++;
    }

    private void generatePopupMenuForAtom(GenericGraphicalAtom atom) {
        GraphicalAtomPopupMenu popupMenu = new GraphicalAtomPopupMenu(atom, this);
        popupMenu.show(this, atom.getStartX(), atom.getStartY());
    }

    /* This function cleans up the list of the bindings between atoms, so that there won't be any bindings with only
     * one atom at one edge, or any binding without any atoms.
     */
    private void sanitizeBindings() {
        @SuppressWarnings("unchecked")
        ArrayList<SingleGraphicalBinding> singleGraphicalBindingListClone = (ArrayList<SingleGraphicalBinding>) singleGraphicalBindingList.clone();

        @SuppressWarnings("unchecked")
        ArrayList<DoubleGraphicalBinding> doubleGraphicalBindingListClone = (ArrayList<DoubleGraphicalBinding>) doubleGraphicalBindingList.clone();
        int index = 0;

        // Sanitizing single bindings.
        for (SingleGraphicalBinding binding : singleGraphicalBindingListClone) {
            if (binding.getNumberOfAtomsBinded() == 0)
                singleGraphicalBindingList.remove(index);

            index++;
        }

        // Sanitizing double bindings.
        index = 0;
        for (DoubleGraphicalBinding binding : doubleGraphicalBindingListClone) {
            if (binding.getNumberOfAtomsBinded() == 0)
                doubleGraphicalBindingList.remove(index);

            index++;
        }

        for (GenericGraphicalAtom atom : graphicalAtomsList)
            if (atom.getDoubleBindingList() != null)
                checkDoubleBindings(atom);
    }

    @Nullable
    private GenericGraphicalAtom getGenericGraphicalAtom(int x, int y) {
        final int errorMargin = 40;

        for (GenericGraphicalAtom atom : graphicalAtomsList) {
            if ((x >= atom.getStartX() - errorMargin && x <= atom.getEndX() + errorMargin) &&
                (y >= atom.getStartY() - errorMargin && y <= atom.getEndY() + errorMargin)) {
                return atom;
            }
        }

        return null;
    }

    private void checkDoubleBindings(@NotNull GenericGraphicalAtom atom) {
        GenericGraphicalAtom bindedAtom = null;

        DoubleGraphicalBinding atomBinding = null;
        DoubleGraphicalBinding bindedAtomBinding = null;

        for (GenericGraphicalAtom graphicalAtom : graphicalAtomsList) {
            for (DoubleGraphicalBinding binding : atom.getDoubleBindingList().getBindings()) {
                if (graphicalAtom == atom)
                    continue;

                if (graphicalAtom.hasAtomBinding(binding.getID())) {
                    bindedAtom = graphicalAtom;
                    atomBinding = atom.getDoubleGraphicalBindingFromID(binding.getID());
                    bindedAtomBinding = bindedAtom.getDoubleGraphicalBindingFromID(binding.getID());
                    break;
                }
            }
        }

        if (bindedAtom == null || atomBinding == null || bindedAtomBinding == null)
            return;

        if (bindedAtom.getCenterX() <= bindedAtom.getCenterY()) {
            if (atom.getDoubleGraphicalBindingEdge(atomBinding.getID()) == GenericGraphicalBindingList.Edges.Start) {
                atomBinding.setStartYL(atom.getCenterX() + 10);
                atomBinding.setStartYR(atom.getCenterX() - 10);
                atomBinding.setStartXL(atom.getCenterY() + 10);
                atomBinding.setStartXR(atom.getCenterY() - 10);

                atomBinding.setEndYL(bindedAtom.getCenterY() + 10);
                atomBinding.setEndYR(bindedAtom.getCenterY() - 10);
                atomBinding.setEndXL(bindedAtom.getCenterX() + 10);
                atomBinding.setEndXR(bindedAtom.getCenterX() - 10);
            } else {
                atomBinding.setStartYL(bindedAtom.getCenterY() + 10);
                atomBinding.setStartYR(bindedAtom.getCenterY() - 10);
                atomBinding.setStartXL(bindedAtom.getCenterX() + 10);
                atomBinding.setStartXR(bindedAtom.getCenterX() - 10);

                atomBinding.setEndYL(atom.getCenterY() + 10);
                atomBinding.setEndYR(atom.getCenterY() - 10);
                atomBinding.setEndXL(atom.getCenterX() + 10);
                atomBinding.setEndXR(atom.getCenterX() - 10);
            }
        } else {
            if (atom.getDoubleGraphicalBindingEdge(atomBinding.getID()) == GenericGraphicalBindingList.Edges.Start) {
                atomBinding.setStartYL(atom.getCenterY() - 10);
                atomBinding.setStartYR(atom.getCenterY() + 10);
                atomBinding.setStartXL(atom.getCenterX() - 10);
                atomBinding.setStartXR(atom.getCenterX() + 10);

                atomBinding.setEndYL(bindedAtom.getCenterY() - 10);
                atomBinding.setEndYR(bindedAtom.getCenterY() + 10);
                atomBinding.setEndXL(bindedAtom.getCenterX() - 10);
                atomBinding.setEndXR(bindedAtom.getCenterX() + 10);
            } else {
                atomBinding.setStartYL(bindedAtom.getCenterY() - 10);
                atomBinding.setStartYR(bindedAtom.getCenterY() + 10);
                atomBinding.setStartXL(bindedAtom.getCenterX() - 10);
                atomBinding.setStartXR(bindedAtom.getCenterX() + 10);

                atomBinding.setEndYL(atom.getCenterY() - 10);
                atomBinding.setEndYR(atom.getCenterY() + 10);
                atomBinding.setEndXL(atom.getCenterX() - 10);
                atomBinding.setEndXR(atom.getCenterX() + 10);
            }
        }
    }
}
