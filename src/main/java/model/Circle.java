package model;

import view.CirclePieceDrawing;
import view.PieceDrawing;

import java.io.FileNotFoundException;

public class Circle extends Piece {

	public Circle(int orientation, int line_number, int column_number) {
		super(orientation, line_number, column_number);
		this.id = 1;
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
		return new CirclePieceDrawing(this);
	}

	@Override
	public int numberOfConnection() {
		// TODO Auto-generated method stub
		return 0;
	}

}
