package model;

import java.util.HashMap;

public abstract class Piece {
	protected HashMap<Orientation, Piece> neighbor;
	protected int id;
	protected int orientation;
	protected int line_number;
	protected int column_number;

	public HashMap<Orientation, Piece> getNear() {
		return neighbor;
	}

	public HashMap<Orientation, Piece> getNeighbor() {
		return neighbor;
	}

	public void setNeighbor(HashMap<Orientation, Piece> neighbor) {
		this.neighbor = neighbor;
	}

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

	public int getLine_number() {
		return line_number;
	}

	public void setLine_number(int line_number) {
		this.line_number = line_number;
	}

	public int getColumn_number() {
		return column_number;
	}

	public void setColumn_number(int column_number) {
		this.column_number = column_number;
	}

	public Piece(int orientation, int line_number, int column_number) {
		this.orientation = orientation;
		this.line_number = line_number;
		this.column_number = column_number;
	}

	public HashMap<Orientation, Piece> init_neighbors() {// à travailler
		
		return neighbor; 
	}

	public abstract void translation(Side side);

}
