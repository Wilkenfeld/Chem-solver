package com.enrico.drawing.graphicalAtoms.binding.doublebinding;

import com.enrico.drawing.graphicalAtoms.binding.GenericGraphicalBinding;

public final class DoubleGraphicalBinding extends GenericGraphicalBinding {
    // Left line.
    private int startXL;
    private int endXL;

    private int startYL;
    private int endYL;

    // Right line.
    private int startXR;
    private int endXR;

    private int startYR;
    private int endYR;

    public DoubleGraphicalBinding(int startXL, int endXL, int startYL, int endYL, int startXR,
                                  int endXR, int startYR, int endYR) {
        super("DOUBLE_BINDING_");

        // Left line.
        this.startXL = startXL;
        this.endXL = endXL;

        this.startYL = startYL;
        this.endYL = endYL;

        // Right line.
        this.startXR = startXR;
        this.endXR = endXR;

        this.startYR = startYR;
        this.endYR = endYR;
    }

    public int getEndXL() {
        return endXL;
    }

    public int getEndXR() {
        return endXR;
    }

    public int getEndYL() {
        return endYL;
    }

    public int getStartXL() {
        return startXL;
    }

    public int getEndYR() {
        return endYR;
    }

    public int getStartXR() {
        return startXR;
    }

    public void setEndXL(int endXL) {
        this.endXL = endXL;
    }

    public int getStartYL() {
        return startYL;
    }

    public int getStartYR() {
        return startYR;
    }

    public void setEndXR(int endXR) {
        this.endXR = endXR;
    }

    public void setEndYL(int endYL) {
        this.endYL = endYL;
    }

    public void setEndYR(int endYR) {
        this.endYR = endYR;
    }

    public void setStartXL(int startXL) {
        this.startXL = startXL;
    }

    public void setStartXR(int startXR) {
        this.startXR = startXR;
    }

    public void setStartYL(int startYL) {
        this.startYL = startYL;
    }

    public void setStartYR(int startYR) {
        this.startYR = startYR;
    }
}
