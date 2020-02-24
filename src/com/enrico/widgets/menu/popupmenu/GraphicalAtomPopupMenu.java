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

        JMenuItem singleBindingItem = new JMenuItem("Single bind to");
        singleBindingItem.addActionListener(actionEvent -> {
            canvas.setCursorState(MoleculeDrawingCanvas.CursorStates.CursorSingleBinding);
        });

        JMenuItem removeSingleBindingItem = new JMenuItem("Remove single binding");
        removeSingleBindingItem.addActionListener(actionEvent -> {
            canvas.setCursorState(MoleculeDrawingCanvas.CursorStates.CursorRemoveSingleBinding);
        });

        add(propertiesItem);
        add(singleBindingItem);
        add(removeSingleBindingItem);

        // Secondary actions.
        if (atom.getDoubleBindingList() != null) {
            JMenuItem doubleBindingItem = new JMenuItem("Double bind to");
            doubleBindingItem.addActionListener(actionEvent -> {
                canvas.setCursorState(MoleculeDrawingCanvas.CursorStates.CursorDoubleBinding);
            });

            JMenuItem removeDoubleBindingItem = new JMenuItem("Remove double binding");
            removeDoubleBindingItem.addActionListener(actionEvent -> {
                canvas.setCursorState(MoleculeDrawingCanvas.CursorStates.CursorRemoveDoubleBinding);
            });

            add(doubleBindingItem);
            add(removeDoubleBindingItem);
        }

        if (atom.getTripleBindingList() != null) {
            JMenuItem tripleBindingItem = new JMenuItem("Triple binding to");
            tripleBindingItem.addActionListener(actionEvent -> {
                canvas.setCursorState(MoleculeDrawingCanvas.CursorStates.CursorTripleBinding);
            });

            JMenuItem removeTripleBindingItem = new JMenuItem("Remove triple binding");
            removeTripleBindingItem.addActionListener(actionEvent -> {
                canvas.setCursorState(MoleculeDrawingCanvas.CursorStates.CursorRemoveTripleBinding);
            });

            add(tripleBindingItem);
            add(removeTripleBindingItem);
        }
    }
}
