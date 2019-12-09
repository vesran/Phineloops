package model;

import view.PieceDrawing;
import view.TPieceDrawing;

import java.io.FileNotFoundException;

public class T extends Piece {

	public T(int orientation, int line_number, int column_number) {
		super(orientation, line_number, column_number);
		this.id = 3;
		// TODO Auto-generated constructor stub
	} 

	@Override
	public void translation(Side side) {
		// TODO Auto-generated method stub
		if (side == Side.RIGHT)
			if (this.orientation == 0)
				this.orientation = 3;
			else
				this.orientation--;
		else if (this.orientation == 3)
			this.orientation = 0;
		else
			this.orientation++;

	}

	@Override
	public PieceDrawing createDrawing() throws FileNotFoundException {
		return new TPieceDrawing(this);
	}

	@Override
	public int numberOfConnection() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		String str = "";
		switch(this.orientation) {
			case 0:
				str = "\u2534";
				break;
			case 1:
				str = "\u251C";
				break;
			case 2:
				str = "\u252C";
				break;
			case 3:
				str = "\u2524";
				break;
			default:
				throw new IllegalStateException("Orientation of T piece " + this.orientation + "should not exist");
		}
		return str;
	}

}
