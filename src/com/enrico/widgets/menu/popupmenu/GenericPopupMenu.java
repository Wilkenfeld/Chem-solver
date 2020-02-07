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
