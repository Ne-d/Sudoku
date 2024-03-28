package org.prepro.model;

/**
 * A class to represent a row or a column of a sudoku grid.
 */
public class RowOrColumn {
    /**
     * An enum to represent whether a RowOrColumn object represents a row or a column.
     */
    public enum RowOrColumnType {
        /**
         * A row is a horizontal line in the grid.
         */
        Row,

        /**
         * A column is a vertical line in the grid.
         */
        Column
    }

    /**
     * Indicates whether this RowOrColumn object represents a row or a column.
     */
    public final RowOrColumnType type;

    /**
     * Indicates the number of the row or column represented by this object.
     */
    public final int number;


    /**
     * Create a new RowOrColumn.
     *
     * @param type The enum to chose whether this represents a row or a column.
     * @param num  The number of the row or column (starting at 0).
     */
    public RowOrColumn(RowOrColumnType type, int num) {
        this.type = type;
        this.number = num;
    }
}
