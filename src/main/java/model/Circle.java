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
		if ((this.orientation == 0 && this.getOneNeighbor(Orientation.NORTH).isConnectedTo(Orientation.SOUTH))
				|| (this.id == 1 && this.getOneNeighbor(Orientation.EAST).isConnectedTo(Orientation.WEST))
				|| (this.id == 2 && this.getOneNeighbor(Orientation.SOUTH).isConnectedTo(Orientation.NORTH))
				|| (this.id == 3 && this.getOneNeighbor(Orientation.WEST).isConnectedTo(Orientation.EAST)))
			return 1;
		else
			return 0;
	}

	@Override
	public boolean isConnectedTo(Orientation orientation) {
		if (this.neighbor.containsKey(orientation)) {
			if (orientation == Orientation.NORTH && this.id == 0)
				return true;
			if (orientation == Orientation.EAST && this.id == 1)
				return true;
			if (orientation == Orientation.SOUTH && this.id == 2)
				return true;
			if (orientation == Orientation.WEST && this.id == 3)
				return true;
		}
		return false;
	}

	@Override
	public boolean connectedAll() {
		// TODO Auto-generated method stub
		return this.numberOfConnection() == 1;
	}

}
