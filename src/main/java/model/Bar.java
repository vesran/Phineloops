package model;

public class Bar extends Piece {

	@Override
	public void translation(Side side) {
		// TODO Auto-generated method stub
			if (this.orientation == 1)
				this.orientation = 0;
			else
				this.orientation = 1;
	}

}
