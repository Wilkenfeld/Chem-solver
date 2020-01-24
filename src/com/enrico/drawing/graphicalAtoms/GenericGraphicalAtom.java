package com.enrico.drawing.graphicalAtoms;

import com.enrico.chemistry.atoms.GenericAtom;

public abstract class GenericGraphicalAtom extends GenericAtom {
    protected int startX;
    protected int endX;
    protected int startY;
    protected int endY;

    protected final String imagePath;

    public GenericGraphicalAtom(String symbol, String completeName, int atomicNumber, double atomicMass, double electronegativity,
                                int bindingElectronsNumber, int doublets, int ionizationEnergy, AtomClassType classType,
                                int startX, int startY, int endX, int endY, String imagePath) {
        super(symbol, completeName, atomicNumber, atomicMass, electronegativity, bindingElectronsNumber, doublets, ionizationEnergy, classType);

        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;

        this.imagePath = imagePath;
    }

    public int getStartX() {
        return startX;
    }

    public int getEndX() {
        return endX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndY() {
        return endY;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public String getImagePath() {
        return imagePath;
    }
}