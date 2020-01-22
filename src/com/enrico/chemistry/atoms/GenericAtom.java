/*
 * Chem solver. A multi-platform chemistry and physics problem solver.
 *  Copyright (C) 2019  Giacalone Enrico
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

import com.enrico.chemistry.atoms.scientific.GenericScientificAtom;

public abstract class GenericAtom {

    protected final String symbol;
    protected final String completeName;

    protected final int atomicNumber;
    protected final double atomicMass;
    protected final double electronegativity;
    protected final int doublets;
    protected final int bindingElectronsNumber;
    protected final int ionizationEnergy;

    protected final AtomClassType classType;


    public enum AtomClassType {
        AlkalineMetals,
        AlkalineEarthMetals,
        TransitionalMetals,
        NotMetals,
        SemiMetals,
        PBlockMetals,
        Halogens,
        NobleGasses
    }

    public GenericAtom(String symbol, String completeName, int atomicNumber, double atomicMass, double electronegativity,
                                 int bindingElectronsNumber, int doublets, int ionizationEnergy, GenericScientificAtom.AtomClassType classType) {
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

}
