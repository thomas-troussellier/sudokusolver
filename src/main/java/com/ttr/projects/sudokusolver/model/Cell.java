package com.ttr.projects.sudokusolver.model;

public class Cell {
    private Integer value = null;
    private int[] possibleValues;

    public Cell(Integer value, int[] possibleValues) {
        this.value = value;
        this.possibleValues = possibleValues;
    }

    @Override
    public String toString() {
        return " " + value + " ";
    }
}
