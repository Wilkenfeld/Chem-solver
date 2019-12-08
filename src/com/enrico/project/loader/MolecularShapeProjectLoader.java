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
