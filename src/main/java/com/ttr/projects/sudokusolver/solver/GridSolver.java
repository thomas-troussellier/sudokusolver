package com.ttr.projects.sudokusolver.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ttr.projects.sudokusolver.model.Cell;
import com.ttr.projects.sudokusolver.model.Grid;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import static com.ttr.projects.sudokusolver.model.SudokuGridConstants.*;

public class GridSolver {

    public static void solveGrids(final List<Grid> grids) {
        if (CollectionUtils.isEmpty(grids)) {
            System.out.println("No grid to solve");
            return;
        }

        grids.forEach(GridSolver::solveGrid);
    }

    private static void solveGrid(Grid grid) {
        boolean reevaluate = true;

        while (reevaluate) {
            // First start with computing possible values for each cell
            computePossibleValues(grid);

            // Solve cells
            reevaluate = checkAndResolveSolvedCells(grid);

            // If no changes, check if there is any last remaining value (ie only one possible value for the cell) - Hidden singles
            if (!reevaluate) {
                reevaluate = checkHiddenSingles(grid);
            }
        }
    }

    /**
     * For each line / column / region, remove already set values from cells possible values
     *
     * @param grid - the grid to solve
     */
    private static void computePossibleValues(Grid grid) {
        for (int line = 1; line <= GRID_LINE_NUMBER; line++) {
            computePossibleValuesForList(grid.getLine(line));
        }
        for (int column = 1; column <= GRID_COLUMN_NUMBER; column++) {
            computePossibleValuesForList(grid.getColumn(column));
        }
        for (int region = 1; region <= GRID_REGION_NUMBER; region++) {
            computePossibleValuesForList(grid.getRegion(region));
        }
    }

    private static void computePossibleValuesForList(List<Cell> cells) {
        List<Integer> impossibleValues = cells.stream().map(Cell::getValue).filter(value -> value != 0).collect(Collectors.toList());
        cells.forEach(cell -> cell.removeImpossibleValues(impossibleValues));
    }

    /**
     * For each cell, check if there is only one possible value left.
     * If that is the case, set the value on the cell, clear the possible values
     *
     * @param grid - the grid to update
     * @return boolean - true if a cell value was updated, false in the other case
     */
    private static boolean checkAndResolveSolvedCells(Grid grid) {
        return grid.getCells().stream().map(Cell::evaluateCell).filter(BooleanUtils::isTrue).findFirst().isPresent();
    }

    /**
     * For each line / column / region, check if there is a possible value that appears only for one cell
     * If that is the case, remove all other possibles from this cell except the found value
     *
     * @param grid - the grid to update
     * @return boolean - true is a hidden single was found, false in the other case
     */
    private static boolean checkHiddenSingles(Grid grid) {
        boolean reevaluate = false;
        for (int line = 1; line <= GRID_LINE_NUMBER; line++) {
            reevaluate = reevaluate || checkHiddenSinglesForList(grid.getLine(line));
        }
        for (int column = 1; column <= GRID_COLUMN_NUMBER; column++) {
            reevaluate = reevaluate || checkHiddenSinglesForList(grid.getColumn(column));
        }
        for (int region = 1; region <= GRID_REGION_NUMBER; region++) {
            reevaluate = reevaluate || checkHiddenSinglesForList(grid.getRegion(region));
        }
        return reevaluate;
    }

    private static boolean checkHiddenSinglesForList(List<Cell> cells) {
        Map<Integer, List<Cell>> mapCellsByPossibleValues = new HashMap<>();

        // For each possible value, get the cells that are eligible
        cells.stream().filter(cell -> CollectionUtils.isNotEmpty(cell.getPossibleValues())).forEach(cell ->
                cell.getPossibleValues().stream().forEach(possibleValue -> {
                    if (mapCellsByPossibleValues.containsKey(possibleValue)) {
                        mapCellsByPossibleValues.get(possibleValue).add(cell);
                    } else {
                        List<Cell> tempList = new ArrayList<>();
                        tempList.add(cell);
                        mapCellsByPossibleValues.put(possibleValue, tempList);
                    }
                })
        );

        // Find values for which only one cell is eligible then update the possible values for these cells
        return mapCellsByPossibleValues.entrySet().stream().filter(entry ->
                CollectionUtils.isNotEmpty(entry.getValue()) && entry.getValue().size() == 1
        ).map(entry -> {
            Cell cell = entry.getValue().get(0);
            cell.keepOnlyValue(entry.getKey());
            return true;
        }).findFirst().isPresent();
    }
}
