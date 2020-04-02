package com.enrico.windows.dialogs.spinnerdialog.numbers;

import javax.swing.*;
import java.awt.event.*;

public class SpinnerDialogInteger extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel mainDialogPanel;
    private JLabel txtLbl;

    private JSpinner spinner;
    private JSpinner.NumberEditor sizeSpinner; // This widgets is not present in the graphical file.
    private SpinnerNumberModel spinnerNumberModel;

    private int selectedValue;

    public static final int START_VALUE_IS_MINIMUM = -1;
    public static final int STANDARD_STEP = 1;

    public SpinnerDialogInteger(int start, int minVal, int maxVal, int step, String title, String lblText) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle(title);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        if (minVal == START_VALUE_IS_MINIMUM)
            spinnerNumberModel = new SpinnerNumberModel(start, start, maxVal, step);
        else
            spinnerNumberModel = new SpinnerNumberModel(start, minVal, maxVal, step);

        spinner.setModel(spinnerNumberModel);
        sizeSpinner = new JSpinner.NumberEditor(spinner);
        spinner.setEditor(sizeSpinner);

        txtLbl.setText(lblText);
    }

    private void onOK() {
        selectedValue = (Integer) spinner.getValue();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void showDialog() {
        pack();
        setVisible(true);
    }

    public int getSelectedValue() {
        return selectedValue;
    }
}
