package Solver;
import static org.chocosolver.solver.search.strategy.Search.*;
import java.util.ArrayList;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.ParallelPortfolio;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.BoolVar;
import model.Level;
import model.enumtype.Orientation;
import model.io.FileReader;
import model.pieces.Piece;


/**
 *  @author Karim Amrouche
 * @author Bilal Khaldi
 * @author Yves Tran
 * Class which makes it possible to solve with a system under constraints
 */
public class Csp implements Solving {
	
	
	private Model m_lastModel;
	private Piece[][] m_myLevelToSolve;
	private BoolVar[][][] vars;
	private boolean m_solved;
	private short m_nbThreads;

	
	public Csp(Piece[][] myLevelToSolve, short thread) {
		this.m_myLevelToSolve = myLevelToSolve;
		new Level(m_myLevelToSolve).init_neighbors();
		this.m_lastModel = new Model("My Problem");
		vars = new BoolVar[this.m_myLevelToSolve.length][this.m_myLevelToSolve[0].length][Orientation.values().length];
		this.m_nbThreads = thread;
	}

	private void initConstraint(Extend solvability) {
		for (int i = 0; i < this.m_myLevelToSolve.length; i++) {
			for (int j = 0; j < this.m_myLevelToSolve[0].length; j++) {
				Class<? extends Piece> myClass = m_myLevelToSolve[i][j].getClass();
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
		Constraint c = this.m_lastModel.sum(orientation, "=", 0);
		this.m_lastModel.post(c);
	}

	private void addConstraintPiece1(int i, int j, Extend extend) {
		BoolVar[] orientation = this.getTab(i, j, extend);
		Constraint c = this.m_lastModel.sum(orientation, "=", 1);
		this.m_lastModel.post(c);
	}

	private void addConstraintPiece2(int i, int j, Extend extend) {
		BoolVar[] orientation = this.getTab(i, j, extend);
		Constraint c1 = this.m_lastModel.sum(orientation, "=", 2);
		Constraint c2 = this.m_lastModel.arithm(orientation[0], "=", orientation[2]);
		this.m_lastModel.post(c1);
		this.m_lastModel.post(c2);
	}

	private void addConstraintPiece3(int i, int j, Extend extend) {
		BoolVar[] orientation = this.getTab(i, j, extend);
		Constraint c = this.m_lastModel.sum(orientation, "=", 3);
		this.m_lastModel.post(c);
	}

	private void addConstraintPiece4(int i, int j, Extend extend) {
		BoolVar[] orientation = this.getTab(i, j, extend);
		Constraint c = this.m_lastModel.sum(orientation, "=", 4);
		this.m_lastModel.post(c);
	}

	private void addConstraintPiece5(int i, int j, Extend extend) {
		BoolVar[] orientation = this.getTab(i, j, extend);
		BoolVar[] arrayToSum = new BoolVar[2];
		arrayToSum[0] = orientation[1];
		arrayToSum[1] = orientation[3];
		this.m_lastModel.post(this.m_lastModel.sum(arrayToSum, "=", 1));
		arrayToSum[0] = orientation[0];
		arrayToSum[1] = orientation[2];
		this.m_lastModel.post(this.m_lastModel.sum(arrayToSum, "=", 1));
	}

	private BoolVar[] getTab(int i, int j, Extend extend) {
		BoolVar[] orientation = new BoolVar[Orientation.values().length];
		// *------*//
		if (i - 1 >= 0) {
			orientation[0] = this.vars[i - 1][j][2];
			this.vars[i][j][0] = orientation[0];
		} else {
			if (extend == Extend.noExtend) {
				orientation[0] = this.m_lastModel.boolVar(String.valueOf(i) + "," + String.valueOf(j) + ",0", false);
			} else {
				if (extend == Extend.north || extend == Extend.northEast || extend == Extend.northWest) {
					orientation[0] = this.m_lastModel.boolVar(String.valueOf(i) + "," + String.valueOf(j) + ",0",
							false);
				} else {
					orientation[0] = this.m_lastModel.boolVar(String.valueOf(i) + "," + String.valueOf(j) + ",0");
				}
			}
			this.vars[i][j][0] = orientation[0];
		}
		// ******//
		if (i + 1 < this.m_myLevelToSolve.length) {
			BoolVar var = this.m_lastModel.boolVar(String.valueOf(i) + "," + String.valueOf(j) + ",2");
			orientation[2] = var;
			this.vars[i][j][2] = var;
			this.vars[i + 1][j][0] = var;
		} else {
			if (extend == Extend.noExtend) {
				orientation[2] = this.m_lastModel.boolVar(String.valueOf(i) + "," + String.valueOf(j) + ",2", false);
			} else {
				if (extend == Extend.south || extend == Extend.southEast || extend == Extend.southWest) {
					orientation[2] = this.m_lastModel.boolVar(String.valueOf(i) + "," + String.valueOf(j) + ",2",
							false);
				} else {
					orientation[2] = this.m_lastModel.boolVar(String.valueOf(i) + "," + String.valueOf(j) + ",2");
				}
			}
			this.vars[i][j][2] = orientation[2];
		}
		if (j - 1 >= 0) {
			orientation[3] = this.vars[i][j - 1][1];
			this.vars[i][j][3] = this.vars[i][j - 1][1];
		} else {
			if (extend == Extend.noExtend) {
				orientation[3] = this.m_lastModel.boolVar(String.valueOf(i) + "," + String.valueOf(j) + ",3", false);
			} else {
				if (extend == Extend.west || extend == Extend.northWest || extend == Extend.southWest) {
					orientation[3] = this.m_lastModel.boolVar(String.valueOf(i) + "," + String.valueOf(j) + ",3",
							false);
				} else {
					orientation[3] = this.m_lastModel.boolVar(String.valueOf(i) + "," + String.valueOf(j) + ",3");
				}
			}
			this.vars[i][j][3] = orientation[3];
		}
		if (j + 1 < this.m_myLevelToSolve[0].length) {
			BoolVar var = this.m_lastModel.boolVar(String.valueOf(i) + "," + String.valueOf(j) + ",1");
			orientation[1] = var;
			this.vars[i][j][1] = var;
			this.vars[i][j + 1][3] = var;
		} else {
			if (extend == Extend.noExtend) {
				orientation[1] = this.m_lastModel.boolVar(String.valueOf(i) + "," + String.valueOf(j) + ",1", false);
			} else {
				if (extend == Extend.east || extend == Extend.northEast || extend == Extend.southEast) {
					orientation[1] = this.m_lastModel.boolVar(String.valueOf(i) + "," + String.valueOf(j) + ",1",
							false);
				} else {
					orientation[1] = this.m_lastModel.boolVar(String.valueOf(i) + "," + String.valueOf(j) + ",1");
				}
			}
			this.vars[i][j][1] = orientation[1];
		}
		return orientation;
	}

	public Piece[][] getMyLevelToSolve() {
		return this.m_myLevelToSolve;
	}
	
	 /**
     * Method to invoke to solve the level instance. 
     * @return true if the solver has been able to solve the grid, false otherwise
     */

	public boolean solving(Extend extend) {
		/*ParallelPortfolio portfolio = new ParallelPortfolio();
		for (int i = 0; i < this.m_nbThreads; i++) {
			portfolio.addModel(this.createModel((short) i, extend));
		}*/
		this.createModel((short)0, Extend.noExtend) ; 
		this.m_solved = this.m_lastModel.getSolver().solve() ; 
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

	private boolean divideReign() {
		short nbIter = 0;
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
			divide = 8;
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
		BoolVar[][][] variss = new BoolVar[m_myLevelToSolve.length][m_myLevelToSolve.length][4];
		for (int i = 0; i < m_myLevelToSolve.length; i += m_myLevelToSolve.length / divide) {
			short nbIterj = 0;
			for (int j = 0; j < m_myLevelToSolve.length; j += m_myLevelToSolve.length / divide) {
				Piece[][] tabsem = new Piece[m_myLevelToSolve.length / divide][m_myLevelToSolve.length / divide];
				for (int r = 0; r < tabsem.length; r++) {
					for (int z = 0; z < tabsem[0].length; z++) {
						tabsem[r][z] = m_myLevelToSolve[r + nbIter * (m_myLevelToSolve.length / divide)][z
								+ nbIter * (m_myLevelToSolve.length / divide)];
					}
				}
				Csp monCsp = new Csp(tabsem, this.m_nbThreads);
				boolean aa = monCsp.solving(Extend.allExtend);
				for (int z = 0; z < monCsp.getTab().length; z++) {
					for (int zz = 0; zz < monCsp.getTab()[0].length; zz++) {
						variss[z + nbIter * (m_myLevelToSolve.length / divide)][zz
								+ nbIterj * (m_myLevelToSolve.length / divide)] = monCsp.getTab()[z][zz];
					}
				}
				nbIterj++;
			}
			nbIter++;
		}
		
		return true;
	}

	private void guessOrientation(int i, int j, BoolVar[] open) {
		Class<? extends Piece> myClass = m_myLevelToSolve[i][j].getClass();
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

	private BoolVar[][][] getTab() {
		return this.vars;
	}


	 /**
    * Method to invoke to solve the level instance. All possibilities will be returned in a ArrayList
    * @return arrayList of all possibilities of solution
    */

	public ArrayList<Piece[][]> getAllSolutions(Extend extend) throws CloneNotSupportedException {
		this.initConstraint(extend);
		ArrayList<Piece[][]> myTabs = new ArrayList<Piece[][]>();
		BoolVar[] orientation = new BoolVar[this.vars.length * this.vars[0].length * this.vars[0][0].length];
		int index = 0;
		for (int i = 0; i < this.vars.length; i++) {
			for (int j = 0; j < this.vars[0].length; j++) {
				for (int z = 0; z < this.vars[0][0].length; z++) {
					orientation[index] = this.vars[i][j][z];
					index++;
				}
			}
		}
		this.m_lastModel.getSolver().limitTime("65s");
		this.m_lastModel.getSolver().setSearch(bestBound(minDomUBSearch(orientation)));
		while (this.m_lastModel.getSolver().solve()) {
			for (int i = 0; i < m_myLevelToSolve.length; i++) {
				for (int j = 0; j < this.m_myLevelToSolve[0].length; j++) {
					for (int z = 0; z < Orientation.values().length; z++) {
						this.guessOrientation(i, j, vars[i][j]);
					}
				}
			}
			myTabs.add(copyGrid(m_myLevelToSolve));
		}
		return myTabs;
	}

	public Piece[][] getGrid() {
		return this.m_myLevelToSolve;
	}

	public Piece[][] copyGrid(Piece[][] myGrid) throws CloneNotSupportedException {
		Piece[][] retur = new Piece[myGrid.length][myGrid[0].length];
		for (int i = 0; i < myGrid.length; i++) {
			for (int j = 0; j < myGrid[0].length; j++) {
				retur[i][j] = (Piece) myGrid[i][j].clone();
			}
		}
		return retur;
	}
	 /**
	    * Method to invoke to create a model with constraint and a search heuristic
	    * @return the model
	    */
	public Model createModel(short search, Extend extend) {
		BoolVar[] orientation = null;
		switch (search) {
		case 0:
			this.m_lastModel = new Model("My Problem");
			this.initConstraint(extend);
			orientation = this.ArrayTo1D();
			this.m_lastModel.getSolver().setSearch(bestBound(minDomUBSearch(orientation)));
			this.m_lastModel.getSolver().limitTime("65s");
			return this.m_lastModel;
		case 1:
			this.m_lastModel = new Model("My Problem");
			this.initConstraint(extend);
			orientation = this.ArrayTo1D();
			this.m_lastModel.getSolver().setSearch((minDomUBSearch(orientation)));
			this.m_lastModel.getSolver().limitTime("65s");
			return this.m_lastModel;
		case 2:
			this.m_lastModel = new Model("My Problem");
			this.initConstraint(extend);
			orientation = this.ArrayTo1D();
			this.m_lastModel.getSolver().setSearch((minDomUBSearch(orientation)));
			this.m_lastModel.getSolver().limitTime("65s");
			return this.m_lastModel;
		case 3:
			this.m_lastModel = new Model("My Problem");
			this.initConstraint(extend);
			orientation = this.ArrayTo1D();
			this.m_lastModel.getSolver().setSearch((minDomUBSearch(orientation)));
			this.m_lastModel.getSolver().limitTime("65s");
			return this.m_lastModel;
		}
		return this.m_lastModel;
	}

	private BoolVar[] ArrayTo1D() {
		BoolVar[] orientation = new BoolVar[this.vars.length * this.vars[0].length * this.vars[0][0].length];
		int index = 0;
		for (int i = 0; i < this.vars.length; i++) {
			for (int j = 0; j < this.vars[0].length; j++) {
				for (int z = 0; z < this.vars[0][0].length; z++) {
					orientation[index] = this.vars[i][j][z];
					index++;
				}
			}
		}
		return orientation;
	}
	

}
