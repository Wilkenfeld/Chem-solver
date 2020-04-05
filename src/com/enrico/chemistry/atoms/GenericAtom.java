/*
 * Chem solver. A multi-platform chemistry and physics problem solver.
 *  Copyright (C) 2019 - 2020  Giacalone Enrico
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.enrico.chemistry.atoms;

public abstract class GenericAtom {

    protected final String symbol;
    protected final String completeName;

    protected final int atomicNumber;
    protected final double atomicMass;
    protected final double electronegativity;
    protected final int doublets;
    protected final int bondElectronsNumber;
    protected final int ionizationEnergy;

    protected final AtomClassType classType;

    public enum AtomClassType {
        AlkalineMetals {
            @Override
            public String toString() {
                return "Alkaline metals";
            }
        },
        AlkalineEarthMetals {
            @Override
            public String toString() {
                return "Alkaline earth metals";
            }
        },
        TransitionalMetals {
            @Override
            public String toString() {
                return "Transitional metals";
            }
        },
        NotMetals {
            @Override
            public String toString() {
                return "Non metals";
            }
        },
        SemiMetals  {
            @Override
            public String toString() {
                return "Semi metals";
            }
        },
        PBlockMetals  {
            @Override
            public String toString() {
                return "P-Block metals";
            }
        },
        Halogens {
            @Override
            public String toString() {
                return "Halogens";
            }
        },
        NobleGasses {
            @Override
            public String toString() {
                return "Noble gasses";
            }
        },
        Lanthanides {
            @Override
            public String toString() {
                return "Lanthanides";
            }
        },
        Actinides {
            @Override
            public String toString() {
                return "Actinides";
            }
        }
    }

    public GenericAtom(String symbol, String completeName, int atomicNumber, double atomicMass, double electronegativity,
                                 int bondElectronsNumber, int doublets, int ionizationEnergy, AtomClassType classType) {
        this.symbol = symbol;
        this.completeName = completeName;
        this.atomicNumber = atomicNumber;
        this.atomicMass = atomicMass;
        this.electronegativity = electronegativity;
        this.doublets = doublets;
        this.classType = classType;
        this.bondElectronsNumber = bondElectronsNumber;
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
        return bondElectronsNumber;
    }

    public int getIonizationEnergy() {
        return ionizationEnergy;
    }

    public boolean isMetal() {
        return classType != AtomClassType.Halogens && classType != AtomClassType.NotMetals && classType != AtomClassType.NobleGasses;
    }
}
