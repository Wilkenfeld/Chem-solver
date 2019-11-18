package com.enrico.widgets.combobox;

public class DefaultComboBoxItem {
    private final String key;
    private final String value;

    public DefaultComboBoxItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
