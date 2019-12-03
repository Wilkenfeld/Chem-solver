package com.enrico;

import com.enrico.project.loader.GenericProjectLoader;
import com.enrico.project.loader.MolecularShapeProjectLoader;
import com.enrico.windows.main.MainWindow;
import com.enrico.windows.main.problems.chemistry.MolecularShapeProblemWindow;

public final class Main {
    public static void main(String[] args) {
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
