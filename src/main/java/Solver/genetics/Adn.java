package Solver.genetics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.chocosolver.solver.variables.BoolVar;

import com.sun.prism.PhongMaterial.MapType;

import Solver.Csp;
import Solver.Extend;
import model.Level;
import model.enumtype.Orientation;
import model.io.FileReader;
import model.pieces.Empty;
import model.pieces.Piece;
import model.pieces.X;

public class Adn implements Cloneable {
	private Piece[][] m_grid;
	private int m_fitness;
	private int m_fitnessGoal = 0;
	private ArrayList<Piece> m_wrongOrientation;
	private ArrayList<Piece> m_goodOrientation;
	private ArrayList<ArrayList<Piece[][]>> mySolutions;
	private Random m_myRandom;

	public Adn(Piece[][] myAdn) {
		this.m_grid = myAdn;
		new Level(this.m_grid).init_neighbors();
		this.m_goodOrientation = new ArrayList<Piece>();
		this.m_wrongOrientation = new ArrayList<Piece>();
		mySolutions = new ArrayList<ArrayList<Piece[][]>>();
		m_myRandom = new Random();
		short nbIter = 0;
		Csp moncsp = null;
		int divide = 0;
		switch (this.m_grid.length) {
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
			divide = 16;
			break;
		case 512:
			divide = 64;
			break;
		case 1024:
			divide = 128;
			break;
		}
		for (int i = 0; i < m_grid.length; i += m_grid.length / divide) {
			short nbIterj = 0;
			for (int j = 0; j < m_grid.length; j += m_grid.length / divide) {
				Piece[][] tabsem = new Piece[m_grid.length / divide][m_grid.length / divide];
				for (int r = 0; r < tabsem.length; r++) {
					for (int z = 0; z < tabsem[0].length; z++) {
						tabsem[r][z] = m_grid[r + nbIter * (m_grid.length / divide)][z
								+ nbIterj * (m_grid.length / divide)];
					}
				}
			//	moncsp = new Csp(tabsem);
			//	this.mySolutions.add(moncsp.getAllSolutions((Extend.allExtend)));
				/*if (i == 0 && j == 0) {
					ArrayList<Piece[][]> a = moncsp.getAllSolutions((Extend.northWest));
				} else if (i + m_grid.length / divide >= m_grid.length && j + m_grid.length / divide >= m_grid.length) {
					ArrayList<Piece[][]> a = moncsp.getAllSolutions((Extend.southEast));
					this.mySolutions.add(a);
				} else if (i == 0 && j + m_grid.length / divide >= m_grid.length) {
					ArrayList<Piece[][]> a = moncsp.getAllSolutions((Extend.northEast));
					this.mySolutions.add(a);
				} else if (i != 0 && j == 0) {
					this.mySolutions.add(moncsp.getAllSolutions((Extend.west)));
				} else {
					this.mySolutions.add(moncsp.getAllSolutions((Extend.allExtend)));
					
				}*/
				nbIterj++;
			}
			nbIter++;
		}
		for (int i = 0; i < myAdn.length; i++) {
			for (int y = 0; y < myAdn[0].length; y++) {
				Class<? extends Piece> myClass = myAdn[i][y].getClass();
				switch (myClass.getName()) {
				case "model.pieces.Bar":
					m_fitnessGoal += myAdn[i][y].numbeOfPossibleConnection();
					break;
				case "model.pieces.Empty":
					break;
				case "model.pieces.X":
					m_fitnessGoal += myAdn[i][y].numbeOfPossibleConnection();
					break;
				default:
					m_fitnessGoal += myAdn[i][y].numbeOfPossibleConnection();
					break;
				}
			}
		}
		this.updateFitness();
		for (int i = 0; i < myAdn.length; i++) {
			for (int y = 0; y < myAdn[0].length; y++) {
				if (myAdn[i][y].numbeOfPossibleConnection() == myAdn[i][y].numberOfConnection()) {
					this.m_goodOrientation.add(myAdn[i][y]);
				} else {
					this.m_wrongOrientation.add(myAdn[i][y]);
				}
			}
		}
		this.calSolution();
	}

