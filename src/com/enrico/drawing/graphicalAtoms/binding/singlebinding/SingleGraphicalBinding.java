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

package com.enrico.drawing.graphicalAtoms.bond.singlebond;

import com.enrico.drawing.graphicalAtoms.bond.GenericGraphicalBinding;

public final class SingleGraphicalBinding extends GenericGraphicalBinding {
    private int startX;
    private int endX;

    private int startY;
    private int endY;

    public SingleGraphicalBinding(int startX, int endX, int startY, int endY) {
        super("SINGLE_BONDING");

        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }
}
