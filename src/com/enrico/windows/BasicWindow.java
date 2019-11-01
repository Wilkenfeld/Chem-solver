package com.enrico.windows;

import com.enrico.windows.dialogs.ProblemChooserDialog;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public abstract class BasicWindow extends JDialog {

    protected static final int EXIT_ON_CLOSE_WINDOW = 0;
    protected static final int CLOSE_ON_HIDE_WINDOW = 1;

    protected String retStatus = ProblemChooserDialog.NO_PROBLEM_CHOOSED;

    public BasicWindow(String title, int closeMode) {
        super(null, title, ModalityType.TOOLKIT_MODAL);

        setModal(true);
        setResizable(true);

        if (closeMode == EXIT_ON_CLOSE_WINDOW) {
            addComponentListener(new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent componentEvent) {

                }

                @Override
                public void componentMoved(ComponentEvent componentEvent) {

                }

                @Override
                public void componentShown(ComponentEvent componentEvent) {

                }

                @Override
                public void componentHidden(ComponentEvent componentEvent) {
                    System.exit(0);
                }
            });
        } else {
            addComponentListener(new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent componentEvent) {

                }

                @Override
                public void componentMoved(ComponentEvent componentEvent) {

                }

                @Override
                public void componentShown(ComponentEvent componentEvent) {

                }

                @Override
                public void componentHidden(ComponentEvent componentEvent) {
                    dispose();
                }
            });
        }
    }

    public String showWindow() {
        pack();
        setVisible(true);

        return retStatus;
    }
}
