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

package com.enrico.interfaces;

import java.awt.*;

/**
 * Interface that contains all the fonts for the program.
 */
public interface FontInterface {
    Font titleFont = new Font(Font.MONOSPACED, Font.PLAIN, 20);
    Font subtitleFont = new Font(Font.MONOSPACED, Font.PLAIN, 15);

    Font normalTextFont = new Font(Font.MONOSPACED, Font.PLAIN, 12);
    Font menuBarFont = new Font(Font.MONOSPACED, Font.BOLD, 12);
}
