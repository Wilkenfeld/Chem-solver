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
     public void saveProject() throws ParserConfigurationException, TransformerException, FileNotFoundException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        fileDocument = builder.newDocument();

        rootElement = fileDocument.createElement(ROOT_ELEMENT);
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
