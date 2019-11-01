package com.enrico;

import com.enrico.windows.dialogs.ProblemChooserDialog;
import com.enrico.windows.main.MainWindow;
import com.enrico.windows.main.problems.chemistry.MolecularShapeProblemWindow;

/**
 * Nella funzione main volgio fare un ciclo che controlla in continuazione quali finestre ci sono  e se sono
 * state attivate.
 *
 * Pseudo codice:
 *
 * Avvia finenstra principale
 *
 * finestraAttiva = false
 * statusFinestra = "NO FINESTRA"
 * avviaFinestraMain = true
 *
 * while avviato
 *
 *  if statusFinestra != "NO_FINETSRA"
 *      statusFinestra = "NO FINESTRA"
 *      finestraAvviata = false
 *      avviaFinestraMain = true
 *
 *  if avviaFinestraMain && status == "NO FINESTRA"
 *      avvia finestra main
 *      avviaFinestraMain = false
 *
 *  if codice_finestra_1 && !finestraAttiva && statusFinestra == "NO FINESTRA"
 *      finestraAttiva = true
 *      statusFinestra = avvvia finestra 1
 *  else if codice_finestra_2 && !finestraAttiva && statusFinestra == "NO FINESTRA"
 *      ...
 *      ...
 *
 *  // quando la finestra secondara (non principale) si chiude, visto che se non si preme sulla X ma si fa "chiudi
 *  problema" allora la finestra si chiude e basta
 */
public final class Main {

    public static void main(String[] args) {

        final String NO_WINDOW_STATUS = "NO_WINDOW";

        boolean activeMainWindow = false;
        int times_window_open = 0;
        String statusWindow = NO_WINDOW_STATUS;

        while (true) {

            if (statusWindow.equals(NO_WINDOW_STATUS) && !activeMainWindow) {
                activeMainWindow = true;
            }

            if (activeMainWindow) {
                MainWindow mainWindow = new MainWindow();
                statusWindow = mainWindow.showWindow();
                activeMainWindow = false;
            }

            if (statusWindow.equals(MolecularShapeProblemWindow.MOLECULAR_RETURN_STATUS)) {
                MolecularShapeProblemWindow window = new MolecularShapeProblemWindow();
                statusWindow = window.showWindow();
                activeMainWindow = true;
                times_window_open++;
            }

            if ((statusWindow.equals(NO_WINDOW_STATUS) && times_window_open == 0) || statusWindow.equals(ProblemChooserDialog.NO_PROBLEM_CHOOSED))
                System.exit(0);

            System.out.println(statusWindow);
        }
        /*
        MainWindow mainWindow = new MainWindow();
        String problem = mainWindow.showWindow();

        MolecularShapeProblemWindow window = new MolecularShapeProblemWindow();
        window.showWindow();*/
    }
}
