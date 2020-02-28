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
import com.enrico.drawing.graphicalAtoms.nonmetals.*;
import com.enrico.drawing.graphicalAtoms.binding.GenericGraphicalBindingList;
import com.enrico.drawing.graphicalAtoms.binding.doublebinding.DoubleGraphicalBinding;
import com.enrico.drawing.graphicalAtoms.binding.singlebinding.SingleGraphicalBinding;
import com.enrico.drawing.graphicalAtoms.binding.triplebinding.TripleGraphicalBinding;
import com.enrico.drawing.graphicalAtoms.semimetals.*;
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
import java.util.stream.Stream;

public final class MoleculeDrawingCanvas extends GenericCanvas {
    private final Cursor drawingCursor;
    private final Cursor singleBindingCursor;
    private final Cursor movingCursor;
    private final Cursor removeSingleBindingCursor;
    private final Cursor doubleBindingCursor;
    private final Cursor removeDoubleBindingCursor;
    private final Cursor tripleBindingCursor;
    private final Cursor removeTripleBindingCursor;

    private CursorStates cursorState;

    private ArrayList<GenericGraphicalAtom> graphicalAtomsList = new ArrayList<>();
    private ArrayList<SingleGraphicalBinding> singleGraphicalBindingList = new ArrayList<>();
    private ArrayList<DoubleGraphicalBinding> doubleGraphicalBindingList = new ArrayList<>();
    private ArrayList<TripleGraphicalBinding> tripleGraphicalBindingList = new ArrayList<>();

    private GenericGraphicalAtom lastSelectedAtom;

    private String currentAtomSymbol;

    private int atomsInserted = 0;

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

        cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_triple_binding.png"));
        cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        tripleBindingCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_triple_binding_image");

        cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_remove_bindings_cursor.png"));
        cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);
        removeTripleBindingCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_binding_remove_image");

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

    @SuppressWarnings("unchecked")
    public void removeAtom(@NotNull GenericGraphicalAtom atom) {
        ArrayList<SingleGraphicalBinding> singleBindings = (ArrayList<SingleGraphicalBinding>) atom.getSingleBindingList().getBindings().clone();

        ArrayList<DoubleGraphicalBinding> doubleBindings = null;
        if (atom.getDoubleBindingList()  != null)
            doubleBindings = (ArrayList<DoubleGraphicalBinding>) atom.getDoubleBindingList().getBindings().clone();

        ArrayList<TripleGraphicalBinding> tripleBindings = null;
        if (atom.getTripleBindingList() != null)
            tripleBindings = (ArrayList<TripleGraphicalBinding>) atom.getTripleBindingList().getBindings().clone();

        int index = 0;

        for (GenericGraphicalAtom bindedAtom : graphicalAtomsList) {
            if (singleBindings.size() > 0) {
                for (SingleGraphicalBinding binding : singleBindings) {
                    if (bindedAtom.hasAtomBinding(binding.getID())) {
                        bindedAtom.removeSingleBinding(binding.getID());
                        binding.markDeletion();
                        try {
                            singleGraphicalBindingList.remove(index);
                        } catch (IndexOutOfBoundsException ignored) {
                        }
                    }
                    index++;
                }
            }

            index = 0;
            if (doubleBindings != null && doubleGraphicalBindingList.size() > 0) {
                for (DoubleGraphicalBinding binding : doubleBindings) {
                    if (bindedAtom.hasAtomBinding(binding.getID())) {
                        bindedAtom.removeDoubleBinding(binding.getID());
                        binding.markDeletion();
                        doubleGraphicalBindingList.remove(index);
                    }
                    index++;
                }
            }

            index = 0;
            if (tripleBindings != null && tripleGraphicalBindingList.size() > 0) {
                for (TripleGraphicalBinding binding : tripleBindings) {
                    if (bindedAtom.hasAtomBinding(binding.getID())) {
                        bindedAtom.removeTripleBinding(binding.getID());
                        binding.markDeletion();
                        tripleGraphicalBindingList.remove(index);
                    }
                    index++;
                }
            }
        }

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

            for (SingleGraphicalBinding binding : singleGraphicalBindingList) {
                g.drawLine(binding.getStartX(), binding.getStartY(), binding.getEndX(), binding.getEndY());
            }
        }

        // Draw double bindings.
        if (doubleGraphicalBindingList.size() > 0) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(DoubleGraphicalBinding.DEFAULT_COLOR);
            g2d.setStroke(DoubleGraphicalBinding.DEFAULT_STROKE);

            sanitizeBindings();

            for (DoubleGraphicalBinding binding : doubleGraphicalBindingList) {
                g.drawLine(binding.getStartXL(), binding.getStartYL(), binding.getEndXL(), binding.getEndYL());
                g.drawLine(binding.getStartXR(), binding.getStartYR(), binding.getEndXR(), binding.getEndYR());
            }
        }

        // Draw triple bindings.
        if (tripleGraphicalBindingList.size() > 0) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(TripleGraphicalBinding.DEFAULT_COLOR);
            g2d.setStroke(TripleGraphicalBinding.DEFAULT_STROKE);

            sanitizeBindings();

            for (TripleGraphicalBinding binding : tripleGraphicalBindingList) {
                g.drawLine(binding.getStartCentralX(), binding.getStartCentralY(), binding.getEndCentralX(), binding.getEndCentralY());
                g.drawLine(binding.getStartLeftX(), binding.getStartLeftY(), binding.getEndLeftX(), binding.getEndLeftY());
                g.drawLine(binding.getStartRightX(), binding.getStartRightY(), binding.getEndRightX(), binding.getEndRightY());
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
        private void addAtomEvent(int x, int y) {
            addNewAtom(x, y);
            repaint();
        }

        private void selectAtomEvent(int x, int y) {
            GenericGraphicalAtom atom = getGenericGraphicalAtom(x, y);
            if (atom == null)
                return;

            generatePopupMenuForAtom(atom);
        }

        private void singleBindingEvent(GenericGraphicalAtom lastSelectedAtom, int x, int y) {
            GenericGraphicalAtom selectedAtom = getGenericGraphicalAtom(x, y);

            if (checkIfBindingPossible(selectedAtom, 1))
                return;

            @SuppressWarnings("ConstantConditions")
            SingleGraphicalBinding binding = new SingleGraphicalBinding(selectedAtom.getCenterX(), lastSelectedAtom.getCenterX(),
                                                                        selectedAtom.getCenterY(), lastSelectedAtom.getCenterY());

            singleGraphicalBindingList.add(binding);

            lastSelectedAtom.doSingleBinding(binding, GenericGraphicalBindingList.Edges.Start);
            selectedAtom.doSingleBinding(binding, GenericGraphicalBindingList.Edges.End);

            setCursor(Cursor.getDefaultCursor());
            cursorState = CursorStates.CursorSelecting;

            repaint();
        }

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

        private void tripleBindingEvent(GenericGraphicalAtom lastSelectedAtom, int x, int y) {
            GenericGraphicalAtom selectedAtom = getGenericGraphicalAtom(x, y);

            if (checkIfBindingPossible(selectedAtom, 3))
                return;

            @SuppressWarnings("ConstantConditions")
            TripleGraphicalBinding binding = new TripleGraphicalBinding(lastSelectedAtom.getCenterX(), selectedAtom.getCenterX(),
                                                                        lastSelectedAtom.getCenterY(), selectedAtom.getCenterY(),
                                                                        lastSelectedAtom.getCenterX() + 10, selectedAtom.getCenterX() + 10,
                                                                        lastSelectedAtom.getCenterY(), selectedAtom.getCenterY(),
                                                                        lastSelectedAtom.getCenterX() - 10, selectedAtom.getCenterX() - 10,
                                                                        lastSelectedAtom.getCenterY(), selectedAtom.getCenterY());
            tripleGraphicalBindingList.add(binding);

            lastSelectedAtom.doTripleBinding(binding, GenericGraphicalBindingList.Edges.Start);
            selectedAtom.doTripleBinding(binding, GenericGraphicalBindingList.Edges.End);

            setCursor(Cursor.getDefaultCursor());
            cursorState = CursorStates.CursorSelecting;

            repaint();
        }

        private void singleBindingRemoveEvent(GenericGraphicalAtom lastSelectedAtom, int x, int y) {
            GenericGraphicalAtom secondAtom = getGenericGraphicalAtom(x, y);
            if (secondAtom == null) {
                String msg = "No atom selected";
                JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (secondAtom == lastSelectedAtom) {
                String msg = "You can't unbind an atom from itself.";
                JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Stream<SingleGraphicalBinding> graphicalBindingStream = singleGraphicalBindingList.stream();
            SingleGraphicalBinding binding = graphicalBindingStream.filter(e -> lastSelectedAtom.hasAtomBinding(e.getID()))
                    .filter(e -> secondAtom.hasAtomBinding(e.getID()))
                    .findFirst().orElse(null);

            if (binding == null)
                return;

            binding.markDeletion();
            lastSelectedAtom.removeSingleBinding(binding.getID());
            secondAtom.removeSingleBinding(binding.getID());

            setCursorState(CursorStates.CursorSelecting);
            setCursor(Cursor.getDefaultCursor());

            repaint();
        }

        private void doubleBindingRemoveEvent(int x, int y) {
            GenericGraphicalAtom secondAtom = getGenericGraphicalAtom(x, y);
            if (secondAtom == null) {
                String msg = "No atom selected";
                JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (secondAtom == lastSelectedAtom) {
                String msg = "You can't unbind an atom from itself.";
                JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Stream<DoubleGraphicalBinding> graphicalBindingStream = doubleGraphicalBindingList.stream();
            DoubleGraphicalBinding binding = graphicalBindingStream.filter(e -> lastSelectedAtom.hasAtomBinding(e.getID()))
                    .filter(e -> secondAtom.hasAtomBinding(e.getID()))
                    .findFirst().orElse(null);
            if (binding == null)
                return;

            binding.markDeletion();
            lastSelectedAtom.removeDoubleBinding(binding.getID());
            secondAtom.removeDoubleBinding(binding.getID());

            cursorState = CursorStates.CursorSelecting;
            setCursor(Cursor.getDefaultCursor());

            repaint();
        }

        private void tripleBindingRemoveEvent(int x, int y) {
            GenericGraphicalAtom secondAtom = getGenericGraphicalAtom(x, y);

            if (secondAtom == null) {
                String msg = "No atom selected";
                JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (secondAtom == lastSelectedAtom) {
                String msg = "You can't unbind an atom from itself.";
                JOptionPane.showMessageDialog(null, msg, "Please select a valid atom.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Stream<TripleGraphicalBinding> tripleBindingStream = tripleGraphicalBindingList.stream();
            TripleGraphicalBinding binding = tripleBindingStream.filter(e -> lastSelectedAtom.hasAtomBinding(e.getID()))
                    .filter(e -> secondAtom.hasAtomBinding(e.getID()))
                    .findFirst().orElse(null);
            if (binding == null)
                return;

            binding.markDeletion();
            lastSelectedAtom.removeTripleBinding(binding.getID());
            secondAtom.removeTripleBinding(binding.getID());

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

            lastSelectedAtom.move(x, y);

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

        /* Checks if the atom can perform a certain binding type.
         * Returns true if not possible, false otherwise
         */
        private boolean checkIfBindingPossible(GenericGraphicalAtom selectedAtom, int bindingNum) {
            if (selectedAtom == null) {
                String msg = "No atom was selected for binding.";
                JOptionPane.showMessageDialog(null, msg, "No atom selected", JOptionPane.ERROR_MESSAGE);
                return true;
            }

            if (lastSelectedAtom == null) {
                System.out.println("NULL");
                return true;
            }

            if (selectedAtom == lastSelectedAtom) {
                String msg = "You can't bind an atom to itself.";
                JOptionPane.showMessageDialog(null, msg, "No valid atom selected", JOptionPane.ERROR_MESSAGE);
                return true;
            }

            // Check if it's still possible to make bindings.
            if (lastSelectedAtom.getBindingsRemaining() - bindingNum < 0) {
                String msg = "Maximum number of bindings for atom " + lastSelectedAtom.getAtomId() + " has been reached.";
                JOptionPane.showMessageDialog(null, msg, "Maximum number of atoms reached.", JOptionPane.ERROR_MESSAGE);
                return true;
            }

            if (selectedAtom.getBindingsRemaining() - bindingNum < 0) {
                String msg = "Maximum number of bindings for the selected atom has been reached.";
                JOptionPane.showMessageDialog(null, msg, "Maximum number of atoms reached.", JOptionPane.ERROR_MESSAGE);
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

        GenericGraphicalAtom overlappedAtom = getGenericGraphicalAtom(x, y);
        if (overlappedAtom != null)
            return;

        switch (currentAtomSymbol) {
            // Non metals.
            case GraphicalCarbonAtom.ATOM_SYMBOL:
                graphicalAtomsList.add(new GraphicalCarbonAtom(x, y, x + 45, y + 45, "ATOM_" + atomsInserted));
            break;

            case GraphicalHydrogenAtom.ATOM_SYMBOL:
                graphicalAtomsList.add(new GraphicalHydrogenAtom(x, y, x + 45, y + 45, "ATOM_" + atomsInserted));
            break;

            case GraphicalNitrogenAtom.ATOM_SYMBOL:
                graphicalAtomsList.add(new GraphicalNitrogenAtom(x, y, x + 45, y + 45, "ATOM_" + atomsInserted));
            break;

            case GraphicalOxygenAtom.ATOM_SYMBOL:
                graphicalAtomsList.add(new GraphicalOxygenAtom(x, y, x + 45, y + 45, "ATOM_" + atomsInserted));
            break;

            case GraphicalPhosphorusAtom.ATOM_SYMBOL:
                graphicalAtomsList.add(new GraphicalPhosphorusAtom(x, y, x + 45, y + 45, "ATOM_" + atomsInserted));
            break;

            case GraphicalSeleniumAtom.ATOM_SYMBOL:
                graphicalAtomsList.add(new GraphicalSeleniumAtom(x, y, x + 45, y + 45, "ATOM_" + atomsInserted));
            break;

            case GraphicalSulfurAtom.ATOM_SYMBOL:
                graphicalAtomsList.add(new GraphicalSulfurAtom(x, y, x + 45, y + 45, "ATOM_" + atomsInserted));
            break;

            // Semi metals.
            case GraphicalBoronAtom.ATOM_SYMBOL:
                graphicalAtomsList.add(new GraphicalBoronAtom(x, y, x + 45, y + 45, "ATOM_" + atomsInserted));
            break;

            case GraphicalSiliconAtom.ATOM_SYMBOL:
                graphicalAtomsList.add(new GraphicalSiliconAtom(x, y, x + 45, y + 45, "ATOM_" + atomsInserted));
            break;

            case GraphicalGermaniumAtom.ATOM_SYMBOL:
                graphicalAtomsList.add(new GraphicalGermaniumAtom(x, y, x + 45, y + 45, "ATOM_" + atomsInserted));
            break;

            case GraphicalArsenicAtom.ATOM_SYMBOL:
                graphicalAtomsList.add(new GraphicalArsenicAtom(x, y, x + 45, y + 45, "ATOM_" + atomsInserted));
            break;

            case GraphicalAntimonyAtom.ATOM_SYMBOL:
                graphicalAtomsList.add(new GraphicalAntimonyAtom(x, y, x + 45, y + 45, "ATOM_" + atomsInserted));
            break;

            case GraphicalTelluriumAtom.ATOM_SYMBOL:
                graphicalAtomsList.add(new GraphicalTelluriumAtom(x, y, x + 45, y + 45, "ATOM_" + atomsInserted));
            break;

            case GraphicalPoloniumAtom.ATOM_SYMBOL:
                graphicalAtomsList.add(new GraphicalPoloniumAtom(x, y, x + 45, y + 45, "ATOM_" + atomsInserted));
            break;

            case GraphicalAstatineAtom.ATOM_SYMBOL:
                graphicalAtomsList.add(new GraphicalAstatineAtom(x, y, x + 45, y + 45, "ATOM_" + atomsInserted));
            break;
        }

        atomsInserted++;
    }

    private void generatePopupMenuForAtom(@NotNull GenericGraphicalAtom atom) {
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

        @SuppressWarnings("unchecked")
        ArrayList<TripleGraphicalBinding> tripleGraphicalBindingListClone = (ArrayList<TripleGraphicalBinding>) tripleGraphicalBindingList.clone();
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

        // Sanitizing triple bindings.
        index = 0;
        for (TripleGraphicalBinding binding : tripleGraphicalBindingListClone) {
            if (binding.getNumberOfAtomsBinded() == 0)
                doubleGraphicalBindingList.remove(index);

            index++;
        }

        for (GenericGraphicalAtom atom : graphicalAtomsList)
            if (atom.getDoubleBindingList() != null)
                checkDoubleBindings(atom);
    }

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

        if (atom.getCenterY() < 1) {
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
            atomBinding.setStartYL(atom.getCenterY() - 10);
            atomBinding.setStartYR(atom.getCenterY() + 10);
            atomBinding.setStartXL(atom.getCenterX() - 10);
            atomBinding.setStartXR(atom.getCenterX() + 10);

            atomBinding.setEndYL(bindedAtom.getCenterY() - 10);
            atomBinding.setEndYR(bindedAtom.getCenterY() + 10);
            atomBinding.setEndXL(bindedAtom.getCenterX() - 10);
            atomBinding.setEndXR(bindedAtom.getCenterX() + 10);
        }
    }
}
