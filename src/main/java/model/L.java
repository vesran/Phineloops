package model;

import view.LPieceDrawing;
import view.PieceDrawing;

import java.io.FileNotFoundException;

public class L extends Piece {

	public L(int orientation, int line_number, int column_number) {
		super(orientation, line_number, column_number);
		this.id = 5;
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
		return new LPieceDrawing(this);
	}

	@Override
	public int numberOfConnection() {
		int r =0;
		if(this.orientation == 0) {
			if (this.getOneNeighbor(Orientation.NORTH).isConnectedTo(Orientation.SOUTH))
				r++;
			if (this.getOneNeighbor(Orientation.EAST).isConnectedTo(Orientation.WEST))
				r++;
		}
		if(this.orientation ==1) {
			if (this.getOneNeighbor(Orientation.SOUTH).isConnectedTo(Orientation.NORTH))
				r++;
			if (this.getOneNeighbor(Orientation.EAST).isConnectedTo(Orientation.WEST))
				r++;
		}
		if(this.orientation == 2) {
			if (this.getOneNeighbor(Orientation.WEST).isConnectedTo(Orientation.EAST))
				r++;
			if (this.getOneNeighbor(Orientation.SOUTH).isConnectedTo(Orientation.NORTH))
				r++;
		}
		if(this.orientation == 3) {
			if (this.getOneNeighbor(Orientation.NORTH).isConnectedTo(Orientation.SOUTH))
				r++;
			if (this.getOneNeighbor(Orientation.WEST).isConnectedTo(Orientation.EAST))
				r++;
		}
			
		return r;
	}

	@Override
	public boolean isConnectedTo(Orientation orientation) {
		if (this.neighbor.containsKey(orientation)) {
			if (orientation == Orientation.NORTH && (this.id == 0 || this.id == 3))
				return true;
			if (orientation == Orientation.EAST && (this.id == 1 || this.id == 0))
				return true;
			if (orientation == Orientation.SOUTH && (this.id == 2 || this.id == 1))
				return true;
			if (orientation == Orientation.WEST && (this.id == 3 || this.id == 2))
				return true;
		}
		return false;
	}

	@Override
	public boolean connectedAll() {
		// TODO Auto-generated method stub
		return this.numberOfConnection() == 2;
	}

	@Override
	public String toString() {
		String str = "";
		switch(this.orientation) {
			case 0:
				str = "\u2514";
				break;
			case 1:
				str = "\u250C";
				break;
			case 2:
				str = "\u2510";
				break;
			case 3:
				str = "\u2518";
				break;
			default:
				throw new IllegalStateException("Orientation of L piece " + this.orientation + "should not exist");
		}
		return str;
	}
}
