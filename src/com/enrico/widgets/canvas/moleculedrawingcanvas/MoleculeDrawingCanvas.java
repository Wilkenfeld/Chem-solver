package com.enrico.widgets.canvas.moleculedrawingcanvas;

import com.enrico.drawing.graphicalAtoms.GenericGraphicalAtom;
import com.enrico.drawing.graphicalAtoms.GraphicalCarbonAtom;
import com.enrico.widgets.canvas.GenericCanvas;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public final class MoleculeDrawingCanvas extends GenericCanvas {
    private final Cursor drawingCursor;
    private CursorStates cursorState;

    private ArrayList<GenericGraphicalAtom> graphicalAtomsList = new ArrayList<>();
    private String currentAtomSymbol;

    public enum CursorStates {
        CursorSelecting, // Normal arrow.
        CursorDrawing    // Circle.
    }

    public MoleculeDrawingCanvas() {
        super();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_insert_cursor.png"));
        Image cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);

        drawingCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_image");
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

                g.drawImage(image, atom.getCenterX(), atom.getCenterY(), 50, 50, null);
            }
        }
    }

    public void createUIComponents() {
    }

    private final class MouseListenerImpl extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (cursorState == CursorStates.CursorSelecting)
                return;

            addNewAtom(e.getX(), e.getY());
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);

            if (cursorState == CursorStates.CursorDrawing)
                setCursor(drawingCursor);
            else
                setCursor(Cursor.getDefaultCursor());
        }

        @Override
        public void mouseExited(MouseEvent e) {
            super.mouseExited(e);
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private void addNewAtom(int x, int y) {
        if (currentAtomSymbol.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select an atom.", "No atom selected", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (currentAtomSymbol) {
            case GraphicalCarbonAtom.ATOM_SYMBOL:
                graphicalAtomsList.add(new GraphicalCarbonAtom(x, y, x - 25, y - 25, x + 25, y + 25));
            break;
        }
    }
}
