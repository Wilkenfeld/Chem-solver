package com.enrico.drawing.graphicalAtoms.binding;

import java.awt.*;

public abstract class GenericGraphicalBinding {
    public static final Color DEFAULT_COLOR = Color.black;
    public static final BasicStroke DEFAULT_STROKE = new BasicStroke(5);

    private final String ID;

    private static int idCount = 0;

    private int atomsBinded = 2;

    public GenericGraphicalBinding(String prefix) {
        ID = prefix + idCount;
        idCount++;
    }

    public final String getID() {
        return ID;
    }

    public final int getNumberOfAtomsBinded() {
        return atomsBinded;
    }

    public final void markDeletion() {
        atomsBinded = 0;
    }
}
