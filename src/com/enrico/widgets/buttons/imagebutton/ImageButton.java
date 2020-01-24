package com.enrico.widgets.buttons.imagebutton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;

public class ImageButton extends JPanel {
    private final BufferedImage image;

    private final Callable<Void> btnFunction;

    public ImageButton(URL imagePath, Dimension dimension, Callable<Void> btnFunction) throws IOException {
        super();

        Dimension defaultDimension = new Dimension(40, 40);
        if (dimension != null)
            defaultDimension.setSize(dimension);
        else
            setPreferredSize(defaultDimension);

        image = ImageIO.read(imagePath);

        this.btnFunction = btnFunction;

        addMouseListener(new MouseListenerImpl());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Dimension size = getSize();
        g.drawImage(image, 0, 0, size.width, size.height, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    public void createUIComponents() {
    }

    private final class MouseListenerImpl extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent me) {
            try {
                btnFunction.call();
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "This button's method is null.", "Null method", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Unexpected error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
