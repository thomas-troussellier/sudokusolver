package com.ttr.projects.sudokusolver.model;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;

import static com.ttr.projects.sudokusolver.model.SudokuGridConstants.*;

public class Grid {
    // Grid filename
    private String name;
    // Grid content - 81 cells for 9 by 9 sudoku grids
    private List<Cell> grid;

    public Grid(String name, List<Cell> grid) {
        this.name = name;
        this.grid = grid;
    }

    public void displayGrid() {
        System.out.println("Grid " + name);

        Collection<List<Cell>> gridLines = this.grid.stream().collect(Collectors.groupingBy(Cell::getLine)).values();

        gridLines.forEach(line ->
                System.out.println(line.stream().sorted(Comparator.comparing(Cell::getColumn)).map(Cell::toString).collect(Collectors.joining()))
        );
    }

    public Grid solveGrid() {
        // TODO extract methods to separate different possible strategies. Maybe new Solver class ?
        // Actualize possible values for lines / columns / regions
        for (int i = 1; i <= GRID_LINE_NUMBER; i++) {
            List<Integer> impossibleValues = this.getLine(i).stream().map(Cell::getValue).filter(value -> value != 0).collect(Collectors.toList());
            this.getLine(i).forEach(cell -> cell.removeImpossibleValues(impossibleValues));
        }
        for (int i = 1; i <= GRID_COLUMN_NUMBER; i++) {
            List<Integer> impossibleValues = this.getColumn(i).stream().map(Cell::getValue).filter(value -> value != 0).collect(Collectors.toList());
            this.getColumn(i).stream().forEach(cell -> cell.removeImpossibleValues(impossibleValues));
        }
        for (int i = 1; i <= GRID_REGION_NUMBER; i++) {
            List<Integer> impossibleValues = this.getRegion(i).stream().map(Cell::getValue).filter(value -> value != 0).collect(Collectors.toList());
            this.getRegion(i).stream().forEach(cell -> cell.removeImpossibleValues(impossibleValues));
        }

        // Evaluate cells
        Optional<Boolean> reevaluate = this.grid.stream().map(Cell::evaluateCell).filter(BooleanUtils::isTrue).findFirst();

        if (!reevaluate.isPresent()) {
            System.out.println("Nothing to evaluate anymore");
            return null;
        }

        // repeat until no cell changed
        this.solveGrid();
        return null;
    }

    private List<Cell> getLine(int index) {
        checkIndex(index, GRID_LINE_NUMBER);
        return grid.stream().filter(cell -> cell.getLine() == index).collect(Collectors.toList());
    }

    private List<Cell> getColumn(int index) {
        checkIndex(index, GRID_COLUMN_NUMBER);
        return grid.stream().filter(cell -> cell.getColumn() == index).collect(Collectors.toList());
    }

    private List<Cell> getRegion(int index) {
        checkIndex(index, GRID_REGION_NUMBER);
        return this.grid.stream().filter(cell -> cell.getRegion() == index).collect(Collectors.toList());
    }

    private void checkIndex(int index, int limitNumber) {
        if (index < 1 || index > limitNumber) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
    }
}
