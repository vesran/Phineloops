package model;

import view.PieceDrawing;
import view.XPieceDrawing;

import java.io.FileNotFoundException;

public class X extends Piece {

	public X(int orientation, int line_number, int column_number) {
		super(orientation, line_number, column_number);
		this.id = 4;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void translation(Side side) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PieceDrawing createDrawing() throws FileNotFoundException {
		return new XPieceDrawing(this);
	}


}
