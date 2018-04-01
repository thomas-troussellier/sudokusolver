package com.ttr.projects.sudokusolver;

import java.util.List;

import com.ttr.projects.sudokusolver.loader.DirectoryLoading;
import com.ttr.projects.sudokusolver.model.Grid;
import com.ttr.projects.sudokusolver.solver.GridSolver;

public class SudokuSolver {

    public static void main(String[] args) {
        // First step load grids
        List<Grid> grids = DirectoryLoading.loadGridsFromDirectory(args[0]);

        // Then solve them
        GridSolver.solveGrids(grids);

        // Finally, display solved results
        grids.forEach(Grid::displayGrid);
    }
}
