package com.ttr.projects.sudokusolver.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grid {
    private String name;
    private Cell[][] grid;
    private List<Integer> cellValues;

    public Grid(String name, Cell[][] grid, List<Integer> cellValues) {
        this.name = name;
        this.grid = grid;
        this.cellValues = cellValues;
    }

    public void displayGrid() {
        System.out.println("Grid " + name);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                System.out.print(grid[i][j].displayPossibleValues());
            }
            System.out.println();
        }
    }

    public Grid solveGrid() {
        // Actualize possible values for lines / columns / regions
        for (int i = 0; i < this.grid.length; i++) {
            List<Integer> impossibleValues = Stream.of(this.getLine(i)).map(Cell::getValue).filter(value -> value != 0).collect(Collectors.toList());
            Stream.of(this.getLine(i)).forEach(cell -> cell.removeImpossibleValues(impossibleValues));
        }
        for (int i = 0; i < this.grid.length; i++) {
            List<Integer> impossibleValues = Stream.of(this.getColumn(i)).map(Cell::getValue).filter(value -> value != 0).collect(Collectors.toList());
            Stream.of(this.getColumn(i)).forEach(cell -> cell.removeImpossibleValues(impossibleValues));
        }
        for (int i = 0; i < this.grid.length; i++) {
            List<Integer> impossibleValues = Stream.of(this.getRegion(i)).map(Cell::getValue).filter(value -> value != 0).collect(Collectors.toList());
            Stream.of(this.getRegion(i)).forEach(cell -> cell.removeImpossibleValues(impossibleValues));
        }
        // Evaluate cells
        // repeat until no cell changed
        return null;
    }

    private Cell[] getLine(int index) {
        if (index < 0 || index > this.grid.length) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
        return this.grid[index];
    }

    private Cell[] getColumn(int index) {
        if (index < 0 || index > this.grid.length) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
        return Arrays.stream(this.grid).map(cells -> cells[index]).toArray(Cell[]::new);
    }

    private Cell[] getRegion(int index) {
        if (index < 0 || index > this.grid.length) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }

        int regionSize = (int) Math.round(Math.sqrt(this.grid.length));
        int lowerBound = 0;
        int upperBound = (index + 1) * regionSize;

        Cell[] region = new Cell[this.grid.length];
        for (int i = 0; i < regionSize; i++) {
            Arrays.copyOfRange(this.getLine(i), lowerBound, upperBound);
        }
        return Arrays.stream(this.grid).map(cells -> cells[index]).toArray(Cell[]::new);
    }
}
