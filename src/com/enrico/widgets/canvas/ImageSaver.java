/*
 * Chem solver. A multi-platform chemistry and physics problem solver.
 *  Copyright (C) 2019  Giacalone Enrico
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.enrico.widgets.canvas;

import com.enrico.widgets.canvas.moleculeshapecanvas.MoleculeShapeCanvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class ImageSaver {
    private BufferedImage canvasImage;
    private MoleculeShapeCanvas moleculeShapeCanvas;
    private String completeName;

    public static final int IMAGE_PNG_FORMAT = 0;
    public static final int IMAGE_JPG_FORMAT = 1;

    public ImageSaver(MoleculeShapeCanvas moleculeShapeCanvas) {
        this.moleculeShapeCanvas = moleculeShapeCanvas;
        canvasImage = new BufferedImage(moleculeShapeCanvas.getWidth(), moleculeShapeCanvas.getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    public boolean saveImage(String fileName, int imageFormat) throws IOException {
        String currentFileName;
        Graphics2D graphics2D = canvasImage.createGraphics();
        moleculeShapeCanvas.print(graphics2D);

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
