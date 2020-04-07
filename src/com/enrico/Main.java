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

package com.enrico;

import com.enrico.programresources.messagebundle.ProgramMessageBundle;
import com.enrico.project.loader.GenericProjectLoader;
import com.enrico.project.loader.MolecularShapeProjectLoader;
import com.enrico.windows.main.MainWindow;
import com.enrico.windows.main.problems.chemistry.molecularshape.MolecularShapeProblemWindow;

import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;

import javax.swing.*;
import java.util.Properties;

public final class Main {
    public static void main(String[] args) {
        // Initializing the language bundle.
        ProgramMessageBundle.init();

        try {
            // Raising exception if the Aluminium look and feel is not found.
            Class.forName("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");

            // Modifying the properties to not show anything on the side of a menu.
            Properties props = new Properties();
            props.put("logoString", "");
            AluminiumLookAndFeel.setCurrentTheme(props);

            // Applying the look and feel to the GUI.
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        } catch (Exception ignored) {
        }

        if (args.length == 1) {

            String filePath = args[0];

            try {
                String projectType = GenericProjectLoader.getProjectTypeUninitialized(filePath);

                switch (projectType) {
                    case MolecularShapeProblemWindow.PROJECT_ID:
                        MolecularShapeProjectLoader loader = new MolecularShapeProjectLoader(filePath);
                        loader.initializeDocument();
                        loader.parseProject();
                        String formula = loader.getFormula();

                        System.out.println(formula);

                        MolecularShapeProblemWindow window = new MolecularShapeProblemWindow();
                        window.showWindow();
                        window.setFormulaOnTextField(formula);
                        window.solveProblem();
                    break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MainWindow window = new MainWindow();
            window.showWindow();
        }
    }
}
