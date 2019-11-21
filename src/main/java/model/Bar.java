package model;

public class Bar extends Piece {

	@Override
	public void translation_right() {
		// TODO Auto-generated method stub
		if (this.orientation == 1)
			this.orientation = 0;
		else
			this.orientation = 1;
	}

	@Override
	public void translation_left() {
		this.translation_right();
	}

}
