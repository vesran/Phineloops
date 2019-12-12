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

	@Override
	public int numberOfConnection() {
		int r = 0;
		if (this.getOneNeighbor(Orientation.NORTH).isConnectedTo(Orientation.SOUTH))
			r++;
		if (this.getOneNeighbor(Orientation.SOUTH).isConnectedTo(Orientation.NORTH))
			r++;
		if (this.getOneNeighbor(Orientation.EAST).isConnectedTo(Orientation.WEST))
			r++;
		if (this.getOneNeighbor(Orientation.WEST).isConnectedTo(Orientation.EAST))
			r++;
		return r;
	}

	@Override
	public boolean isConnectedTo(Orientation orientation) {
		return true;
	}

	@Override
	public boolean connectedAll() {
		// TODO Auto-generated method stub
		return this.numberOfConnection() == 4;
	}

}
