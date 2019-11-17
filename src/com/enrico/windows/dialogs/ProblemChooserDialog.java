package com.enrico.windows.dialogs;

import com.enrico.interfaces.FontInterface;
import com.enrico.widgets.buttons.DefaultButton;

import javax.swing.*;
import java.awt.event.*;

public class ProblemChooserDialog extends JDialog implements FontInterface {
    private JPanel contentPane;
    private DefaultButton buttonOK;
    private DefaultButton buttonCancel;
    private JList chemProblemList;
    private JList physProblemList;
    private JLabel chemTitleLbl;
    private JLabel physTitleLbl;

    public static final String NO_PROBLEM_CHOOSED = "NO_PROBLEM_CHOOSED";

    private String choosedProblem = NO_PROBLEM_CHOOSED;

    @SuppressWarnings("unchecked")
    public ProblemChooserDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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

        setTitle("Choose a problem");
        setResizable(false);

        ProblemListModel chemProblemListModel = new ProblemListModel(ProblemListModel.CHEM_PROBLEM_TYPE);
        ProblemListModel physProblemListModel = new ProblemListModel(ProblemListModel.PHYS_PROBLEM_TYPE);

        chemProblemList.setModel(chemProblemListModel);
        physProblemList.setModel(physProblemListModel);

        chemProblemList.setFont(normalTextFont);
        physProblemList.setFont(normalTextFont);

        chemTitleLbl.setFont(normalTextFont);
        physTitleLbl.setFont(normalTextFont);
    }

    private void onOK() {

        if (!chemProblemList.isSelectionEmpty()) {
            choosedProblem = (String) chemProblemList.getSelectedValue();
        } else if (!physProblemList.isSelectionEmpty()) {
            choosedProblem = (String) physProblemList.getSelectedValue();
        }

        dispose();
    }

    private void onCancel() {
        choosedProblem = NO_PROBLEM_CHOOSED;
        dispose();
    }

    public String showDialog() {
        pack();
        setVisible(true);

        return choosedProblem;
    }

    public void createUIComponents() {
        buttonOK = new DefaultButton("OK");
        buttonCancel = new DefaultButton("Cancel");
    }

    public static void main(String[] args) {
        ProblemChooserDialog dialog = new ProblemChooserDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
