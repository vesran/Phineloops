package Solver;

import java.util.Arrays;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.search.limits.TimeCounter;
import org.chocosolver.solver.variables.BoolVar;

import model.pieces.Circle;
import model.pieces.L;
import model.Level;
import model.enumtype.Orientation;
import model.io.FileReader;
import model.pieces.Piece;
import model.pieces.T;
import model.pieces.X;

public class Csp implements Solving {
	private Model m_myModel;
	private Piece[][] m_myLevelToSolve;
	private BoolVar[][][] vars;
	private boolean m_solved;

	public Csp(Piece[][] myLevelToSolve) {
		this.m_myLevelToSolve = myLevelToSolve;
		this.m_myModel = new Model("My Problem");
		vars = new BoolVar[this.m_myLevelToSolve.length][this.m_myLevelToSolve[0].length][Orientation.values().length];
	}

	private void initConstraint() {
		for (int i = 0; i < this.m_myLevelToSolve.length; i++) {
			for (int j = 0; j < this.m_myLevelToSolve[0].length; j++) {
				Class myClass = m_myLevelToSolve[i][j].getClass();
				switch (myClass.getName()) {
				case "model.pieces.Bar":
					this.addConstraintPiece2(i, j);
					break;
				case "model.pieces.L":
					this.addConstraintPiece5(i, j);
					break;
				case "model.pieces.Empty":
					this.addConstraintPiece0(i, j);
					break;
				case "model.pieces.Circle":
					this.addConstraintPiece1(i, j);
					break;
				case "model.pieces.T":
					this.addConstraintPiece3(i, j);
					break;
				case "model.pieces.X":
					this.addConstraintPiece4(i, j);
					break;
				}
			}
		}
		this.initGeneralConstraint();
		// TimeCounter a = new TimeCounter(this.m_myModel,20000);
		// this.m_myModel.getSolver().addStopCriterion(a);
		this.m_myModel.getSolver().limitTime("20s");
		this.m_solved = this.m_myModel.getSolver().solve();
	}

	private void addConstraintPiece0(int i, int j) {
		for (int z = 0; z < Orientation.values().length; z++) {
			BoolVar var = this.m_myModel.boolVar(String.valueOf(i) + String.valueOf(j) + String.valueOf(z));
			this.m_myModel.post(this.m_myModel.arithm(var, "=", 0));
			vars[i][j][z] = var;
		}
		//Constraint c = this.m_myModel.sum(orientation, "=", 4);
		// this.m_myModel.post(c);
	}

	private void addConstraintPiece1(int i, int j) {
		BoolVar[] orientation = new BoolVar[Orientation.values().length];
		for (int z = 0; z < Orientation.values().length; z++) {
			BoolVar var = this.m_myModel.boolVar(String.valueOf(i) + String.valueOf(j) + String.valueOf(z));
			orientation[z] = var;
			vars[i][j][z] = var;
		}
		Constraint c = this.m_myModel.sum(orientation, "=", 1);
		this.m_myModel.post(c);
	}

	private void addConstraintPiece2(int i, int j) {
		BoolVar[] orientation = new BoolVar[Orientation.values().length];
		for (int z = 0; z < Orientation.values().length; z++) {
			BoolVar var = this.m_myModel.boolVar(String.valueOf(i) + String.valueOf(j) + String.valueOf(z));
			orientation[z] = var;
			vars[i][j][z] = var;
		}
		Constraint[] myConstraint = new Constraint[5];
		// myConstraint[0] = this.m_myModel.arithm(orientation[3], "=", orientation[1]);
		myConstraint[1] = this.m_myModel.arithm(orientation[0], "=", orientation[2]);

		myConstraint[2] = this.m_myModel.sum(orientation, "=", 2);
		this.m_myModel.post(myConstraint[2]);
		this.m_myModel.post(myConstraint[1]);

		// myConstraint[3] = this.m_myModel.arithm(orientation[3], "=", 0);
		// myConstraint[4] = this.m_myModel.arithm(orientation[0], "=", 1);
		/// this.m_myModel.ifOnlyIf(myConstraint[3], myConstraint[4]);
	}

