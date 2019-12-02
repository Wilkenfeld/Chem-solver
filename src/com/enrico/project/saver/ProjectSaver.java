package com.enrico.project.saver;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ProjectSaver {
    public static final String CHEM_SOLVER_PROJECT_FILE_EXTENSION = ".cmfp";

    private final String ROOT_ELEMENT = "project";
    private HashMap<String, String> tagsAndValues;

    private final String projectPath;
    private final String fileName;
    private final File projectFile;

    private Document fileDocument;
    private Element rootElement;

    private static int tagsAndValuesPos = 0;

    public ProjectSaver(HashMap<String, String> tagsAndValues, String projectPath,
                        boolean overwritePreviousFile) throws IOException, OverwriteException {
        this.tagsAndValues = tagsAndValues;
        this.projectPath = projectPath;

        fileName = projectPath + CHEM_SOLVER_PROJECT_FILE_EXTENSION;
        projectFile = new File(fileName);

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

    private Node makeNode() {
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
