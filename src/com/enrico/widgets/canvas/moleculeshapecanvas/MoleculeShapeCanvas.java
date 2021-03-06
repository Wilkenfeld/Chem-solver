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

package com.enrico.widgets.canvas.moleculeshapecanvas;

import com.enrico.chemistry.atoms.scientific.GenericScientificAtom;
import com.enrico.chemistry.molecule.Molecule;
import com.enrico.chemistry.molecule.atomgroup.AtomGroup;
import com.enrico.chemistry.molecule.shapedmolecule.ShapedMolecule;
import com.enrico.drawing.Line;
import com.enrico.widgets.canvas.GenericCanvas;

import java.awt.*;
import java.util.ArrayList;

public final class MoleculeShapeCanvas extends GenericCanvas {
    private GenericScientificAtom[] GenericScientificAtomList = null;
    private GenericScientificAtom centralGenericScientificAtom = null;
    private Molecule molecule = null;

    public MoleculeShapeCanvas() {
        super();

        setPreferredSize(new Dimension(100, 100));
        setBackground(new Color(0xC0C0C0));
        setOpaque(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int centerWidth = getWidth() / 2;
        int centerHeight = getHeight() / 2;

        if (GenericScientificAtomList != null && centralGenericScientificAtom != null && molecule != null) {
            ShapedMolecule shapedMolecule = new ShapedMolecule(molecule, centerWidth, centerHeight);
            ArrayList<AtomGroup> atoms = shapedMolecule.getAtomGroups();
            ArrayList<Line> lines = shapedMolecule.getLineGroups();

            for (AtomGroup atom : atoms) {
                ArrayList<ShapedMolecule.AtomPlaceCard> atomList = atom.getAtoms();
                for (ShapedMolecule.AtomPlaceCard atomListElement: atomList) {
                    g.drawString(atomListElement.getAtomSymbol(), atomListElement.getX(), atomListElement.getY());
                }

                for (Line line: lines) {
                    g.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
                }
            }
        }

    }

    public void createUIComponents() {
    }

    public void setGenericScientificAtomList(GenericScientificAtom[] GenericScientificAtomList) {
        this.GenericScientificAtomList = GenericScientificAtomList;
    }

    public void setCentralGenericScientificAtom(GenericScientificAtom centralGenericScientificAtom) {
        this.centralGenericScientificAtom = centralGenericScientificAtom;
    }

    public void setMolecule(Molecule molecule) {
        this.molecule = molecule;
    }

}
