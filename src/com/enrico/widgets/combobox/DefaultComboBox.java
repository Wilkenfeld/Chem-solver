package com.enrico.widgets.combobox;

import com.enrico.interfaces.FontInterface;

import javax.swing.*;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class DefaultComboBox extends JComboBox implements FontInterface {
    public DefaultComboBox(DefaultComboBoxItem[] items) {
        for (DefaultComboBoxItem item : items)
            addItem(item);

        setFont(normalTextFont);
    }

    public DefaultComboBox(ArrayList<DefaultComboBoxItem> items) {
        for (DefaultComboBoxItem item : items)
            addItem(item);

        setFont(normalTextFont);
    }

    private void createUIComponents() {
    }
}
