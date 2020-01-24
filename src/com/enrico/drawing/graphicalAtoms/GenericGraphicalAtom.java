package com.enrico.drawing.graphicalAtoms;

import com.enrico.chemistry.atoms.GenericAtom;

public abstract class GenericGraphicalAtom extends GenericAtom {
    protected int x;
    protected int y;

    protected final String imagePath;

    public GenericGraphicalAtom(String symbol, String completeName, int atomicNumber, double atomicMass, double electronegativity,
                                int bindingElectronsNumber, int doublets, int ionizationEnergy, AtomClassType classType,
                                int xPos, int yPos, String imagePath) {
        super(symbol, completeName, atomicNumber, atomicMass, electronegativity, bindingElectronsNumber, doublets, ionizationEnergy, classType);

        x = xPos;
        y = yPos;
        this.imagePath = imagePath;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
