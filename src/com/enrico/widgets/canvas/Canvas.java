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

package com.enrico.widgets.canvas;

import com.enrico.chemistry.atoms.Atom;
import com.enrico.chemistry.molecule.Molecule;
import com.enrico.chemistry.molecule.atomgroup.AtomGroup;
import com.enrico.chemistry.molecule.shapedmolecule.ShapedMolecule;
import com.enrico.drawing.Line;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canvas extends JPanel {
    private Atom[] atomList = null;
    private Atom centralAtom = null;
    private Molecule molecule = null;

    public Canvas() {
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

        if (atomList != null && centralAtom != null && molecule != null) {
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

    public void setAtomList(Atom[] atomList) {
        this.atomList = atomList;
    }

    public void setCentralAtom(Atom centralAtom) {
        this.centralAtom = centralAtom;
    }

    public void setMolecule(Molecule molecule) {
        this.molecule = molecule;
    }

}
