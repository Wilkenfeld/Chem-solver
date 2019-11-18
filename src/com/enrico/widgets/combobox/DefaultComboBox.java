package com.enrico.widgets.combobox;

import javax.swing.*;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class DefaultComboBox extends JComboBox {
    public DefaultComboBox(DefaultComboBoxItem[] items) {
        for (DefaultComboBoxItem item : items)
            addItem(item);
    }

    public DefaultComboBox(ArrayList<DefaultComboBoxItem> items) {
        for (DefaultComboBoxItem item : items)
            addItem(item);
    }

    private void createUIComponents() {
    }
}
