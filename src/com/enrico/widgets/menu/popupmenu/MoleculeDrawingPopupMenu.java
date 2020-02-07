package com.enrico.widgets.menu.popupmenu;

import com.enrico.drawing.graphicalAtoms.GenericGraphicalAtom;
import com.enrico.windows.dialogs.graphicalatomproperties.GraphicalAtomPropertiesDialog;

import javax.swing.*;

public final class MoleculeDrawingPopupMenu extends GenericPopupMenu {
    public MoleculeDrawingPopupMenu(GenericGraphicalAtom atom) {
        super("Atom: " + atom.getAtomId());

        JMenuItem propertiesItem = new JMenuItem("Properties");

        propertiesItem.addActionListener(actionEvent -> {
            GraphicalAtomPropertiesDialog dialog = new GraphicalAtomPropertiesDialog(atom);
            dialog.showDialog();
        });

        add(propertiesItem);
    }
}
