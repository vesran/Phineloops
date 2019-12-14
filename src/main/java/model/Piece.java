package model;

import view.PieceDrawing;

import java.io.FileNotFoundException;
import java.util.HashMap;

public abstract class Piece {
	protected HashMap<Orientation, Piece> neighbor;
	protected int id;
	protected int orientation;
	protected int line_number;
	protected int column_number;

	public Piece getOneNeighbor(Orientation orientation) { // rajouter une exception ? pour le cas ou le voisin n'existe
															// pas ? ou bien on suppose que le jeu est parfaitement créé
															// avant de commencer à verifier
		return this.neighbor.get(orientation);
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
		this.neighbor = new HashMap<Orientation, Piece>();
	}

	public Piece() {

	}

	public HashMap<Orientation, Piece> init_neighbors() {// � travailler

		return neighbor;
	}

	public void addNeighbor(Piece piece, Orientation orientation) {
		this.neighbor.put(orientation, piece);
	}
	
	public abstract boolean connectedAll();

	public abstract void translation(Side side);

	public abstract PieceDrawing createDrawing() throws FileNotFoundException;

	public abstract int numberOfConnection();

	public abstract boolean isConnectedTo(Orientation orientation);
}
