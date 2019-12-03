package com.enrico.project.loader;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class is made to represent a generic project loader.
 * This project loader will be specialized into every type of problem, so  problem has its own project
 * loader.
 */
public abstract class GenericProjectLoader {
    private final File currentFile;
    private final String fileName;
    private final String filePath;

    private String projectType;

    protected Document xmlDocument;

    public static final String PROJECT_TYPE_ID = "project_type";

    public GenericProjectLoader(String filePath) throws FileNotFoundException {
        currentFile = new File(filePath);
        if (!currentFile.exists()) // The file may not exists if we launch the program by terminal.
            throw new FileNotFoundException("Error: file \"" + filePath + " does not exists.");

        this.filePath = filePath;
        fileName = currentFile.getName();
    }

    public final String getFileName() {
        return fileName;
    }

    public final String getFilePath() {
        return filePath;
    }

    public String getProjectType() {
        return projectType;
    }

    public void initializeDocument() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder =  dbFactory.newDocumentBuilder();

        xmlDocument = dBuilder.parse(currentFile);
        xmlDocument.getDocumentElement().normalize();

        projectType = xmlDocument.getDocumentElement().getAttribute(PROJECT_TYPE_ID);
    }

    public static String getProjectTypeUninitialized(String filePath)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder =  dbFactory.newDocumentBuilder();

        Document xmlDocument = dBuilder.parse(filePath);
        xmlDocument.getDocumentElement().normalize();

        return xmlDocument.getDocumentElement().getAttribute(PROJECT_TYPE_ID);
    }

    public abstract void parseProject();
}