	public Adn(Piece[][] myAdn, int fitness, int goal, ArrayList<ArrayList<Piece[][]>> array) {
		this.m_grid = myAdn;
		this.m_fitness = fitness;
		this.m_fitnessGoal = goal;
		this.m_goodOrientation = new ArrayList<Piece>();
		this.m_wrongOrientation = new ArrayList<Piece>();
		this.mySolutions = array;
		m_myRandom = new Random();
		for (int i = 0; i < myAdn.length; i++) {
			for (int y = 0; y < myAdn[0].length; y++) {
				if (myAdn[i][y].numbeOfPossibleConnection() == myAdn[i][y].numberOfConnection()) {
					this.m_goodOrientation.add(myAdn[i][y]);
				} else {
					this.m_wrongOrientation.add(myAdn[i][y]);
				}
			}
		}
	}

	public void updateFitness() {
		new Level(this.m_grid).init_neighbors();
		int fitness = 0;
		for (int i = 0; i < this.m_grid.length; i++) {
			for (int y = 0; y < this.m_grid[0].length; y++) {
				fitness += this.m_grid[i][y].numberOfConnection();
			}
		}
		this.m_fitness = fitness;
	}

	public int getFitness() {
		return this.m_fitness;
	}

	public Piece[][] getGrid() {
		return this.m_grid;
	}

	public void crossMySelf(short strategie) {
		int index = 0;
		if (this.m_wrongOrientation.size() > 0) {
			index = m_myRandom.nextInt(this.m_wrongOrientation.size());
		} else {
			return;
		}
		Piece selectedPiece = this.m_wrongOrientation.get(index);
		Class myClass = m_grid[selectedPiece.getLine_number()][selectedPiece.getColumn_number()].getClass();
		short orientationMax = 0;
		switch (myClass.getName()) {
		case "model.pieces.Bar":
			orientationMax = 2;
			break;
		case "model.pieces.Empty":
			break;
		case "model.pieces.X":
			break;
		default:
			orientationMax = 4;
			break;
		}
		switch (strategie) {
		case 0:
			/*
			 * int nbVoisin = 0; short orientation = (short) selectedPiece.getOrientation();
			 * for (int i = 0; i <= orientationMax; i++) { selectedPiece.setOrientation(i);
			 * new Level(this.m_grid).init_neighbors(); int nb =
			 * selectedPiece.numberOfConnection(); if (nb > nbVoisin) { nbVoisin = nb;
			 * orientation = (short) i; } } selectedPiece.setOrientation(orientation);
			 */
			for (int i = 0; i < this.mySolutions.size(); i++) {
				Piece[][] temp = this.mySolutions.get(i).get(this.m_myRandom.nextInt(this.mySolutions.get(i).size()));
				for (int z = 0; z < temp.length; z++) {
					for (int j = 0; j < temp[0].length; j++) {
						this.m_grid[temp[z][j].getLine_number()][temp[z][j].getColumn_number()] = temp[z][j];
					}
				}
			}
			this.updateFitness();
			System.out.println(this.m_fitness + "--->" + this.goal());
			break;
		case 1:
			int offSet = m_myRandom.nextInt(orientationMax);
			if (selectedPiece.getOrientation() - offSet > 0 && m_myRandom.nextBoolean()) {
				selectedPiece.setOrientation(selectedPiece.getOrientation() - offSet);
			} else if (selectedPiece.getOrientation() + offSet < orientationMax) {
				selectedPiece.setOrientation(selectedPiece.getOrientation() + offSet);
			}
			break;
		}
		if (selectedPiece.connectedAll())
			this.m_goodOrientation.add(selectedPiece);
		this.m_wrongOrientation.remove(index);
		if (m_myRandom.nextFloat() >= 0)
			// this.mutation();
			this.updateFitness();
		if (this.m_fitness == this.m_fitnessGoal)
			System.out.println("Finish");
		new Level(this.m_grid).init_neighbors();
	}

