package com.enrico.widgets.canvas.moleculedrawingcanvas;

import com.enrico.drawing.graphicalAtoms.GenericGraphicalAtom;
import com.enrico.widgets.canvas.GenericCanvas;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public final class MoleculeDrawingCanvas extends GenericCanvas implements MouseListener {
    private final Cursor drawingCursor;

    private ArrayList<GenericGraphicalAtom> graphicalAtomsList = new ArrayList<>();

    public MoleculeDrawingCanvas() {
        super();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImageRaw = toolkit.getImage(getClass().getClassLoader().getResource("cursor_assets/molecule_builder_insert_cursor.png"));
        Image cursorImage = cursorImageRaw.getScaledInstance(45, 45, 0);

        drawingCursor = toolkit.createCustomCursor(cursorImage, new Point(1, 1), "cursor_image");
        setCursor(drawingCursor);
    }

    public void addGraphicalAtom(GenericGraphicalAtom atom) {
        graphicalAtomsList.add(atom);
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
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }

    public void createUIComponents() {
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        //System.out.println(currentAtom.getClass());
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        setCursor(drawingCursor);
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        // Set the default cursor.
        setCursor(Cursor.getDefaultCursor());
    }
}
