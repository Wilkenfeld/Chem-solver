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

package com.enrico.project.saver;

import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class FormulaShapeProjectSaver extends ProjectSaver {
    public FormulaShapeProjectSaver(HashMap<String, String> tagsAndValues, String projectPath,
                                    boolean overwritePreviousFile) throws IOException, OverwriteException {
        super(tagsAndValues, projectPath, overwritePreviousFile);
    }

     @Override
     public void saveProject(String projectType) throws ParserConfigurationException, TransformerException, FileNotFoundException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        fileDocument = builder.newDocument();

        rootElement = fileDocument.createElement(ROOT_ELEMENT);
        rootElement.setAttribute(PROJECT_TYPE_ID, projectType);
        fileDocument.appendChild(rootElement);

        Node formulaToAdd = makeNode();
        if (formulaToAdd == null)
            throw new ArrayStoreException("Error: an error occurred adding the formula to the file.");

        rootElement.appendChild(formulaToAdd);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        transformer.transform(new DOMSource(fileDocument),
                new StreamResult(new FileOutputStream(fileName)));
    }
}