	public void mutation() {
		int nbPiece = 0;
		if (this.m_wrongOrientation.size() > 0) {
			nbPiece = this.m_wrongOrientation.size();
		} else {
			return;
		}
		int index = this.m_myRandom.nextInt(nbPiece);
		Piece selectedPiece = null;
		HashMap<Orientation, Piece> map = this.m_wrongOrientation.get(index).getNeighbor();
		List<Orientation> keys = new ArrayList(map.keySet());
		int nbNeigh = keys.size();
		int nbTentative = 0;
		while (selectedPiece == null) {
			int choix = this.m_myRandom.nextInt(nbNeigh);
			if (!this.m_wrongOrientation.get(index).isConnectedTo(keys.get(choix))) {
				selectedPiece = map.get(keys.get(choix));
			}
			if (nbTentative == 10) {
				selectedPiece = this.m_wrongOrientation.get(index);
			}
			nbTentative++;
		}
		for (Orientation e : map.keySet()) {
			if (!this.m_wrongOrientation.get(index).isConnectedTo(e)) {
				selectedPiece = map.get(e);
				break;
			}
		}
		if (selectedPiece == null) {
			selectedPiece = this.m_wrongOrientation.get(index);
		}
		Class<? extends Piece> myClass = m_grid[selectedPiece.getLine_number()][selectedPiece.getColumn_number()].getClass();
		short orientationMax = 0;
		switch (myClass.getName()) {
		case "model.pieces.Bar":
			orientationMax = 2;
			break;
		case "model.pieces.Empty":
			break;
		case "model.pieces.X":
			break;
		default:
			orientationMax = 4;
			break;
		}
		if (selectedPiece.getOrientation() - 1 > 0 && m_myRandom.nextBoolean()) {
			selectedPiece.setOrientation(selectedPiece.getOrientation() - 1);
		} else if (selectedPiece.getOrientation() + 1 < orientationMax) {
			selectedPiece.setOrientation(selectedPiece.getOrientation() + 1);
		}
		this.updateFitness();
		if (this.m_goodOrientation.contains(selectedPiece)) {
			if (!selectedPiece.connectedAll()) {
				this.m_goodOrientation.remove(selectedPiece);
				this.m_wrongOrientation.add(selectedPiece);
			}
		} else {
			if (selectedPiece.connectedAll()) {
				this.m_goodOrientation.add(selectedPiece);
				this.m_wrongOrientation.remove(selectedPiece);
			}
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Piece[][] myPieces = new Piece[m_grid.length][m_grid[0].length];
		for (int i = 0; i < m_grid.length; i++) {
			for (int y = 0; y < m_grid[0].length; y++) {
				myPieces[i][y] = (Piece) m_grid[i][y].clone();
			}
		}
		return new Adn(myPieces, this.getFitness(), this.goal(), this.mySolutions);
	}

	public int goal() {
		return this.m_fitnessGoal;
	}

	public void calSolution() {
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		Piece[][] test = FileReader.getGrid(
				"C:\\Users\\Bilal\\git\\phineloops-kby\\instances\\public\\grid_256x256_dist.1_vflip.false_hflip.true_messedup.false_id.1.dat",
				" ");
		Piece[][] test2 = new Piece[3][3];
		test2[0][0] = new X(0, 0, 0);
		test2[0][1] = new X(0, 0, 1);
		test2[0][2] = new Empty(0, 0, 2);
		test2[1][0] = new Empty(0, 1, 0);
		test2[1][1] = new Empty(0, 1, 1);
		test2[1][2] = new Empty(0, 1, 2);
		test2[2][0] = new Empty(0, 2, 0);
		test2[2][1] = new Empty(0, 2, 1);
		test2[2][2] = new Empty(0, 2, 2);
		new Level(test2).init_neighbors();
		Adn monAdn = new Adn(test);
		while (true) {
			Adn enfant = (Adn) monAdn.clone();
			monAdn.crossMySelf((short) 0);
			if (monAdn.getFitness() < enfant.getFitness()) {
				// System.out.println(enfant.getFitness() + "----->" + enfant.goal() + "---->"
				// + new Level(enfant.getGrid()).checkGrid());
				monAdn = enfant;
				if (enfant.getFitness() == enfant.goal()) {
					System.out.println("");
				}
			} else {
			}
		}
	}
}
