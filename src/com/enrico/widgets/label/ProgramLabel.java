package com.enrico.widgets.label;

import com.enrico.programresources.FontResources;

import javax.swing.*;

public class ProgramLabel extends JLabel {
    public ProgramLabel() {
        super();
        setFont(FontResources.normalTextFont);
    }

    public ProgramLabel(String text) {
        super(text);
        setFont(FontResources.normalTextFont);
    }

    public void createUIComponents() {
    }
}
