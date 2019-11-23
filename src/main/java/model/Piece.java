package model;

import java.util.HashMap;

public abstract class Piece {
	protected HashMap<Orientation, Piece> neighbor;
	protected int id;
	protected int orientation;

	public HashMap<Orientation, Piece> getNear() {
		return neighbor;
	}

//	public void setNear(HashMap<Orientation, Piece> neighbor) { pas encore finie
//		this.neighbor = neighbor;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public abstract void translation(Side side);

}
