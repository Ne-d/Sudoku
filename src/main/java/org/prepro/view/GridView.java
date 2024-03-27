package org.prepro.view;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.prepro.model.Grid;
import org.prepro.model.GridExemple;
import org.prepro.model.Notes;

public class GridView extends GridPane {
    /**
     * The model that contains the current sudoku grid.
     */
    private Grid grid;

    /**
     * The model that contains the last loaded sudoku grid.
     */
    private Grid startingGrid;

    /**
     * A 2D array of all CellViews in the grid.
     */
    private CellView[][] cellViews;

    /**
     * The column of the currently selected cell in the grid.
     */
    private int selectedColumn = 0;

    /**
     * The column of the currently selected cell in the grid.
     */
    private int selectedRow = 0;

    /**
     * Creates a new GridView with a default grid.
     */
    public GridView() {
        this.setAlignment(Pos.CENTER);

        this.setHgap(2);
        this.setVgap(2);

        loadGrid(GridExemple.grid1.getGrid());
    }

    /**
     * Sets the current grid.
     *
     * @param grid The grid to put into the view.
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
        loadGrid(grid);
    }

    /**
     * Loads a grid into the view.
     *
     * @param grid The grid to load.
     */
    public void loadGrid(Grid grid) {
        this.grid = grid;

        // We use the copy constructor to make a new grid that won't be a reference to the old one.
        this.startingGrid = new Grid(grid);
        this.cellViews = new CellView[grid.SIZE][grid.SIZE];
        int cellSize = 60;

        for (int y = 0; y < this.grid.SIZE; y++) {
            for (int x = 0; x < this.grid.SIZE; x++) {
                this.getChildren().remove(cellViews[x][y]);
                Notes notes = this.grid.getNotes(x, y);
                int value = this.grid.getVal(x, y);

                CellView currentCellView = new CellView(notes, value, this.grid.SIZE, x, y, this, cellSize);
                this.add(currentCellView, x, y);
                cellViews[x][y] = currentCellView;
            }
        }

        // Ajouter les bordures des boîtes (3x3)
        for (int i = 0; i < grid.SQRTSIZE; i++) {
            for (int j = 0; j < grid.SQRTSIZE; j++) {
                int size = (int) (this.grid.SQRTSIZE * cellSize + (grid.SQRTSIZE - 1) * this.getHgap());
                Rectangle cellBorder = new Rectangle(size, size);
                cellBorder.setFill(null);
                cellBorder.setStroke(Color.BLACK);
                this.add(cellBorder, j * grid.SQRTSIZE, i * grid.SQRTSIZE,
                        grid.SQRTSIZE, grid.SQRTSIZE);
            }
        }
    }

    /**
     * Update the visual aspect of every CellView in the GridView.
     * Chooses automatically between showing the value or the notes.
     */
    public void update() {
        // For every cell in the grid
        for (int y = 0; y < this.grid.SIZE; y++) {
            for (int x = 0; x < this.grid.SIZE; x++) {
                // Get the value and notes of the cell in the grid
                Notes notes = this.grid.getNotes(x, y);
                int value = this.grid.getVal(x, y);

                // Update the corresponding CellView
                cellViews[x][y].setNotes(notes);
                cellViews[x][y].setValue(value);
            }
        }
    }

    /**
     * Get the current grid model.
     *
     * @return The current grid model.
     */
    public Grid getGrid() {
        return this.grid;
    }

    /**
     * Reload the last loaded grid.
     */
    public void resetToStartingGrid() {
        this.loadGrid(startingGrid);
    }

    /**
     * Get the selected cell's column.
     *
     * @return The selected cell's column.
     */
    public int getSelectedColumn() {
        return selectedColumn;
    }

    /**
     * Get the selected cell's row.
     *
     * @return The selected cell's row.
     */
    public int getSelectedRow() {
        return selectedRow;
    }

    /**
     * Get the CellView corresponding to the grid's cell at the given coordinates.
     *
     * @param x The x coordinate of the cell.
     * @param y The y coordinate of the cell.
     * @return The CellView at the given coordinates.
     */
    public CellView getCellView(int x, int y) {
        return cellViews[x][y];
    }

    /**
     * Sets the currently selected cell to the given coordinates.
     *
     * @param column The x coordinate of the selected cell.
     * @param row    The y coordinate of the selected cell.
     */
    public void setSelectedCell(int column, int row) {
        this.cellViews[this.selectedColumn][this.selectedRow].setStyle("-fx-background-color:lightgray");
        this.selectedColumn = column;
        this.selectedRow = row;
        this.cellViews[column][row].setStyle("-fx-background-color:#FFCCFF");
    }
}
