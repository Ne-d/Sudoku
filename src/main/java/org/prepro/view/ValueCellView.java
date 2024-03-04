package org.prepro.view;

import javafx.scene.control.Label;

public class ValueCellView extends Label {
    public ValueCellView(int value) {
        setValue(value);
    }

    public void setValue(int value) {
        this.setText(String.valueOf(value));
    }
}
