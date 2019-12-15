package Solver;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.BoolVar;

import model.Level;
import model.Orientation;
import model.Piece;

public class Csp {
	private Model m_myModel;
	private Piece[][] m_myLevelToSolve;
	private static int BigM = 10;

	public Csp(Level myLevelToSolve) {
		this.m_myLevelToSolve = myLevelToSolve.getGrid();
		this.m_myModel = new Model("My Problem");
	}

	public void initConstraint() {
		for (int i = 0; i < this.m_myLevelToSolve.length; i++) {
			for (int j = 0; j < this.m_myLevelToSolve[0].length; j++) {
				Class myClass = m_myLevelToSolve[i][j].getClass();
				switch (myClass.getName()) {
				case "Bar":
					this.addConstraintPiece2(i, j);
					break;
				case "L":
					this.addConstraintPiece5(i, j);
					break;
				case "Empty":
					this.addConstraintPiece0(i, j);
					break;
				case "Circle":
					this.addConstraintPiece1(i, j);
					break;
				case "T":
					this.addConstraintPiece3(i, j);
					break;
				case "X":
					this.addConstraintPiece4(i, j);
					break;
				}
			}
		}
	}

	private void addConstraintPiece0(int i, int j) {
		BoolVar[] orientation = new BoolVar[Orientation.values().length];
		for(int z=0 ; z < Orientation.values().length ;z++) {
			orientation[z] = this.m_myModel.boolVar(String.valueOf(i) + String.valueOf(j) + String.valueOf(z));
		}
		Constraint c = this.m_myModel.sum(orientation, "=", 0);
		this.m_myModel.post(c);
		
	}

	private void addConstraintPiece1(int i, int j) {
		BoolVar[] orientation = new BoolVar[Orientation.values().length];
		for(int z=0 ; z < Orientation.values().length ;z++) {
			orientation[z] = this.m_myModel.boolVar(String.valueOf(i) + String.valueOf(j) + String.valueOf(z));
		}
		Constraint c = this.m_myModel.sum(orientation, "=", 1);
		this.m_myModel.post(c);
	}

	private void addConstraintPiece2(int i, int j) {
		BoolVar[] orientation = new BoolVar[Orientation.values().length];
		for(int z=0 ; z < Orientation.values().length ;z++) {
			orientation[z] = this.m_myModel.boolVar(String.valueOf(i) + String.valueOf(j) + String.valueOf(z));
		}
		
		
	}

	private void addConstraintPiece3(int i, int j) {
	}

	private void addConstraintPiece4(int i, int j) {
	}

	private void addConstraintPiece5(int i, int j) {
	}
}
