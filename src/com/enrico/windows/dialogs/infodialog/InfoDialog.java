package com.enrico.windows.dialogs.infodialog;

import com.enrico.programresources.FontResources;
import com.enrico.widgets.buttons.DefaultButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InfoDialog extends JDialog {
    private JPanel contentPane;
    private DefaultButton buttonOK;
    private JLabel infoLbl;
    private JLabel titleLbl;

    public InfoDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        titleLbl.setFont(FontResources.titleFont);
        infoLbl.setFont(FontResources.normalTextFont);

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setSize(new Dimension(500, 350));
        setResizable(false);
        setTitle("About ChemSolver");
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void showDialog() {
        setVisible(true);
        pack();
    }

    public static void main(String[] args) {
        InfoDialog dialog = new InfoDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        buttonOK = new DefaultButton("OK");
    }
}
