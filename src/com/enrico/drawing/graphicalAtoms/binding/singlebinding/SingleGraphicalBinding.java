package com.enrico.drawing.graphicalAtoms.binding.singlebinding;

import com.enrico.drawing.graphicalAtoms.binding.GenericGraphicalBinding;

public final class SingleGraphicalBinding extends GenericGraphicalBinding {
    private int startX;
    private int endX;

    private int startY;
    private int endY;

    public SingleGraphicalBinding(int startX, int endX, int startY, int endY) {
        super("SINGLE_BINDING");

        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }
}
