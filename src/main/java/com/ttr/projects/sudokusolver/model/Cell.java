package com.ttr.projects.sudokusolver.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import static com.ttr.projects.sudokusolver.model.SudokuGridConstants.CELLS_POSSIBLE_VALUES;
import static com.ttr.projects.sudokusolver.model.SudokuGridConstants.REGIONS_FROM_COORDINATES;

public class Cell {
    // Cell value
    private Integer value = null;
    // Cell possible values
    private List<Integer> possibleValues = new ArrayList<>();

    // Cell position in grid
    private int line;
    private int column;
    private int region;

    public Cell(Integer value, List<Integer> possibleValues, int line, int column) {
        this.value = value;
        this.possibleValues = possibleValues;
        if (value != 0) {
            this.possibleValues.clear();
        }
        this.line = line;
        this.column = column;

        // Compute cell region based on region coordinates on the grid
        // Get x axis and y axis coordinates by dividing cell line and column coordinates on the grid by the number of regions on each axis (3 in our case) and get upper int to
        // get corresponding region x and y coordinates
        this.region = REGIONS_FROM_COORDINATES.get((int) (Math.ceil(this.line / 3.0))).get((int) Math.ceil(this.column / 3.0));
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getRegion() {
        return region;
    }

    public Integer getValue() {
        return this.value;
    }

    public List<Integer> getPossibleValues() {
        return possibleValues;
    }

    /**
     * Check remaining possible values for cell
     * if only one remaining, it's the cell value
     *
     * @return true if value was determined, false in any other case (no change on value)
     */
    public boolean evaluateCell() {
        if (CollectionUtils.isEmpty(possibleValues)) {
            return false;
        }

        if (value != 0 && CollectionUtils.isNotEmpty(possibleValues)) {
            possibleValues.clear();
        }

        if (possibleValues.size() == 1) {
            value = possibleValues.get(0);
            possibleValues.clear();
            return true;
        }

        return false;
    }

    /**
     * Remove list of values from cell possible values
     *
     * @param impossibleValues
     */
    public void removeImpossibleValues(List<Integer> impossibleValues) {
        if (CollectionUtils.isEmpty(impossibleValues)) {
            return;
        }
        this.possibleValues.removeAll(impossibleValues);
    }

    /**
     * Keep only given value in possible list
     *
     * @param valueToKeep
     */
    public void keepOnlyValue(Integer valueToKeep) {
        if (!CELLS_POSSIBLE_VALUES.contains(valueToKeep)) {
            return;
        }
        this.possibleValues.clear();
        this.possibleValues.add(valueToKeep);
    }

    public String displayPossibleValues() {
        return possibleValues.toString();
    }

    @Override
    public String toString() {
        return " " + value + " ";
    }
}
