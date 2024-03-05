package org.prepro.view;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class ValueCellView extends Label {
    public ValueCellView(int value) {
        this.setFont(new Font(24));
        setValue(value);
    }

    public void setValue(int value) {
        this.setText(String.valueOf(value));
    }
}
