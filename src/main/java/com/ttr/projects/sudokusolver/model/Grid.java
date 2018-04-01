package com.ttr.projects.sudokusolver.model;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.ttr.projects.sudokusolver.model.SudokuGridConstants.*;

public class Grid {
    // Grid filename
    private String name;
    // Grid content - 81 cells for 9 by 9 sudoku grids
    private List<Cell> cells;

    public Grid(String name, List<Cell> cells) {
        this.name = name;
        this.cells = cells;
    }

    public void displayGrid() {
        System.out.println("Grid " + name);

        Collection<List<Cell>> gridLines = this.cells.stream().collect(Collectors.groupingBy(Cell::getLine)).values();

        gridLines.forEach(line ->
                System.out.println(line.stream().sorted(Comparator.comparing(Cell::getColumn)).map(Cell::toString).collect(Collectors.joining()))
        );
    }

    public List<Cell> getCells() {
        return this.cells;
    }

    public List<Cell> getLine(int index) {
        checkIndex(index, GRID_LINE_NUMBER);
        return cells.stream().filter(cell -> cell.getLine() == index).collect(Collectors.toList());
    }

    public List<Cell> getColumn(int index) {
        checkIndex(index, GRID_COLUMN_NUMBER);
        return cells.stream().filter(cell -> cell.getColumn() == index).collect(Collectors.toList());
    }

    public List<Cell> getRegion(int index) {
        checkIndex(index, GRID_REGION_NUMBER);
        return this.cells.stream().filter(cell -> cell.getRegion() == index).collect(Collectors.toList());
    }

    private void checkIndex(int index, int limitNumber) {
        if (index < 1 || index > limitNumber) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
    }
}
