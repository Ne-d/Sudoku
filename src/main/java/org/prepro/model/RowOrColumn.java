package org.prepro.model;

public class RowOrColumn {
    public enum RowOrColumnEnum {
        Row,
        Column
    }

    RowOrColumnEnum e;
    int number;


    /**
     * Create a new RowOrColumn
     * @param e The enum to chose whether this represents a row or a column
     * @param num The number of the row or column (starting at 0)
     */
    public RowOrColumn(RowOrColumnEnum e, int num) {
        this.e = e;
        this.number = num;
    }
}
