package model;

import java.util.HashMap;

public class Empty {
	protected HashMap<Orientation, Piece> neighbor;
	protected int id;
	protected int line_number; 
	protected int column_number;

	public Empty(int id, int line_number, int column_number) {
		this.id = id;
		this.line_number = line_number;
		this.column_number = column_number;
	}
}
