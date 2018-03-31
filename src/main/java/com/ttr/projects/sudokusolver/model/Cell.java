package com.ttr.projects.sudokusolver.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

public class Cell {
    private Integer value = null;
    private List<Integer> possibleValues = new ArrayList<>();

    public Cell(Integer value, List<Integer> possibleValues) {
        this.value = value;
        this.possibleValues = possibleValues;
        if (value != 0) {
            this.possibleValues.clear();
        }
    }

    public Integer getValue() {
        return this.value;
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

    public String displayPossibleValues() {
        return possibleValues.toString();
    }

    @Override
    public String toString() {
        return " " + value + " ";
    }
}
