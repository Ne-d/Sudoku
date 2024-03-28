package org.prepro.view;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * A class to represent the value of a cell.
 */
public class ValueView extends Label {
    /**
     * Create a new ValueView.
     *
     * @param value The value to put in the ValueView.
     */
    public ValueView(int value) {
        this.setFont(new Font(24));
        setValue(value);
    }

    /**
     * Sets the value do display.
     *
     * @param value The value to display.
     */
    public void setValue(int value) {
        this.setText(String.valueOf(value));
    }
}
