package model.pieces;

import java.io.FileNotFoundException;

import model.enumtype.Orientation;
import model.enumtype.Side;
import view.pieces.PieceDrawing;

public class Empty extends Piece {
	/**
	 * @param orientation The orientation of the Empty Piece
	 * @param line_number The line number of the Empty Piece
	 * @param column_number The column number of the Empty Piece
	 * 
	 *		initializes the attributes of the Empty Piece
	 * 
	 */
	public Empty(int orientation, int line_number, int column_number) {
		super(orientation, line_number, column_number);
		this.id = 0;
		this.numberOfOrientations = 0;
	}

	@Override
	public void translation(Side side) {
		// TODO Auto-generated method stub

	}

	@Override
	public PieceDrawing createDrawing() throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int numberOfConnection() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isConnectedTo(Orientation orientation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean connectedAll() {
		// TODO Auto-generated method stub
		return true;
	}

	public String toString() {
		return " "; // Empty piece should not be seen on screen. See later what character we can
					// print instead if needed

	}

	@Override
	public int numbeOfPossibleConnection() {
		// TODO Auto-generated method stub
		return 0;
	}
}