	private void addConstraintPiece3(int i, int j) {
		BoolVar[] orientation = new BoolVar[Orientation.values().length];
		for (int z = 0; z < Orientation.values().length; z++) {
			BoolVar var = this.m_myModel.boolVar(String.valueOf(i) + String.valueOf(j) + String.valueOf(z));
			orientation[z] = var;
			vars[i][j][z] = var;
		}
		Constraint[] myConstraint = new Constraint[9];
		/*
		 * myConstraint[0] = this.m_myModel.arithm(orientation[0], "=", 0); BoolVar[]
		 * arrayToSum = new BoolVar[3]; arrayToSum[0] = orientation[1]; arrayToSum[1] =
		 * orientation[2]; arrayToSum[2] = orientation[3]; myConstraint[1] =
		 * this.m_myModel.sum(arrayToSum, "=", 3);
		 * this.m_myModel.ifOnlyIf(myConstraint[0], myConstraint[1]); myConstraint[2] =
		 * this.m_myModel.arithm(orientation[1], "=", 0); arrayToSum[0] =
		 * orientation[0]; arrayToSum[1] = orientation[2]; arrayToSum[2] =
		 * orientation[3]; myConstraint[3] = this.m_myModel.sum(arrayToSum, "=", 3);
		 * this.m_myModel.ifOnlyIf(myConstraint[2], myConstraint[3]); myConstraint[4] =
		 * this.m_myModel.arithm(orientation[2], "=", 0); arrayToSum[0] =
		 * orientation[0]; arrayToSum[1] = orientation[1]; arrayToSum[2] =
		 * orientation[3]; myConstraint[5] = this.m_myModel.sum(arrayToSum, "=", 3);
		 * this.m_myModel.ifOnlyIf(myConstraint[4], myConstraint[5]); myConstraint[6] =
		 * this.m_myModel.arithm(orientation[3], "=", 0); arrayToSum[0] =
		 * orientation[0]; arrayToSum[1] = orientation[1]; arrayToSum[2] =
		 * orientation[2]; myConstraint[7] = this.m_myModel.sum(arrayToSum, "=", 3);
		 * this.m_myModel.ifOnlyIf(myConstraint[6], myConstraint[7]);
		 */
		myConstraint[8] = this.m_myModel.sum(orientation, "=", 3);
		this.m_myModel.post(myConstraint[8]);
	}

	private void addConstraintPiece4(int i, int j) {
		BoolVar[] orientation = new BoolVar[Orientation.values().length];
		for (int z = 0; z < Orientation.values().length; z++) {
			BoolVar var = this.m_myModel.boolVar(String.valueOf(i) + String.valueOf(j) + String.valueOf(z));
			this.m_myModel.post(this.m_myModel.arithm(var, "=", 1));
			orientation[z] = var;
			vars[i][j][z] = var;
		}
		// Constraint c = this.m_myModel.sum(orientation, "=", 4);
		// this.m_myModel.post(c);
	}

