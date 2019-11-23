package model;

public class L extends Piece {

	public L(int orientation) {
		this.id = 5;
		this.orientation = orientation;
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
}
