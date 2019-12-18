package dev;

import Solver.Csp;
import Solver.quasiexhaustive.QuasiExhaustiveSolver;
import Solver.quasiexhaustive.RandomizedSolver;
import model.*;
import model.io.FileCreator;
import model.io.FileReader;
import model.pieces.Circle;
import model.pieces.L;
import model.pieces.Piece;
import view.PhineLoopsMainGUI;

public class Tmp {

    public static Level genLevel() {
        int width = 3;
        int height = 3;
        Level lvl = new Level(height, width);
        Piece[][] grid = lvl.getGrid();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (false && j != i) {
                    grid[j][i] = new Circle(j % 4, j, i);
                } else {
                    grid[j][i] = new L(0, j, i);
                }
            }
        }

        return lvl;
    }

    public static Level importLevel(String filename) {
        Piece [][] grid = FileReader.getGrid(filename, " ");
        Level lvl = new Level(grid.length, grid[0].length);
        Piece[][] lvlGrid = lvl.getGrid();

        for (int i = 0; i < grid[0].length; i++) {
            for (int j = 0; j < grid.length; j++) {
                lvlGrid[j][i] = grid[j][i];
            }
        }

        return lvl;
    }

    public static void justDisplay() {
        Level lvl = importLevel("instances/public/grid_32x32_dist.6_vflip.false_hflip.false_messedup.true_id.0.dat");
        Csp solver = new Csp(lvl.getGrid());
        solver.getMyLevelToSolve();

        System.out.println(lvl);
        PhineLoopsMainGUI.display(lvl);
    }

    public static void timeTest() {
        int size = 100000000;
        Integer[] nbs = new Integer[size];
        for (int i = 0; i < size; i++) {
            nbs[i] = 1;
        }

        int a;
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            a = nbs[i] + 1;
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);


        long start2 = System.currentTimeMillis();
        for (Integer n : nbs) {
            a = n + 1;
        }
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);
    }

    public static void solving() {
//        Level lvl = importLevel("instances/public/grid_8x8_dist.0_vflip.false_hflip.true_messedup.true_id.0.dat");
        Level lvl = genLevel();
        lvl.init_neighbors();

        QuasiExhaustiveSolver solver = new QuasiExhaustiveSolver(lvl);

        System.out.println("Is level solved ? " + lvl.checkGrid());
        long start = System.nanoTime();
        solver.solving();
        long end = System.nanoTime();
        System.out.println("Is level solved ? " + lvl.checkGrid());
        System.out.println("Time : " + (end - start) * 10E-10 + " seconds");
    }

    public void testingFileCreator() {
        //        Level lvl1 = importLevel("instances/public/grid_16x16_dist.0_vflip.false_hflip.true_messedup.false_id.3.dat");
        Level lvl1 = genLevel();
        FileCreator.write(lvl1, "outputs/test.txt");

        Level lvl2 = importLevel("outputs/test.txt");
        System.out.println(lvl1);
        System.out.println(lvl2);
    }

    public static void readSolveWrite() {
        String file = "instances/public/grid_8x8_dist.0_vflip.false_hflip.false_messedup.false_id.3.dat";
        String filesolved = "outputs/test.txt";

        Level lvl = importLevel(file);
//        System.out.println(lvl);
        Csp solver = new Csp(lvl.getGrid());
        System.out.println(solver.solving());
        lvl.setGrid(solver.getMyLevelToSolve());

        FileCreator.write(lvl, filesolved);
//        PhineLoopsMainGUI.display(lvl);


        Level lvl2 = importLevel("outputs/test.txt");
        PhineLoopsMainGUI.display(lvl);

    }

    public static void main(String [] args) {
        solving();
    }

    // translation methods in Piece must be corrected, Side is reversed ?
    // level instance verification --> use iterator instead (much more quicker) of index access.
}