	private void addConstraintPiece5(int i, int j) {
		BoolVar[] orientation = new BoolVar[Orientation.values().length];
		for (int z = 0; z < Orientation.values().length; z++) {
			BoolVar var = this.m_myModel.boolVar(String.valueOf(i) + String.valueOf(j) + String.valueOf(z));
			orientation[z] = var;
			vars[i][j][z] = var;
		}
		Constraint[] myConstraint = new Constraint[11];
		BoolVar[] arrayToSum = new BoolVar[2];
		arrayToSum[0] = orientation[3];
		arrayToSum[1] = orientation[2];
		// myConstraint[0] = this.m_myModel.sum(arrayToSum, "=", 0);
		arrayToSum[0] = orientation[0];
		arrayToSum[1] = orientation[1];
		// myConstraint[1] = this.m_myModel.sum(arrayToSum, "=", 2);
		// this.m_myModel.ifOnlyIf(myConstraint[0], myConstraint[1]);
		// --//
		arrayToSum[0] = orientation[3];
		arrayToSum[1] = orientation[0];
		// myConstraint[2] = this.m_myModel.sum(arrayToSum, "=", 0);
		arrayToSum[0] = orientation[1];
		arrayToSum[1] = orientation[2];
		// myConstraint[3] = this.m_myModel.sum(arrayToSum, "=", 2);
		// this.m_myModel.ifOnlyIf(myConstraint[2], myConstraint[3]);
		// --//
		arrayToSum[0] = orientation[1];
		arrayToSum[1] = orientation[3];
		myConstraint[4] = this.m_myModel.sum(arrayToSum, "=", 1);
		this.m_myModel.post(myConstraint[4]);
		// --//
		arrayToSum[0] = orientation[0];
		arrayToSum[1] = orientation[2];
		myConstraint[5] = this.m_myModel.sum(arrayToSum, "=", 1);
		this.m_myModel.post(myConstraint[5]);
		// --//
		arrayToSum[0] = orientation[0];
		arrayToSum[1] = orientation[1];
		// myConstraint[6] = this.m_myModel.sum(arrayToSum, "=", 0);
		arrayToSum[0] = orientation[3];
		arrayToSum[1] = orientation[2];
		// myConstraint[7] = this.m_myModel.sum(arrayToSum, "=", 2);
		// this.m_myModel.ifOnlyIf(myConstraint[6], myConstraint[7]);
		// --//
		arrayToSum[0] = orientation[1];
		arrayToSum[1] = orientation[2];
		// myConstraint[8] = this.m_myModel.sum(arrayToSum, "=", 0);
		arrayToSum[0] = orientation[3];
		arrayToSum[1] = orientation[0];
		// myConstraint[9] = this.m_myModel.sum(arrayToSum, "=", 2);
		// this.m_myModel.ifOnlyIf(myConstraint[8], myConstraint[9]);
		// --//
		//myConstraint[10] = this.m_myModel.sum(orientation, "=", 2);
		// this.m_myModel.post(myConstraint[10]);
	}

	private void initGeneralConstraint() {
		for (int i = 0; i < this.m_myLevelToSolve.length; i++) {
			for (int j = 0; j < this.m_myLevelToSolve[0].length; j++) {
				if (j + 1 < this.m_myLevelToSolve[0].length) {
					BoolVar RightWall = vars[i][j][1];
					BoolVar LeftWall = vars[i][j + 1][3];
					Constraint c = this.m_myModel.arithm(RightWall, "=", LeftWall);
					this.m_myModel.post(c);
				} else {
					BoolVar RightWall = vars[i][j][1];
					Constraint c = this.m_myModel.arithm(RightWall, "=", 0);
					this.m_myModel.post(c);
				}

				if (j - 1 >= 0) {
					
					  BoolVar RightWall = vars[i][j - 1][1]; BoolVar LeftWall = vars[i][j][3];
					 Constraint c = this.m_myModel.arithm(RightWall, "=", LeftWall);
					  this.m_myModel.post(c);
					 
				} else {
					BoolVar LeftWall = vars[i][j][3];
					Constraint c = this.m_myModel.arithm(LeftWall, "=", 0);
					this.m_myModel.post(c);
				}

				if (i + 1 < this.m_myLevelToSolve.length) {
					BoolVar southWall = vars[i][j][2];
					BoolVar northWall = vars[i + 1][j][0];
					Constraint c = this.m_myModel.arithm(southWall, "=", northWall);
					this.m_myModel.post(c);
				} else {
					BoolVar southWall = vars[i][j][2];
					Constraint c = this.m_myModel.arithm(southWall, "=", 0);
					this.m_myModel.post(c);
				}
				if (i - 1 >= 0) {
					
					 BoolVar northWall = vars[i][j][0]; BoolVar southWall = vars[i - 1][j][2];
					  Constraint c = this.m_myModel.arithm(southWall, "=", northWall);
					  this.m_myModel.post(c);
					 
				} else {
					BoolVar northWall = vars[i][j][0];
					Constraint c = this.m_myModel.arithm(northWall, "=", 0);
					this.m_myModel.post(c);
				}

			}
		}
	}

