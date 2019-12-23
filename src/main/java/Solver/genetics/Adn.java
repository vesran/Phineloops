package Solver.genetics;

import java.util.ArrayList;
import java.util.Random;

import model.pieces.Piece;

public class Adn {
	private Piece[][] m_grid;
	private int m_fitness;
	private int m_fitnessGoal = 0;
	private ArrayList<Piece> m_wrongOrientation;
	private ArrayList<Piece> m_goodOrientation;
	private Random m_myRandom;

	public Adn(Piece[][] myAdn) {
		this.m_grid = myAdn;
		this.m_goodOrientation = new ArrayList<Piece>();
		this.m_wrongOrientation = new ArrayList<Piece>();
		m_myRandom = new Random();
		for (int i = 0; i < myAdn.length; i++) {
			for (int y = 0; y < myAdn[0].length; y++) {
				Class myClass = myAdn[i][y].getClass();
				Random monRand = new Random();
				switch (myClass.getName()) {
				case "model.pieces.Bar":
					myAdn[i][y].setOrientation(monRand.nextInt(3));
					m_fitnessGoal += myAdn[i][y].numbeOfPossibleConnection();
					break;
				case "model.pieces.Empty":
					break;
				case "model.pieces.X":
					m_fitnessGoal += myAdn[i][y].numbeOfPossibleConnection();
					break;
				default:
					myAdn[i][y].setOrientation(monRand.nextInt(4));
					m_fitnessGoal += myAdn[i][y].numbeOfPossibleConnection();
					break;
				}
			}
		}
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

	public Adn(Piece[][] myAdn , int fitness) {
		
	}
	public void updateFitness() {
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

	public void crossMySelf(short strategie) {
		int index = m_myRandom.nextInt(this.m_wrongOrientation.size());
		Piece selectedPiece = this.m_wrongOrientation.get(index);
		Class myClass = m_grid[selectedPiece.getLine_number()][selectedPiece.getColumn_number()].getClass();
		Random monRand = new Random();
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
			if (selectedPiece.getOrientation() - 1 > 0 && m_myRandom.nextBoolean()) {
				selectedPiece.setOrientation(selectedPiece.getOrientation() - 1);
			} else if (selectedPiece.getOrientation() + 1 < orientationMax) {
				selectedPiece.setOrientation(selectedPiece.getOrientation() + 1);
			}
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
		if (m_myRandom.nextFloat() >= 0.95)
			this.mutation();
		this.updateFitness();
		if (selectedPiece.connectedAll())
			this.m_goodOrientation.add(selectedPiece);
		this.m_wrongOrientation.remove(index);
		if (this.m_fitness == this.m_fitnessGoal)
			System.out.println("Finish");
	}

	public void mutation() {
		int nbPiece = this.m_goodOrientation.size();
		for (int i = 0; i < 0.1 * nbPiece; i++) {
			int index = this.m_myRandom.nextInt(nbPiece);
			Piece selectedPiece = this.m_goodOrientation.get(index);
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
			if (selectedPiece.getOrientation() - 1 > 0 && m_myRandom.nextBoolean()) {
				selectedPiece.setOrientation(selectedPiece.getOrientation() - 1);
			} else if (selectedPiece.getOrientation() + 1 < orientationMax) {
				selectedPiece.setOrientation(selectedPiece.getOrientation() + 1);
			}
		}
	}

@Override
	public Object clone() {
	
		
		
	}


}
