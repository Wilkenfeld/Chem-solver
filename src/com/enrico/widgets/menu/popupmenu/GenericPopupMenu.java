package com.enrico.widgets.menu.popupmenu;

import com.enrico.interfaces.FontInterface;
import com.enrico.programresources.ColorResources;

import javax.swing.*;

public abstract class GenericPopupMenu extends JPopupMenu implements FontInterface {
    public GenericPopupMenu(String title) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(normalTextFont);

        add(titleLabel);
    }

    @Override
    public JMenuItem add(JMenuItem item) {
        super.add(item);

        item.setFont(menuBarFont);
        item.setBackground(ColorResources.btnBackgroundColor);

        return item;
    }
}
