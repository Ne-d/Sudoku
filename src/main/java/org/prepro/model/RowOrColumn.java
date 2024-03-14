package org.prepro.model;

public class RowOrColumn {
    public enum RowOrColumnType {
        Row,
        Column
    }

    public RowOrColumnType type;
    public int number;


    /**
     * Create a new RowOrColumn
     *
     * @param type The enum to chose whether this represents a row or a column
     * @param num  The number of the row or column (starting at 0)
     */
    public RowOrColumn(RowOrColumnType type, int num) {
        this.type = type;
        this.number = num;
    }
}
