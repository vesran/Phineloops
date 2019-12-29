package fr.dauphine.javaavance.phineloops;

import java.io.File;

import Solver.Csp;
import Solver.Extend;
import junit.framework.TestCase;
import model.Level;
import model.io.FileCreator;
import model.io.FileReader;
import model.pieces.Bar;
import model.pieces.L;
import model.pieces.Piece;
import model.pieces.X;

/**
 * Unit test for simple App.
 */
public class MainTest extends TestCase {
	/**
	 * Rigourous Test :-)
	 */
	public void testSolveur() {
		Piece[][] solvedGrid = new Piece[2][2];
		solvedGrid[0][0] = new L(1, 0, 0);
		solvedGrid[0][1] = new L(2, 0, 1);
		solvedGrid[1][0] = new L(0, 1, 0);
		solvedGrid[1][1] = new L(3, 0, 1);
		Piece[][] unSolvedGrid = new Piece[2][2];
		unSolvedGrid[0][0] = new L(0, 0, 0);
		unSolvedGrid[0][1] = new L(0, 0, 1);
		unSolvedGrid[1][0] = new L(0, 1, 0);
		unSolvedGrid[1][1] = new L(0, 0, 1);
		Csp monSolveur = new Csp(unSolvedGrid, (short) 1);
		monSolveur.solving(Extend.noExtend);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (!(solvedGrid[i][j].getOrientation() == unSolvedGrid[i][j].getOrientation())) {
					assertTrue(false);
				}
			}
		}
		assertTrue(true);
	}

	public void testCheckGridUnsolved() {
		Piece[][] unSolvedGrid = new Piece[2][2];
		unSolvedGrid[0][0] = new L(0, 0, 0);
		unSolvedGrid[0][1] = new L(0, 0, 1);
		unSolvedGrid[1][0] = new L(0, 1, 0);
		unSolvedGrid[1][1] = new L(0, 0, 1);
		Level test = new Level(unSolvedGrid);
		test.init_neighbors();
		if (test.checkGrid()) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

	public void testCheckGridSolved() {
		Piece[][] solvedGrid = new Piece[2][2];
		solvedGrid[0][0] = new L(1, 0, 0);
		solvedGrid[0][1] = new L(2, 0, 1);
		solvedGrid[1][0] = new L(0, 1, 0);
		solvedGrid[1][1] = new L(3, 0, 1);
		Level test = new Level(solvedGrid);
		test.init_neighbors();
		if (test.checkGrid()) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

	public void testSolveurUnsolvableGrid() {
		Piece[][] unSolvedGrid = new Piece[2][2];
		unSolvedGrid[0][0] = new X(0, 0, 0);
		unSolvedGrid[0][1] = new L(0, 0, 1);
		unSolvedGrid[1][0] = new L(0, 1, 0);
		unSolvedGrid[1][1] = new L(0, 0, 1);
		Csp monSolveur = new Csp(unSolvedGrid, (short) 1);
		boolean test = monSolveur.solving(Extend.noExtend);
		assertFalse(test);
	}

	public void testReadingFile() {
		Piece[][] read = FileReader.getGrid("instances/public/grid_2x2_JunitTest.dat", " ");
		Piece[][] grid = new Piece[2][2];
		grid[0][0] = new Bar(1, 0, 0);
		grid[0][1] = new Bar(1, 0, 1);
		grid[1][0] = new Bar(1, 1, 0);
		grid[1][1] = new Bar(1, 0, 1);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (!(read[i][j].getClass() == grid[i][j].getClass()
						&& read[i][j].getOrientation() == grid[i][j].getOrientation())) {
					assertTrue(false);
				}
			}
		}
		assertTrue(true);
	}

	public void testWritingFile() {
		Piece[][] grid = new Piece[2][2];
		grid[0][0] = new Bar(1, 0, 0);
		grid[0][1] = new Bar(1, 0, 1);
		grid[1][0] = new Bar(1, 1, 0);
		grid[1][1] = new Bar(1, 0, 1);
		FileCreator.write(grid, "instances/public/grid_2x2_test_writing");
		Piece[][] readGrid = FileReader.getGrid("instances/public/grid_2x2_test_writing", " ");
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (!(readGrid[i][j].getClass() == grid[i][j].getClass()
						&& readGrid[i][j].getOrientation() == grid[i][j].getOrientation())) {
					new File("instances/public/grid_2x2_test_writing").delete();
					assertTrue(false);
				}
			}
		}
		new File("instances/public/grid_2x2_test_writing").delete();
		assertTrue(true);
	}
}
