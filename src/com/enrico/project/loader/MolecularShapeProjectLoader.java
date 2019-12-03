package com.enrico.project.loader;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileNotFoundException;

public final class MolecularShapeProjectLoader extends GenericProjectLoader {
    private String formula;

    public MolecularShapeProjectLoader(String filePath) throws FileNotFoundException {
        super(filePath);
    }

    @Override
    public void parseProject() {
        NodeList nodeList = xmlDocument.getElementsByTagName("formula");

        Node node = nodeList.item(0);
        formula = node.getTextContent();
    }

    public String getFormula() {
        return formula;
    }
}
