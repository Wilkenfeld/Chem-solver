package com.enrico.widgets.buttons;

import javax.swing.*;
import java.awt.*;

/**
 * Main button used in the program.
 */
public class DefaultButton extends JButton {

    public DefaultButton(String text) {
        super();
        JLabel textLbl = new JLabel("<html><center>" + text + "</center></html>");
        textLbl.setForeground(Color.white);
        add(textLbl);

        Color defaultBackgroundColor = new Color(0x439e43);
        setBackground(defaultBackgroundColor);
    }

    public void createUIComponents() {
    }
}
