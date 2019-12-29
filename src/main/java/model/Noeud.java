package model;

import java.util.Map.Entry;

public class Noeud extends ObjectOfMaze {

	public Noeud(int x, int y, int pere) {
		this.x = x;
		this.y = y;
		this.pere = pere;
	}

	protected String printMap() {
		return " h " + this.murs.get(0) + " d " + this.murs.get(1) + " b " + this.murs.get(2) + " g "
				+ this.murs.get(3);
	}

	protected <T extends ObjectOfMaze> void union(int pere, T m[][], int width, int height) {
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				T u = m[i][j];
				if (u.pere == this.pere && u != this) {
					m[i][j].pere = pere;
				}
			}
		this.pere = pere;
	}

	protected int numberOfNeighbors() {
		int i = 0;
		for (Entry<Integer, Boolean> mapentry : this.murs.entrySet())
			if (mapentry.getValue() == true)
				i++;
		return i;
	}

	private boolean PossibleToCreateEmpty(Noeud m[][], int height, int width) {
		boolean r = false;
		if (this.murs.get(0).booleanValue())
			if (m[x - 1][y].numberOfNeighbors() > 1)
				r = true;
		if (this.murs.get(1).booleanValue())
			if (m[x][y + 1].numberOfNeighbors() > 1)
				r = true;
		if (this.murs.get(2).booleanValue())
			if (m[x + 1][y].numberOfNeighbors() > 1)
				r = true;
		if (this.murs.get(3).booleanValue())
			if (m[x][y - 1].numberOfNeighbors() > 1)
				r = true;
		return r;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " x = " + this.x + " y = " + this.y;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Noeud n = (Noeud) obj;
		return this.x == n.x && this.y == n.y;
	}

	@Override
	protected <T extends ObjectOfMaze> int sizeOfConnexcomposant(T[][] m, int height, int width) {
		int size = 0;
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				if (m[i][j].pere == this.pere && this != m[i][j])
					size++;
		return size;
	}

	@Override
	protected <T extends ObjectOfMaze> void replaceValue(T[][] m) {
		if (this.murs.get(0).booleanValue())
			m[this.x - 1][this.y].murs.replace(2, false);
		if (this.murs.get(1).booleanValue())
			m[this.x][this.y + 1].murs.replace(3, false);
		if (this.murs.get(2).booleanValue())
			m[this.x + 1][this.y].murs.replace(0, false);
		if (this.murs.get(3).booleanValue())
			m[this.x][this.y - 1].murs.replace(1, false);
	}
}
