package com.enrico.drawing.graphicalAtoms.binding;

import com.enrico.drawing.Line;

import java.awt.*;

public final class GraphicalBinding extends Line {
    public static final Color DEFAULT_COLOR = Color.black;
    public static final BasicStroke DEFAULT_STROKE = new BasicStroke(5);

    private final String ID;

    private static int id_count = 0;

    public GraphicalBinding(int startX, int endX, int startY, int endY) {
        super(startX, endX, startY, endY);
        ID = "BINDING_" + id_count;
        id_count++;
    }

    public String getID() {
        return ID;
    }
}
