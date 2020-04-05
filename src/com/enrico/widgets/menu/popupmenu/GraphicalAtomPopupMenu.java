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

package com.enrico.widgets.menu.popupmenu;

import com.enrico.drawing.graphicalAtoms.GenericGraphicalAtom;
import com.enrico.widgets.canvas.moleculedrawingcanvas.MoleculeDrawingCanvas;
import com.enrico.windows.dialogs.graphicalatomproperties.GraphicalAtomPropertiesDialog;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class GraphicalAtomPopupMenu extends GenericPopupMenu {
    public GraphicalAtomPopupMenu(@NotNull GenericGraphicalAtom atom, MoleculeDrawingCanvas canvas) {
        super("Atom: " + atom.getAtomId());

        // Primary actions.
        JMenuItem propertiesItem = new JMenuItem("Properties");
        propertiesItem.addActionListener(actionEvent -> {
            GraphicalAtomPropertiesDialog dialog = new GraphicalAtomPropertiesDialog(atom);
            dialog.showDialog();
        });

        JMenuItem singleBondItem = new JMenuItem("Single bond to");
        singleBondItem.addActionListener(actionEvent -> {
            canvas.setCursorState(MoleculeDrawingCanvas.CursorStates.CursorSingleBond);
        });

        JMenuItem removeSingleBondItem = new JMenuItem("Remove single bond");
        removeSingleBondItem.addActionListener(actionEvent -> {
            canvas.setCursorState(MoleculeDrawingCanvas.CursorStates.CursorRemoveSingleBond);
        });

        JMenuItem removeAtomItem = new JMenuItem("Remove atom");
        removeAtomItem.addActionListener(actionEvent -> {
            canvas.removeAtom(atom);
        });

        add(propertiesItem);
        add(singleBondItem);
        add(removeSingleBondItem);
        add(removeAtomItem);

        // Secondary actions.
        if (atom.getDoubleBondList() != null) {
            JMenuItem doubleBondItem = new JMenuItem("Double bond to");
            doubleBondItem.addActionListener(actionEvent -> {
                canvas.setCursorState(MoleculeDrawingCanvas.CursorStates.CursorDoubleBond);
            });

            JMenuItem removeDoubleBondItem = new JMenuItem("Remove double bond");
            removeDoubleBondItem.addActionListener(actionEvent -> {
                canvas.setCursorState(MoleculeDrawingCanvas.CursorStates.CursorRemoveDoubleBond);
            });

            add(doubleBondItem);
            add(removeDoubleBondItem);
        }

        if (atom.getTripleBondList() != null) {
            JMenuItem tripleBondItem = new JMenuItem("Triple bond to");
            tripleBondItem.addActionListener(actionEvent -> {
                canvas.setCursorState(MoleculeDrawingCanvas.CursorStates.CursorTripleBond);
            });

            JMenuItem removeTripleBondItem = new JMenuItem("Remove triple bond");
            removeTripleBondItem.addActionListener(actionEvent -> {
                canvas.setCursorState(MoleculeDrawingCanvas.CursorStates.CursorRemoveTripleBond);
            });

            add(tripleBondItem);
            add(removeTripleBondItem);
        }
    }
}
