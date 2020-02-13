package com.enrico.widgets.canvas.moleculedrawingcanvas;

import com.enrico.drawing.graphicalAtoms.GenericGraphicalAtom;
import com.enrico.drawing.graphicalAtoms.GraphicalCarbonAtom;
import com.enrico.drawing.graphicalAtoms.binding.GraphicalBinding;
import com.enrico.drawing.graphicalAtoms.binding.GraphicalBindingList;
import com.enrico.widgets.canvas.GenericCanvas;
import com.enrico.widgets.menu.popupmenu.GraphicalAtomPopupMenu;

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

    private CursorStates cursorState;

    private ArrayList<GenericGraphicalAtom> graphicalAtomsList = new ArrayList<>();
    private ArrayList<GraphicalBinding> graphicalBindingList = new ArrayList<>();

    private GenericGraphicalAtom lastSelectedAtom;

    private String currentAtomSymbol;

    private int atomsInserted = 0;

    public enum CursorStates {
        CursorSelecting,            // Normal arrow.
        CursorDrawing,              // Circle.
        CursorSingleBinding,        // Bold circle.
        CursorRemoveSingleBinding,  // Removing the single binding.
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

        if (graphicalBindingList.size() > 0) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(GraphicalBinding.DEFAULT_COLOR);
            g2d.setStroke(GraphicalBinding.DEFAULT_STROKE); // Set thickness of the line.

            sanitizeBindings();

            for (GraphicalBinding binding : graphicalBindingList) {
                g.drawLine(binding.getStartX(), binding.getStartY(), binding.getEndX(), binding.getEndY());
            }
        }

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

                    GraphicalBinding binding = new GraphicalBinding(selectedAtom.getCenterX(), lastSelectedAtom.getCenterX(),
                                                                    selectedAtom.getCenterY(), lastSelectedAtom.getCenterY());

                    graphicalBindingList.add(binding);

                    originAtom.doBinding(binding, GraphicalBindingList.Edges.Start);
                    selectedAtom.doBinding(binding, GraphicalBindingList.Edges.End);

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

                    for (GraphicalBinding bind : graphicalBindingList) {
                        if (lastSelectedAtom.hasAtomBinding(bind.getID()) && secondAtom.hasAtomBinding(bind.getID())) {
                            bind.markDeletion();
                            lastSelectedAtom.removeBinding(bind.getID());
                            secondAtom.hasAtomBinding(bind.getID());
                        }
                    }

                    setCursorState(CursorStates.CursorSelecting);
                    setCursor(Cursor.getDefaultCursor());

                    repaint();
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

                case CursorDrawing:
                    setCursor(drawingCursor);
                break;

                case CursorSelecting:
                    setCursor(Cursor.getDefaultCursor());
                break;

                case CursorRemoveSingleBinding:
                    setCursor(removeSingleBindingCursor);
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

            lastSelectedAtom.move(e.getX(), e.getY());

            setCursorState(CursorStates.CursorSelecting);
            setCursor(Cursor.getDefaultCursor());

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
        ArrayList<GraphicalBinding> graphicalBindingListClone = (ArrayList<GraphicalBinding>)graphicalBindingList.clone();
        int index = 0;

        for (GraphicalBinding binding : graphicalBindingListClone) {
            if (binding.getNumberOfAtomsBinded() == 0)
                graphicalBindingList.remove(index);

            index++;
        }
    }

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
}
