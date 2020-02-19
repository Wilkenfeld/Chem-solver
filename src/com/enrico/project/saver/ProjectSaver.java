/*
 * Chem solver. A multi-platform chemistry and physics problem solver.
 *  Copyright (C) 2019 - 2020  Giacalone Enrico
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

package com.enrico.project.saver;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class ProjectSaver {
    public static final String CHEM_SOLVER_PROJECT_FILE_EXTENSION = ".cmfp";
    public static final String PROJECT_TYPE_ID = "project_type";

    final String ROOT_ELEMENT = "project";
    private HashMap<String, String> tagsAndValues;

    final String fileName;

    Document fileDocument;
    Element rootElement;

    private static int tagsAndValuesPos = 0;

    public ProjectSaver(HashMap<String, String> tagsAndValues, String projectPath,
                        boolean overwritePreviousFile) throws IOException, OverwriteException {
        this.tagsAndValues = tagsAndValues;

        fileName = projectPath + CHEM_SOLVER_PROJECT_FILE_EXTENSION;
        File projectFile = new File(fileName);

        if (!projectFile.exists()) {
            projectFile.createNewFile();
        } else {
            if (overwritePreviousFile) {
                if (!projectFile.delete())
                    throw new IOException("Error: cannot delete file: " + projectFile.getAbsolutePath());
            } else {
                throw new OverwriteException(projectFile);
            }
        }
    }

    public abstract void saveProject(String projectType) throws ParserConfigurationException, TransformerException, FileNotFoundException;

     final Node makeNode() {
        Element node;

        String tag = null, val = null;
        int i = 0;

        for (Map.Entry<String, String> entry : tagsAndValues.entrySet()) {
            tag = entry.getKey();
            val = entry .getValue();

            if (i == tagsAndValuesPos) {
                tagsAndValuesPos++;
                break;
            }
        }

        if (tag != null && val != null) {
            node = fileDocument.createElement(tag);
            node.appendChild(fileDocument.createTextNode(val));
            return node;
        }

        return null;
    }
}
