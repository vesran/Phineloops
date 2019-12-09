package model;

import view.BarPieceDrawing;
import view.PieceDrawing;

import java.io.FileNotFoundException;

public class Bar extends Piece {

	public Bar(int orientation, int line_number, int column_number) {
		super(orientation, line_number, column_number);
		this.id = 2;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void translation(Side side) {
		// TODO Auto-generated method stub
		if (this.orientation == 1)
			this.orientation = 0;
		else
			this.orientation = 1;
	}

	@Override
	public PieceDrawing createDrawing() throws FileNotFoundException {
		return new BarPieceDrawing(this);
	}

	@Override
	public int numberOfConnection() {
		
		return 0;
	}

	@Override
	public String toString() {
		String str = "";
		switch(this.orientation) {
			case 0:
				str = "\u2502";
				break;
			case 1:
				str = "\u2500";
				break;
			default:
				throw new IllegalStateException("Orientation of Bar piece " + this.orientation + "should not exist");
		}
		return str;
	}

}
