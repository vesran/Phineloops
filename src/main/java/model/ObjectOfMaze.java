package model;

import java.util.HashMap;
import java.util.Random;

public abstract class ObjectOfMaze {
	HashMap<Integer, Boolean> murs;
	protected int x, y;
	protected int pere;
	
	public ObjectOfMaze() {
		this.murs = new HashMap<Integer, Boolean>();
		this.murs.put(0, false);
		this.murs.put(1, false);
		this.murs.put(2, false);
		this.murs.put(3, false);
	}
	
	protected int find() {
		return this.pere;
	}
	protected abstract <T extends ObjectOfMaze> void replaceValue(T m[][]);
	protected abstract <T extends ObjectOfMaze> int sizeOfConnexcomposant(T m[][], int height, int width);
}

