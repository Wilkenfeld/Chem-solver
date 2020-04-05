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

package com.enrico.chemistry.atoms.scientific;

import com.enrico.annotations.chemistry.UnusableAtom;
import com.enrico.chemistry.atoms.GenericAtom;

public abstract class GenericScientificAtom extends GenericAtom {

    public enum BindingEnum {
        PureCovalent,
        PolarCovalent,
        Ionic
    }

    public GenericScientificAtom(String symbol, String completeName, int atomicNumber, double atomicMass, double electronegativity,
                                 int bondElectronsNumber, int doublets, int ionizationEnergy, AtomClassType classType) {

        super(symbol, completeName, atomicNumber, atomicMass, electronegativity, bondElectronsNumber, doublets, ionizationEnergy, classType);
    }

    public static double getElectronegativityDifference(GenericScientificAtom genericAtom1, GenericScientificAtom genericAtom2) {
        double atomElectroNegativity1 = genericAtom1.getElectronegativity();
        double atomElectroNegativity2 = genericAtom2.getElectronegativity();

        if (atomElectroNegativity1 >= atomElectroNegativity2)
            return atomElectroNegativity1 - atomElectroNegativity2;
        else
            return atomElectroNegativity2 - atomElectroNegativity1;
    }

    /**
     * @param genericAtom1 The first atom to get electronegativity.
     * @param genericAtom2 The second atom to get electronegativity.
     * @return the most electronegative atom.
     *
     * @throws IllegalArgumentException
     */
    public static GenericAtom getMostElectronegativeAtom(GenericScientificAtom genericAtom1, GenericScientificAtom genericAtom2) throws IllegalArgumentException {
        checkIfUsable(genericAtom1);
        checkIfUsable(genericAtom2);

        if (genericAtom1.getElectronegativity() >= genericAtom2.getElectronegativity())
            return genericAtom1;
        else
            return genericAtom2;
    }

    public static BindingEnum getBindingFromAtoms(GenericScientificAtom genericAtom1, GenericScientificAtom genericAtom2) {
        double electroNegativityDifference = getElectronegativityDifference(genericAtom1, genericAtom2);

        if (electroNegativityDifference >= 0 && electroNegativityDifference <= 0.4)
            return BindingEnum.PureCovalent;
        else if (electroNegativityDifference >= 0.4 && electroNegativityDifference <= 1.9)
            return BindingEnum.PolarCovalent;
        else
            return BindingEnum.Ionic;
    }

    public static void checkIfUsable(GenericScientificAtom genericAtom) throws IllegalArgumentException {
        if (genericAtom.getClass().isAnnotationPresent(UnusableAtom.class))
            throw new IllegalArgumentException("Atom " + genericAtom.getCompleteName()
                    + "(" + genericAtom.getSymbol()+") is not usable and no operations can be" +
                    " done with it.");
    }
}