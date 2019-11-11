package com.enrico.chemistry.atoms;

import com.enrico.annotations.chemistry.UnusableAtom;

public abstract class Atom {
    private final String symbol;
    private final String completeName;

    private final int atomicNumber;
    private final double atomicMass;
    private final double electronegativity;
    private final int doublets;
    private final int bindingElectronsNumber;
    private final int ionizationEnergy;

    private final AtomClassType classType;

    public enum AtomClassType {
        AlkalineMetals,
        AlkalineEarthMetals,
        TransitionalMetals,
        NotMetals,
        SemiMetals,
        PBlockMetals,
        Halogens,
        NobleGasses,
        NotClassified
    }

    public enum BindingEnum {
        PureCovalent,
        PolarCovalent,
        Ionic
    }

    public Atom(String symbol, String completeName, int atomicNumber, double atomicMass, double electronegativity,
                int bindingElectronsNumber, int doublets, int ionizationEnergy, AtomClassType classType) {
        this.symbol = symbol;
        this.completeName = completeName;
        this.atomicNumber = atomicNumber;
        this.atomicMass = atomicMass;
        this.electronegativity = electronegativity;
        this.doublets = doublets;
        this.classType = classType;
        this.bindingElectronsNumber = bindingElectronsNumber;
        this.ionizationEnergy = ionizationEnergy;
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }

    public double getAtomicMass() {
        return atomicMass;
    }

    public double getElectronegativity() {
        return electronegativity;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompleteName() {
        return completeName;
    }

    public AtomClassType getClassType() {
        return classType;
    }

    public int getDoublets() {
        return doublets;
    }

    public int getBindingElectronsNumber() {
        return bindingElectronsNumber;
    }

    public int getIonizationEnergy() {
        return ionizationEnergy;
    }

    public static double getElectronegativityDifference(Atom atom1, Atom atom2) {
        double atomElectroNegativity1 = atom1.getElectronegativity();
        double atomElectroNegativity2 = atom2.getElectronegativity();

        if (atomElectroNegativity1 >= atomElectroNegativity2)
            return atomElectroNegativity1 - atomElectroNegativity2;
        else
            return atomElectroNegativity2 - atomElectroNegativity1;
    }

    /**
     * @param atom1 The first atom to get electronegativity.
     * @param atom2 The second atom to get electronegativity.
     * @return the most electronegative atom.
     *
     * @throws IllegalArgumentException
     */
    public static Atom getMostElectronegativeAtom(Atom atom1, Atom atom2) throws IllegalArgumentException {
        checkIfUsable(atom1);
        checkIfUsable(atom2);

        if (atom1.getElectronegativity() >= atom2.getElectronegativity())
            return atom1;
        else
            return atom2;
    }

    public static BindingEnum getBindingFromAtoms(Atom atom1, Atom atom2) {
        double electroNegativityDifference = getElectronegativityDifference(atom1, atom2);

        if (electroNegativityDifference >= 0 && electroNegativityDifference <= 0.4)
            return BindingEnum.PureCovalent;
        else if (electroNegativityDifference >= 0.4 && electroNegativityDifference <= 1.9)
            return BindingEnum.PolarCovalent;
        else
            return BindingEnum.Ionic;
    }

    public static void checkIfUsable(Atom atom) throws IllegalArgumentException {
        if (atom.getClass().isAnnotationPresent(UnusableAtom.class))
            throw new IllegalArgumentException("Atom " + atom.getCompleteName()
                                               + "(" + atom.getSymbol()+") is not usable and no operations can be" +
                                                 " done with it.");
    }
}
