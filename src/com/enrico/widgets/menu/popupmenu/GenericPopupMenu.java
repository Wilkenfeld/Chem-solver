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

package com.enrico.widgets.menu.popupmenu;

import com.enrico.programresources.ColorResources;
import com.enrico.programresources.FontResources;

import javax.swing.*;

public abstract class GenericPopupMenu extends JPopupMenu {
    public GenericPopupMenu(String title) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FontResources.normalTextFont);

        add(titleLabel);
    }

    @Override
    public JMenuItem add(JMenuItem item) {
        super.add(item);

        item.setFont(FontResources.menuBarFont);
        item.setBackground(ColorResources.btnBackgroundColor);

        return item;
    }
}
