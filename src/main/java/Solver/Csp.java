package Solver;

import static org.chocosolver.solver.search.strategy.Search.*;

import java.awt.List;
import java.util.Arrays;
import java.util.LinkedList;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.ParallelPortfolio;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.search.limits.TimeCounter;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainImpact;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMax;
import org.chocosolver.solver.search.strategy.selectors.values.IntValueSelector;
import org.chocosolver.solver.search.strategy.selectors.variables.Cyclic;
import org.chocosolver.solver.search.strategy.selectors.variables.ImpactBased;
import org.chocosolver.solver.search.strategy.selectors.variables.InputOrder;
import org.chocosolver.solver.search.strategy.selectors.variables.Occurrence;
import org.chocosolver.solver.search.strategy.strategy.GreedyBranching;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;

import model.pieces.Circle;
import model.pieces.L;
import model.Level;
import model.enumtype.Orientation;
import model.io.FileReader;
import model.pieces.Piece;
import model.pieces.T;
import model.pieces.X;
import view.PhineLoopsMainGUI;

public class Csp implements Solving {
	private Model m_myModel;
	private Piece[][] m_myLevelToSolve;
	private BoolVar[][][] vars;
	private boolean m_solved;

	public Csp(Piece[][] myLevelToSolve) {
		this.m_myLevelToSolve = myLevelToSolve;
		new Level(m_myLevelToSolve).init_neighbors(); 
		this.m_myModel = new Model("My Problem");
		vars = new BoolVar[this.m_myLevelToSolve.length][this.m_myLevelToSolve[0].length][Orientation.values().length];
	}

	private void initConstraint(Extend solvability) {
		for (int i = 0; i < this.m_myLevelToSolve.length; i++) {
			for (int j = 0; j < this.m_myLevelToSolve[0].length; j++) {
				Class myClass = m_myLevelToSolve[i][j].getClass();
				switch (myClass.getName()) {
				case "model.pieces.Bar":
					this.addConstraintPiece2(i, j, solvability);
					break;
				case "model.pieces.L":
					this.addConstraintPiece5(i, j, solvability);
					break;
				case "model.pieces.Empty":
					this.addConstraintPiece0(i, j, solvability);
					break;
				case "model.pieces.Circle":
					this.addConstraintPiece1(i, j, solvability);
					break;
				case "model.pieces.T":
					this.addConstraintPiece3(i, j, solvability);
					break;
				case "model.pieces.X":
					this.addConstraintPiece4(i, j, solvability);
					break;
				}
			}
		}
	}

	private void addConstraintPiece0(int i, int j, Extend extend) {
		BoolVar[] orientation = this.getTab(i, j, extend);
		Constraint c = this.m_myModel.sum(orientation, "=", 0);
		this.m_myModel.post(c);
	}

	private void addConstraintPiece1(int i, int j, Extend extend) {
		BoolVar[] orientation = this.getTab(i, j, extend);
		Constraint c = this.m_myModel.sum(orientation, "=", 1);
		this.m_myModel.post(c);
	}

	private void addConstraintPiece2(int i, int j, Extend extend) {
		BoolVar[] orientation = this.getTab(i, j, extend);
		Constraint c1 = this.m_myModel.sum(orientation, "=", 2);
		Constraint c2 = this.m_myModel.arithm(orientation[0], "=", orientation[2]);
		this.m_myModel.post(c1);
		this.m_myModel.post(c2);
	}

	private void addConstraintPiece3(int i, int j, Extend extend) {
		BoolVar[] orientation = this.getTab(i, j, extend);
		Constraint c = this.m_myModel.sum(orientation, "=", 3);
		this.m_myModel.post(c);
	}

	private void addConstraintPiece4(int i, int j, Extend extend) {
		BoolVar[] orientation = this.getTab(i, j, extend);
		Constraint c = this.m_myModel.sum(orientation, "=", 4);
		this.m_myModel.post(c);
	}

	private void addConstraintPiece5(int i, int j, Extend extend) {
		BoolVar[] orientation = this.getTab(i, j, extend);
		BoolVar[] arrayToSum = new BoolVar[2];
		arrayToSum[0] = orientation[1];
		arrayToSum[1] = orientation[3];
		this.m_myModel.post(this.m_myModel.sum(arrayToSum, "=", 1));
		arrayToSum[0] = orientation[0];
		arrayToSum[1] = orientation[2];
		this.m_myModel.post(this.m_myModel.sum(arrayToSum, "=", 1));
	}

