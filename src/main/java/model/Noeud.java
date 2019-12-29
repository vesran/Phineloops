package model;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

public class Noeud {
	HashMap<Integer, Boolean> murs;
	protected int x, y;
	protected int pere;
	protected Boolean empty;
	protected boolean explored;

	public Noeud(int x, int y, int pere) {
		this.murs = new HashMap<Integer, Boolean>();
		this.murs.put(0, false);
		this.murs.put(1, false);
		this.murs.put(2, false);
		this.murs.put(3, false);
		Random random = new Random();
		this.x = x;
		this.y = y;
		this.pere = pere;
		if (random.nextInt(10) != 0) {
			this.empty = false;
			this.explored = false;
		} else {
			this.empty = true;
			this.explored = true;
		}
	}

	public String printMap() {
		return " h " + this.murs.get(0) + " d " + this.murs.get(1) + " b " + this.murs.get(2) + " g "
				+ this.murs.get(3);
	}

	public void union(int pere, Noeud m[][], int width, int height) {
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				Noeud u = m[i][j];
				if (u.pere == this.pere && u != this) {
					m[i][j].pere = pere;
				}
			}

		this.pere = pere;

	}

	public int numberOfNeighbors() {
		int i = 0;
		for (Entry<Integer, Boolean> mapentry : this.murs.entrySet())
			if (mapentry.getValue() == true)
				i++;
		return i;
	}

	public void replaceValue(Noeud m[][]) {
		if (this.murs.get(0).booleanValue())
			m[this.x - 1][this.y].murs.replace(2, false);
		if (this.murs.get(1).booleanValue())
			m[this.x][this.y + 1].murs.replace(3, false);
		if (this.murs.get(2).booleanValue())
			m[this.x + 1][this.y].murs.replace(0, false);
		if (this.murs.get(3).booleanValue())
			m[this.x][this.y - 1].murs.replace(1, false);
	}

	public boolean PossibleToCreateEmpty(Noeud m[][], int height, int width) {
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

	public int sizeOfConnexcomposant(Noeud m[][], int height, int width) {
		int size = 0;
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				if (m[i][j].pere == this.pere && this != m[i][j])
					size++;
		return size;
	}

	public int find() {
		return this.pere;
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
}
