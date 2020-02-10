package com.enrico.widgets.canvas.moleculedrawingcanvas;

import com.enrico.drawing.graphicalAtoms.GenericGraphicalAtom;
import com.enrico.drawing.graphicalAtoms.GraphicalCarbonAtom;
import com.enrico.drawing.graphicalAtoms.binding.GraphicalBinding;
import com.enrico.widgets.canvas.GenericCanvas;
import com.enrico.widgets.menu.popupmenu.MoleculeDrawingPopupMenu;

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

    private CursorStates cursorState;

    private ArrayList<GenericGraphicalAtom> graphicalAtomsList = new ArrayList<>();
    private ArrayList<GraphicalBinding> graphicalBindingList = new ArrayList<>();

    private GenericGraphicalAtom lastSelectedAtom;

    private String currentAtomSymbol;

    private int atomsInserted = 0;

    private int lastXClick;
    private int lastYClick;

    public enum CursorStates {
        CursorSelecting,    // Normal arrow.
        CursorDrawing,      // Circle.
        CursorSingleBinding // Bold circle.
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

        setCursor(drawingCursor);
        cursorState = CursorStates.CursorDrawing;

        currentAtomSymbol = "";

        addMouseListener(new MouseListenerImpl());
    }

    public void setCurrentAtomSymbol(String currentAtomSymbol) {
        if (cursorState == CursorStates.CursorSelecting)
            return;

        this.currentAtomSymbol = currentAtomSymbol;
    }

    public void setCursorState(CursorStates state) {
        cursorState = state;
    }

    public GenericGraphicalAtom getGraphicalAtomFromCoordinates(int x, int y) {
        for(GenericGraphicalAtom indexAtom : graphicalAtomsList) {
            if ((x >= indexAtom.getStartX() && x <= indexAtom.getEndX()) &&
                (y >= indexAtom.getStartY() && y <= indexAtom.getEndY()))
                return indexAtom;
        }
        return null;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (graphicalBindingList.size() > 0) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(GraphicalBinding.DEFAULT_COLOR);
            g2d.setStroke(GraphicalBinding.DEFAULT_STROKE); // Set thickness of the line.

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

            lastXClick = e.getX();
            lastYClick = e.getY();

            switch (cursorState) {
                case CursorDrawing:
                    addNewAtom(e.getX(), e.getY());
                    repaint();
                break;

                case CursorSelecting:
                    for (GenericGraphicalAtom atom : graphicalAtomsList) {
                        if ((e.getX() >= atom.getStartX() && e.getX() <= atom.getEndX()) &&
                                (e.getY() >= atom.getStartY() && e.getY() <= atom.getEndY())) {
                            if (SwingUtilities.isRightMouseButton(e)) {
                                lastSelectedAtom = atom;
                                generatePopupMenuForAtom(atom);
                            }
                        }
                    }
                break;

                case CursorSingleBinding:
                    GenericGraphicalAtom originAtom = lastSelectedAtom;
                    GenericGraphicalAtom selectedAtom = null;

                    // Get the destination atom.
                    for (GenericGraphicalAtom atom : graphicalAtomsList) {
                        if ((e.getX() >= atom.getStartX() && e.getX() <= atom.getEndX()) &&
                                (e.getY() >= atom.getStartY() && e.getY() <= atom.getEndY())) {
                            selectedAtom = atom;
                        }
                    }
                    if (selectedAtom == null) {
                        String msg = "No atom was selected for binding.";
                        JOptionPane.showMessageDialog(null, msg, "No atom selected", JOptionPane.ERROR_MESSAGE);
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

                    graphicalBindingList.add(new GraphicalBinding(selectedAtom.getCenterX(), lastSelectedAtom.getCenterX(),
                                                                  selectedAtom.getCenterY(), lastSelectedAtom.getCenterY()));

                    originAtom.doBinding();
                    selectedAtom.doBinding();

                    System.out.println(originAtom.getAtomId() + " --- " + originAtom.getBindingsRemaining());
                    System.out.println(selectedAtom.getAtomId() + " --- " + selectedAtom.getBindingsRemaining());

                    setCursor(Cursor.getDefaultCursor());
                    cursorState = CursorStates.CursorSelecting;

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
    }

    private final class MouseMotionListenerImpl extends MouseMotionAdapter {

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {

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
        MoleculeDrawingPopupMenu popupMenu = new MoleculeDrawingPopupMenu(atom, this);
        popupMenu.show(this, atom.getStartX(), atom.getStartY());
    }
}
