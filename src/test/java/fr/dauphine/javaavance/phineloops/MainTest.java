package fr.dauphine.javaavance.phineloops;

import Solver.Csp;
import Solver.Extend;
import junit.framework.TestCase;
import model.pieces.L;
import model.pieces.Piece;

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
	//	monSolveur.solving(Extend.noExtend);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (!(solvedGrid[i][j].getOrientation() == unSolvedGrid[i][j].getOrientation())) {
					assertTrue(false);
				}
			}
		}
		assertTrue(true);
	}
}