	private BoolVar[] getTab(int i, int j, Extend extend) {
		BoolVar[] orientation = new BoolVar[Orientation.values().length];
		Class myClass = m_myLevelToSolve[i][j].getClass();
		// *------*//
		if (i - 1 >= 0) {
			orientation[0] = this.vars[i - 1][j][2];
			this.vars[i][j][0] = orientation[0];
		} else {
			if (extend == Extend.noExtend) {
				orientation[0] = this.m_myModel.boolVar(String.valueOf(m_myLevelToSolve[i][j].getNeighbor().size()), false);
			} else {
				if (extend == Extend.north || extend == Extend.northEast || extend == Extend.northWest) {
					orientation[0] = this.m_myModel.boolVar(String.valueOf(m_myLevelToSolve[i][j].getNeighbor().size()), false);
				} else {
					orientation[0] = this.m_myModel.boolVar(String.valueOf(m_myLevelToSolve[i][j].getNeighbor().size()));
				}
			}
			this.vars[i][j][0] = orientation[0];
		}
		// ******//
		if (i + 1 < this.m_myLevelToSolve.length) {
			BoolVar var = this.m_myModel.boolVar(String.valueOf(m_myLevelToSolve[i][j].getNeighbor().size()));
			orientation[2] = var;
			this.vars[i][j][2] = var;
			this.vars[i + 1][j][0] = var;
		} else {
			if (extend == Extend.noExtend) {
				orientation[2] = this.m_myModel.boolVar(String.valueOf(m_myLevelToSolve[i][j].getNeighbor().size()), false);
			} else {
				if (extend == Extend.south || extend == Extend.southEast || extend == Extend.southWest) {
					orientation[2] = this.m_myModel.boolVar(String.valueOf(m_myLevelToSolve[i][j].getNeighbor().size()), false);
				} else {
					orientation[2] = this.m_myModel.boolVar(String.valueOf(m_myLevelToSolve[i][j].getNeighbor().size()));
				}
			}
			this.vars[i][j][2] = orientation[2];
		}
		if (j - 1 >= 0) {
			orientation[3] = this.vars[i][j - 1][1];
			this.vars[i][j][3] = this.vars[i][j - 1][1];
		} else {
			if (extend == Extend.noExtend) {
				orientation[3] = this.m_myModel.boolVar(String.valueOf(m_myLevelToSolve[i][j].getNeighbor().size()), false);
			} else {
				if (extend == Extend.west || extend == Extend.northWest || extend == Extend.southWest) {
					orientation[3] = this.m_myModel.boolVar(String.valueOf(m_myLevelToSolve[i][j].getNeighbor().size()), false);
				} else {
					orientation[3] = this.m_myModel.boolVar(String.valueOf(m_myLevelToSolve[i][j].getNeighbor().size()));
				}
			}
			this.vars[i][j][3] = orientation[3];
		}
		if (j + 1 < this.m_myLevelToSolve[0].length) {
			BoolVar var = this.m_myModel.boolVar(String.valueOf(m_myLevelToSolve[i][j].getNeighbor().size()));
			orientation[1] = var;
			this.vars[i][j][1] = var;
			this.vars[i][j + 1][3] = var;
		} else {
			if (extend == Extend.noExtend) {
				orientation[1] = this.m_myModel.boolVar(String.valueOf(m_myLevelToSolve[i][j].getNeighbor().size()), false);
			} else {
				if (extend == Extend.east || extend == Extend.northEast || extend == Extend.southEast) {
					orientation[1] = this.m_myModel.boolVar(String.valueOf(m_myLevelToSolve[i][j].getNeighbor().size()), false);
				} else {
					orientation[1] = this.m_myModel.boolVar(String.valueOf(m_myLevelToSolve[i][j].getNeighbor().size()));
				}
			}
			this.vars[i][j][1] = orientation[1];
		}
		//this.m_myModel.getSolver().setSearch(inputOrderUBSearch(orientation));

		return orientation;
	}

	public static void main(String args[]) {
		Piece[][] test = new Piece[1024][1024];
		for (int z = 0; z < 1024; z++) {
			for (int j = 0; j < 1024; j++) {
				test[z][j] = new X(0, z, j);
			}
		}
		Piece[][] test2 = new Piece[3][3];
		test2[0][0] = new X(0, 0, 0);
		test2[0][1] = new X(0, 0, 1);
		test2[0][2] = new X(0, 0, 2);
		test2[1][0] = new X(0, 1, 0);
		test2[1][1] = new X(0, 1, 1);
		test2[1][2] = new X(0, 1, 2);
		test2[2][0] = new X(0, 2, 0);
		test2[2][1] = new X(0, 2, 1);
		test2[2][2] = new X(0, 2, 2);
		Piece[][] test3 = FileReader.getGrid(
				"C:\\Users\\Bilal\\git\\phineloops-kby\\instances\\public\\grid_128x128_dist.1_vflip.true_hflip.true_messedup.false_id.1.dat",
				" ");
		Csp moncsp = new Csp(test3);
		long debut = System.currentTimeMillis();
		boolean aa = moncsp.solving(Extend.noExtend);
		long fin = System.currentTimeMillis();
		Level a = new Level(test2);
		a.init_neighbors();
		// System.out.println(a);
		System.out.println(aa);
		// System.out.println(System.currentTimeMillis() - debut);
		// System.out.print(a.checkGrid());
	}

