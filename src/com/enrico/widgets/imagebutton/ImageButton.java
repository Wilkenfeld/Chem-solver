package com.enrico.widgets.imagebutton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageButton extends JPanel {
    private final BufferedImage image;

    public ImageButton(URL imagePath, Dimension dimension) throws IOException {
        super();

        Dimension defaultDimension = new Dimension(40, 40);
        if (dimension != null)
            defaultDimension.setSize(dimension);

        setPreferredSize(defaultDimension);

        image = ImageIO.read(imagePath);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Dimension size = getSize();
        g.drawImage(image, 0, 0, size.width, size.height, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    public void createUIComponents() {
    }
}
