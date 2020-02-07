package com.enrico.windows.dialogs.graphicalatomproperties;

import com.enrico.drawing.graphicalAtoms.GenericGraphicalAtom;
import com.enrico.programresources.FontResources;
import com.enrico.widgets.buttons.ProgramButton;
import com.enrico.widgets.label.ProgramLabel;
import com.enrico.widgets.textfiled.ProgramTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public final class GraphicalAtomPropertiesDialog extends JDialog {
    private JPanel contentPane;
    private ProgramButton buttonOK;
    private ProgramLabel atom_id_lbl;
    private ProgramTextField atom_id_txt_field;
    private ProgramLabel atom_name_lbl;
    private ProgramLabel atom_name_lbl_out;
    private ProgramLabel general_properties_lbl;
    private ProgramLabel electronegativity_lbl;
    private ProgramLabel electronegativity_out_lbl;
    private ProgramLabel mass_lbl;
    private ProgramLabel mass_out_lbl;
    private ProgramLabel atomic_number_lbl;
    private ProgramLabel atomic_number_out_lbl;
    private ProgramLabel atom_class_lbl;
    private ProgramLabel atom_class_out_lbl;

    private GenericGraphicalAtom atom;

    public GraphicalAtomPropertiesDialog(GenericGraphicalAtom atom) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setMinimumSize(new Dimension(500, 300));
        setTitle("Atom properties");
        setResizable(false);

        this.atom = atom;

        atom_id_lbl.setFont(FontResources.normalTextFont);
        atom_id_txt_field.setFont(FontResources.normalTextFont);
        atom_id_txt_field.setText(atom.getAtomId());

        atom_name_lbl.setFont(FontResources.normalTextFont);
        atom_name_lbl_out.setFont(FontResources.normalTextFont);
        atom_name_lbl_out.setText(atom.getCompleteName());

        general_properties_lbl.setFont(FontResources.normalTextFont);

        electronegativity_lbl.setFont(FontResources.normalTextFont);
        electronegativity_out_lbl.setFont(FontResources.normalTextFont);
        electronegativity_out_lbl.setText(String.valueOf(atom.getElectronegativity()));

        mass_lbl.setFont(FontResources.normalTextFont);
        mass_out_lbl.setFont(FontResources.normalTextFont);
        mass_out_lbl.setText(String.valueOf(atom.getAtomicMass()));

        atomic_number_lbl.setFont(FontResources.normalTextFont);
        atomic_number_out_lbl.setFont(FontResources.normalTextFont);
        atomic_number_out_lbl.setText(String.valueOf(atom.getAtomicNumber()));

        atom_class_lbl.setFont(FontResources.normalTextFont);
        atom_class_out_lbl.setFont(FontResources.normalTextFont);
        atom_class_out_lbl.setText(atom.getClassType().toString());

        buttonOK.addActionListener(e -> onOK());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // TODO: change atom ID if possible.
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void createUIComponents() {
        buttonOK = new ProgramButton("OK");
    }

    public void showDialog() {
        setVisible(true);
        pack();
    }
}
