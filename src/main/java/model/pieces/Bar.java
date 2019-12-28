package model.pieces;

import model.enumtype.Orientation;
import model.enumtype.Side;
import view.pieces.BarPieceDrawing;
import view.pieces.PieceDrawing;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class Bar extends Piece {

	/**
	 * @param orientation The orientation of the bar
	 * @param line_number The line number of the bar
	 * @param column_number The column number of the bar
	 * 
	 *		initializes the attributes of the Bar
	 * 
	 */
	public Bar(int orientation, int line_number, int column_number) {
		super(orientation, line_number, column_number);
		this.id = 2;
		this.numberOfOrientations = 2;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void translation(Side side) {
		int oldOrientation = this.orientation;
		// TODO Auto-generated method stub
		if (this.orientation == 1)
			this.orientation = 0;
		else
			this.orientation = 1;

		if (side == Side.LEFT)	this.pcs.firePropertyChange("leftTranslation", oldOrientation, this.orientation);
		else	this.pcs.firePropertyChange("rightTranslation", oldOrientation, this.orientation);
	}

	
	@Override
	public PieceDrawing createDrawing() {
		return new BarPieceDrawing(this);
	}

	
	@Override
	public int numberOfConnection() {
		int r = 0;
		if (this.orientation == 0) {
			if (this.getOneNeighbor(Orientation.NORTH) != null)
				if (this.getOneNeighbor(Orientation.NORTH).isConnectedTo(Orientation.SOUTH))
					r++;
			if (this.getOneNeighbor(Orientation.SOUTH) != null)
				if (this.getOneNeighbor(Orientation.SOUTH).isConnectedTo(Orientation.NORTH))
					r++;
		} else {
			if (this.getOneNeighbor(Orientation.EAST) != null)
				if (this.getOneNeighbor(Orientation.EAST).isConnectedTo(Orientation.WEST))
					r++;
			if (this.getOneNeighbor(Orientation.WEST) != null)
				if (this.getOneNeighbor(Orientation.WEST).isConnectedTo(Orientation.EAST))
					r++;
		}
		return r;
	}

	
	@Override
	public boolean isConnectedTo(Orientation orientation) {
		if (this.neighbor.containsKey(orientation)) {
			if (((orientation == Orientation.NORTH) || (orientation == Orientation.SOUTH)) && (this.orientation == 0))
				return true;
			if (((orientation == Orientation.EAST) || (orientation == Orientation.WEST)) && (this.orientation == 1))
				return true;
		}
		return false;
	}


	@Override
	public boolean connectedAll() {
		// TODO Auto-generated method stub
		return this.numberOfConnection() == this.numbeOfPossibleConnection();
	}

	@Override
	public String toString() {
		String str = "";
		switch (this.orientation) {
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

	@Override
	public int numbeOfPossibleConnection() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public List<Orientation> orientatedTo() {
		if (this.orientation == 0) {
			return Arrays.asList(Orientation.NORTH, Orientation.SOUTH);
		} else if (this.orientation == 1) {
			return Arrays.asList(Orientation.EAST, Orientation.WEST);
		}
		return null;
	}

}
