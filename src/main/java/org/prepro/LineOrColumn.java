package org.prepro;

public class LineOrColumn {
    public enum LineOrColumnEnum {
        Line,
        Column
    }

    LineOrColumnEnum e;
    int number;

    /**
     * Create a new LineOrColumn
     * @param e The enum to chose whether this represents a line or a column
     * @param num The number of the line or column (starting at 0)
     */
    public LineOrColumn(LineOrColumnEnum e, int num) {
        this.e = e;
        this.number = num;
    }
}
