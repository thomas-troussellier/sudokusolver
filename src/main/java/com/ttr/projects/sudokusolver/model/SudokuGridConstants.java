package com.ttr.projects.sudokusolver.model;

import java.util.*;

public class SudokuGridConstants {
    public static final List<Integer> CELLS_POSSIBLE_VALUES = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    public static final Integer GRID_ROW_NUMBER = 9;
    public static final Integer GRID_COLUMN_NUMBER = 9;
    public static final Integer GRID_REGION_NUMBER = 9;
    public static final Integer GRID_INDEX_MAX_NUMBER = 9;
    public static final Integer GRID_CELL_NUMBER = GRID_ROW_NUMBER * GRID_COLUMN_NUMBER;

    // Mapping to recover region number based on x then y region coordinates -> Map<Xaxis, Map<Yaxis, regionNumber>>
    public static final Map<Integer, Map<Integer, Integer>> REGIONS_FROM_COORDINATES;

    static {
        Map<Integer, Map<Integer, Integer>> tempMap = new HashMap<>();
        Map<Integer, Integer> mapOne = new HashMap<>();
        mapOne.put(1, 1);
        mapOne.put(2, 2);
        mapOne.put(3, 3);
        tempMap.put(1, mapOne);

        Map<Integer, Integer> mapTwo = new HashMap<>();
        mapTwo.put(1, 4);
        mapTwo.put(2, 5);
        mapTwo.put(3, 6);
        tempMap.put(2, mapTwo);

        Map<Integer, Integer> mapThree = new HashMap<>();
        mapThree.put(1, 7);
        mapThree.put(2, 8);
        mapThree.put(3, 9);
        tempMap.put(3, mapThree);

        REGIONS_FROM_COORDINATES = Collections.unmodifiableMap(tempMap);
    }
}