	public static void main(String args[]) {
		Piece[][] test = new Piece[28][28];
		for (int z = 0; z < 28; z++) {
			for (int j = 0; j < 28; j++) {
				test[z][j] = new Circle(0, z, j);
			}
		}
		Piece[][] test2 = new Piece[3][3];
		test2[0][0] = new L(0, 0, 0);
		test2[0][1] = new T(0, 0, 1);
		test2[0][2] = new L(0, 0, 2);
		test2[1][0] = new T(0, 1, 0);
		test2[1][1] = new X(0, 1, 1);
		test2[1][2] = new T(0, 1, 2);
		test2[2][0] = new L(0, 2, 0);
		test2[2][1] = new T(0, 2, 1);
		test2[2][2] = new L(0, 2, 2);
		Piece[][] test3 = FileReader.getGrid("/Users/Mamr/JavaProject/phineloops-kby/instances/public/grid_256x256_dist.0_vflip.false_hflip.false_messedup.false_id.0.dat"," ");
		Csp moncsp = new Csp(test3);
		long debut = System.currentTimeMillis();
		boolean aa = moncsp.solving();
		Level a = new Level(test3);
		a.init_neighbors();
		//System.out.println(a);
		System.out.println(System.currentTimeMillis() - debut);
		if (a.checkGrid()) {
			System.out.println("OUI");
		} else {
			System.out.println("NON");
		}
		System.out.println(aa);
	}

	public Piece[][] getMyLevelToSolve() {
		return this.m_myLevelToSolve;
	}

	public boolean solving() {
		this.initConstraint();
		if (this.m_solved) {
			for (int i = 0; i < m_myLevelToSolve.length; i++) {
				for (int j = 0; j < this.m_myLevelToSolve[0].length; j++) {
					for (int z = 0; z < Orientation.values().length; z++) {
						this.guessOrientation(i, j, vars[i][j]);
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	private void guessOrientation(int i, int j, BoolVar[] open) {
		Class myClass = m_myLevelToSolve[i][j].getClass();
		switch (myClass.getName()) {
		case "model.pieces.Bar":
			if (open[0].getValue() == 1) {
				m_myLevelToSolve[i][j].setOrientation(0);
			} else {
				m_myLevelToSolve[i][j].setOrientation(1);
			}
			break;
		case "model.pieces.L":
			if (open[0].getValue() == 1 && open[1].getValue() == 1) {
				m_myLevelToSolve[i][j].setOrientation(0);
			} else if (open[1].getValue() == 1 && open[2].getValue() == 1) {
				m_myLevelToSolve[i][j].setOrientation(1);
			} else if (open[2].getValue() == 1 && open[3].getValue() == 1) {
				m_myLevelToSolve[i][j].setOrientation(2);
			} else {
				m_myLevelToSolve[i][j].setOrientation(3);
			}
			break;
		case "model.pieces.Empty":
			break;
		case "model.pieces.Circle":
			if (open[0].getValue() == 1) {
				m_myLevelToSolve[i][j].setOrientation(0);
			} else if (open[1].getValue() == 1) {
				m_myLevelToSolve[i][j].setOrientation(1);
			} else if (open[2].getValue() == 1) {
				m_myLevelToSolve[i][j].setOrientation(2);
			} else {
				m_myLevelToSolve[i][j].setOrientation(3);
			}
			break;
		case "model.pieces.T":
			if (open[0].getValue() == 1 && open[1].getValue() == 1 && open[3].getValue() == 1) {
				m_myLevelToSolve[i][j].setOrientation(0);
			} else if (open[0].getValue() == 1 && open[1].getValue() == 1 && open[2].getValue() == 1) {
				m_myLevelToSolve[i][j].setOrientation(1);
			} else if (open[3].getValue() == 1 && open[1].getValue() == 1 && open[2].getValue() == 1) {
				m_myLevelToSolve[i][j].setOrientation(2);
			} else {
				m_myLevelToSolve[i][j].setOrientation(3);
			}
			break;
		case "model.pieces.X":
			break;
		}
	}
}
