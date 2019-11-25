package com.enrico.widgets.canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class ImageSaver {
    private BufferedImage canvasImage;
    private Canvas canvas;
    private String completeName;

    public static final int IMAGE_PNG_FORMAT = 0;
    public static final int IMAGE_JPG_FORMAT = 1;

    public ImageSaver(Canvas canvas) {
        this.canvas = canvas;
        canvasImage = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    public boolean saveImage(String fileName, int imageFormat) throws IOException {
        String currentFileName;
        Graphics2D graphics2D = canvasImage.createGraphics();
        canvas.print(graphics2D);

        if (imageFormat == IMAGE_PNG_FORMAT) {
            currentFileName = fileName.concat(".png");
            completeName = currentFileName;

            File file = new File(currentFileName);
            if (!file.createNewFile())
                return false;

            ImageIO.write(canvasImage, "png", file);
        } else {
            currentFileName = fileName.concat(".jpg");
            completeName = currentFileName;

            File file = new File(currentFileName);
            if (!file.createNewFile())
                return false;

            ImageIO.write(canvasImage, "jpg", file);
        }

        return true;
    }

    public String getCompleteName() {
        return completeName;
    }
}
