package com.enrico.widgets.buttons;

import javax.swing.*;
import java.awt.*;

/**
 * Main button used in the program.
 */
public class DefaultButton extends JButton {

    private final Color defaultBackgroundColor = new Color(0xC0C0C0);

    public DefaultButton(String text) {
        super(text);
    }

    public void createUIComponents() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g.create();

        graphics2D.setPaint(new GradientPaint(
                            new Point(0, 0), defaultBackgroundColor,
                            new Point(0, getHeight()), defaultBackgroundColor));

        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        graphics2D.setPaint(Color.BLACK);
        graphics2D.drawString(getText(), getWidth() / 2 - (getWidth() / 2 / 2) - 15, getHeight() / 2 + 5);
        graphics2D.dispose();
    }
}
