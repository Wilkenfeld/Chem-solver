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

package com.enrico.programresources.messagebundle;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public final class ProgramMessageBundle {
    // English is omitted because it is the default language.
    private static final Locale[] supportedLanguages = {Locale.ITALY};

    public static ResourceBundle messageBundle = null;

    // To be called ony once after the program is started.
    public static void init() {
        Locale currentLocale = new Locale(Locale.getDefault().getLanguage(), Locale.getDefault().getCountry());
        if (Arrays.asList(supportedLanguages).contains(currentLocale)) {
            messageBundle = ResourceBundle.getBundle("languages/MessageBundle",
                    currentLocale);
        } else {
            messageBundle = ResourceBundle.getBundle("languages/MessageBundle",
                    Locale.ROOT);
        }
    }

    public static final String WELCOME_TITLE = "welcome_title";
    public static final String WELCOME_SUBTITLE = "welcome_subtitle";
    public static final String WELCOME_BTN = "welcome_btn";
}
