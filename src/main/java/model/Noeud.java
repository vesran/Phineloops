package model;

import java.util.ArrayList;
import java.util.HashMap;
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
		return " h "+this.murs.get(0)+" d "+this.murs.get(1)+" b "+this.murs.get(2)+" g "+this.murs.get(3);
	}

	public void union(int pere, Noeud m[][], int width, int height) {
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				Noeud u = m[i][j];
				if (u.pere == this.pere && u != this) {
//					System.out.println("ouii on modifie "+m[i][j].pere);
					m[i][j].pere = pere;
				}
			}
//		System.out.println("union");
//		for (int i = 0; i < width; i++) {
//			for (int j = 0; j < height; j++)
//				System.out.print(m[i][j].pere+" " );
//			System.out.println();
//		}
//		if (this.x > 0)
//			if (m[this.x - 1][y].pere == this.pere)
//				m[this.x - 1][y].union(pere, m, width, height);
//		if (this.x + 1 < width)
//			if (m[this.x + 1][y].pere == this.pere)
//				m[this.x + 1][y].union(pere, m, width, height);
//		if (this.y + 1 < height)
//			if (m[this.x][y + 1].pere == this.pere)
//				m[this.x][y + 1].union(pere, m, width, height);
//		if (this.y > 0)
//			if (m[this.x][y - 1].pere == this.pere)
//				m[this.x][y - 1].union(pere, m, width, height);

//		for (int i = 0; i < width; i++) 
//			for (int j = 0; j < height; j++)
//				if(m[i][j]==this) {
//					m[i][j].pere=pere;
//			System.out.println(" moi "+ m[i][j]+"voici mon pere" +this.pere);
//				}
//		System.out.println("union2");
//		for (int i = 0; i < width; i++) {
//			for (int j = 0; j < height; j++)
//				System.out.print(m[i][j].pere+" " );
//			System.out.println();
//		}

//		System.out.println("on arrive");
		this.pere = pere;

	}

//	public boolean canExploreNorth(Noeud m[][]) {
//		return this.x > 0 && !m[this.x - 1][this.y].explored;
//	}
//
//	public boolean canExploreSouth(Noeud m[][], int width) {
//		return this.x < width - 1 && !m[this.x + 1][this.y].explored;
//	}
//
//	public boolean canExploreEast(Noeud m[][], int height) {
//		return this.y < height - 1 && !m[this.x][this.y + 1].explored;
//	}
//
//	public boolean canExploreWest(Noeud m[][]) {
//		return this.y > 0 && !m[this.x][this.y - 1].explored;
//	}
//
//	public boolean canExplore(Noeud m[][], int height, int width) {
//		return (this.canExploreEast(m, height) && this.canExploreNorth(m) && this.canExploreSouth(m, width)
//				&& this.canExploreWest(m));
//	}
//
//	public Noeud explore(Noeud m[][], int height, int width) {
//		Random random = new Random();
//		return null;
//	}

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
