package model.pieces;

import model.enumtype.Orientation;
import model.enumtype.Side;
import view.pieces.PieceDrawing;
import view.pieces.TPieceDrawing;

import java.io.FileNotFoundException;

public class T extends Piece {
	/**
	 * @param orientation The orientation of T
	 * @param line_number The line number of T
	 * @param column_number The column number of T
	 * 
	 *		initializes the attributes of T
	 * 
	 */
	public T(int orientation, int line_number, int column_number) {
		super(orientation, line_number, column_number);
		this.id = 3;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void translation(Side side) {
		int oldOrientation = this.orientation;
		// TODO Auto-generated method stub
		if (side == Side.RIGHT) {
			if (this.orientation == 0)
				this.orientation = 3;
			else
				this.orientation--;
			this.pcs.firePropertyChange("rightTranslation", oldOrientation, this.orientation);
		} else {
			if (this.orientation == 3)
				this.orientation = 0;
			else
				this.orientation++;
			this.pcs.firePropertyChange("leftTranslation", oldOrientation, this.orientation);
		}

	}

	@Override
	public PieceDrawing createDrawing() throws FileNotFoundException {
		return new TPieceDrawing(this);
	}

	@Override
	public int numberOfConnection() {
		int r = 0;
		if (this.orientation == 0) {
			if(this.getOneNeighbor(Orientation.NORTH)!=null)
			if (this.getOneNeighbor(Orientation.NORTH).isConnectedTo(Orientation.SOUTH))
				r++;
			if(this.getOneNeighbor(Orientation.EAST)!=null)
			if (this.getOneNeighbor(Orientation.EAST).isConnectedTo(Orientation.WEST))
				r++;
			if(this.getOneNeighbor(Orientation.WEST)!=null)
			if (this.getOneNeighbor(Orientation.WEST).isConnectedTo(Orientation.EAST))
				r++;
		}
		if (this.orientation == 1) {
			if(this.getOneNeighbor(Orientation.NORTH)!=null)
			if (this.getOneNeighbor(Orientation.NORTH).isConnectedTo(Orientation.SOUTH))
				r++;
			if(this.getOneNeighbor(Orientation.SOUTH)!=null)
			if (this.getOneNeighbor(Orientation.SOUTH).isConnectedTo(Orientation.NORTH))
				r++;
			if(this.getOneNeighbor(Orientation.EAST)!=null)
			if (this.getOneNeighbor(Orientation.EAST).isConnectedTo(Orientation.WEST))
				r++;
		}
		if (this.orientation == 2) {
			if(this.getOneNeighbor(Orientation.EAST)!=null)
			if (this.getOneNeighbor(Orientation.EAST).isConnectedTo(Orientation.WEST))
				r++;
			if(this.getOneNeighbor(Orientation.WEST)!=null)
			if (this.getOneNeighbor(Orientation.WEST).isConnectedTo(Orientation.EAST))
				r++;
			if(this.getOneNeighbor(Orientation.SOUTH)!=null)
			if (this.getOneNeighbor(Orientation.SOUTH).isConnectedTo(Orientation.NORTH))
				r++;
		}
		if (this.orientation == 3) {
			if(this.getOneNeighbor(Orientation.NORTH)!=null)
			if (this.getOneNeighbor(Orientation.NORTH).isConnectedTo(Orientation.SOUTH))
				r++;
			if(this.getOneNeighbor(Orientation.SOUTH)!=null)
			if (this.getOneNeighbor(Orientation.SOUTH).isConnectedTo(Orientation.NORTH))
				r++;
			if(this.getOneNeighbor(Orientation.WEST)!=null)
			if (this.getOneNeighbor(Orientation.WEST).isConnectedTo(Orientation.EAST))
				r++;
		}
		return r;

	}

	@Override
	public boolean isConnectedTo(Orientation orientation) {
		if (this.neighbor.containsKey(orientation)) {
			if (orientation == Orientation.NORTH && this.orientation != 2)
				return true;
			if (orientation == Orientation.EAST && this.orientation != 3)
				return true;
			if (orientation == Orientation.SOUTH && this.orientation != 0)
				return true;
			if (orientation == Orientation.WEST && this.orientation != 1)
				return true;
		}
		return false;
	}

	@Override
	public boolean connectedAll() {
		// TODO Auto-generated method stub
		return this.numberOfConnection() == 3;
	}

	@Override
	public String toString() {
		String str = "";
		switch (this.orientation) {
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