	public Piece[][] getMyLevelToSolve() {
		return this.m_myLevelToSolve;
	}

	public boolean solving(Extend extend) {
		this.initConstraint(extend);
		
		// this.m_myModel.getSolver().limitTime("1s");
		// arallelPortfolio portfolio = new ParallelPortfolio(false);
		
		BoolVar[] orientation = new BoolVar[this.vars.length*this.vars[0].length*this.vars[0][0].length];
		int index = 0 ; 
		for (int i = 0; i < this.vars.length; i++) {
			for (int j = 0; j < this.vars[0].length; j++) {
				for (int z = 0; z < this.vars[0][0].length; z++) {
					orientation[index] = this.vars[i][j][z] ; 
					index++ ; 
				}
			}
		}
		this.m_myModel.getSolver().limitTime("65s");
		
		this.m_myModel.getSolver().setSearch(bestBound(minDomUBSearch(orientation)));
		//this.m_myModel.getSolver().setSearch(greedySearch(intVarSearch(new VariableSelectorPersonal() , new IntDomainMax() , orientation)));
		
		
		this.m_solved = this.m_myModel.getSolver().solve();
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

	public boolean divideReign() {
		short nbIter = 0;
		Csp moncsp = null;
		int divide = 0;
		switch (this.m_myLevelToSolve.length) {
		case 8:
			divide = 1;
			break;
		case 16:
			divide = 2;
			break;
		case 32:
			divide = 4;
			break;
		case 64:
			divide = 8;
			break;
		case 128:
			divide = 16;
			break;
		case 256:
			divide = 32;
			break;
		case 512:
			divide = 64;
			break;
		case 1024:
			divide = 128;
			break;
		}
		for (int i = 0; i < m_myLevelToSolve.length; i += m_myLevelToSolve.length / divide) {
			for (int j = 0; j < m_myLevelToSolve.length; j += m_myLevelToSolve.length / divide) {
				Piece[][] tabsem = new Piece[m_myLevelToSolve.length / divide][m_myLevelToSolve.length / divide];
				for (int r = 0; r < tabsem.length; r++) {
					for (int z = 0; z < tabsem[0].length; z++) {
						tabsem[r][z] = m_myLevelToSolve[r + nbIter * m_myLevelToSolve.length / divide][z
								+ nbIter * m_myLevelToSolve.length / divide];
					}
				}
				boolean value = true;
				if (j == 0 && i == 0) {
					moncsp = new Csp(tabsem);
					value = moncsp.solving(Extend.northWest);
				} else if (i == 0 && !(j == 0)) {
					if (j + m_myLevelToSolve.length / divide == m_myLevelToSolve.length) {
						moncsp = new Csp(tabsem);
						value = moncsp.solving(Extend.northEast);
					} else {
						moncsp = new Csp(tabsem);
						value = moncsp.solving(Extend.north);
					}
				} else if (j == 0 && !(i == 0)) {
					if (i + m_myLevelToSolve.length / divide == m_myLevelToSolve.length) {
						moncsp = new Csp(tabsem);
						value = moncsp.solving(Extend.southWest);
					} else {
						moncsp = new Csp(tabsem);
						value = moncsp.solving(Extend.west);
					}
				} else if (j + m_myLevelToSolve.length / divide == m_myLevelToSolve.length
						&& i + m_myLevelToSolve.length / divide == m_myLevelToSolve.length) {
					moncsp = new Csp(tabsem);
					value = moncsp.solving(Extend.southEast);
				} else if (j + m_myLevelToSolve.length / divide == m_myLevelToSolve.length && (i != 0)) {
					moncsp = new Csp(tabsem);
					value = moncsp.solving(Extend.east);
				} else {
					moncsp = new Csp(tabsem);
					value = moncsp.solving(Extend.allExtend);
				}
				if (!value) {
					Level a = new Level(tabsem);
					// PhineLoopsMainGUI.display(a);
					return false;
				}
			}
		}
		return true;
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
