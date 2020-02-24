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

package com.enrico.drawing.graphicalAtoms.binding.triplebinding;

import com.enrico.drawing.graphicalAtoms.binding.GenericGraphicalBinding;

public class TripleGraphicalBinding extends GenericGraphicalBinding {
    // Central line.
    private int startCentralX;
    private int endCentralX;
    private int startCentralY;
    private int endCentralY;

    // Left line.
    private int startLeftX;
    private int endLeftX;
    private int startLeftY;
    private int endLeftY;

    // Right line.
    private int startRightX;
    private int endRightX;
    private int startRightY;
    private int endRightY;

    public TripleGraphicalBinding(int startCentralX, int endCentralX, int startCentralY, int endCentralY,
                                  int startLeftX, int endLeftX, int startLeftY, int endLeftY,
                                  int startRightX, int endRightX, int startRightY, int endRightY) {
        super("TRIPLE_BINDING_");

        // Central line.
        this.startCentralX = startCentralX;
        this.endCentralX = endCentralX;
        this.startCentralY = startCentralY;
        this.endCentralY = endCentralY;

        // Left line.
        this.startLeftX = startLeftX;
        this.endLeftX = endLeftX;
        this.startLeftY = startLeftY;
        this.endLeftY = endLeftY;

        // Right line.
        this.startRightX = startRightX;
        this.endRightX = endRightX;
        this.startRightY = startRightY;
        this.endRightY = endRightY;
    }

    public int getEndCentralX() {
        return endCentralX;
    }

    public int getEndCentralY() {
        return endCentralY;
    }

    public int getEndLeftX() {
        return endLeftX;
    }

    public int getEndLeftY() {
        return endLeftY;
    }

    public int getStartCentralX() {
        return startCentralX;
    }

    public int getEndRightX() {
        return endRightX;
    }

    public int getStartCentralY() {
        return startCentralY;
    }

    public int getStartLeftX() {
        return startLeftX;
    }

    public int getStartLeftY() {
        return startLeftY;
    }

    public int getStartRightX() {
        return startRightX;
    }

    public int getEndRightY() {
        return endRightY;
    }

    public int getStartRightY() {
        return startRightY;
    }

    public void setEndCentralX(int endCentralX) {
        this.endCentralX = endCentralX;
    }

    public void setEndCentralY(int endCentralY) {
        this.endCentralY = endCentralY;
    }

    public void setEndLeftX(int endLeftX) {
        this.endLeftX = endLeftX;
    }

    public void setEndLeftY(int endLeftY) {
        this.endLeftY = endLeftY;
    }

    public void setEndRightX(int endRightX) {
        this.endRightX = endRightX;
    }

    public void setStartCentralX(int startCentralX) {
        this.startCentralX = startCentralX;
    }

    public void setStartCentralY(int startCentralY) {
        this.startCentralY = startCentralY;
    }

    public void setEndRightY(int endRightY) {
        this.endRightY = endRightY;
    }

    public void setStartLeftY(int startLeftY) {
        this.startLeftY = startLeftY;
    }

    public void setStartLeftX(int startLeftX) {
        this.startLeftX = startLeftX;
    }

    public void setStartRightX(int startRightX) {
        this.startRightX = startRightX;
    }

    public void setStartRightY(int startRightY) {
        this.startRightY = startRightY;
    }
}
