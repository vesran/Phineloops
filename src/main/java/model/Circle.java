package model;

public class Circle extends Piece {

	@Override
	public void translation_right() {
		// TODO Auto-generated method stub
		if (this.orientation == 0)
			this.orientation = 3;
		else
			this.orientation--;
	}

	@Override
	public void translation_left() {
		// TODO Auto-generated method stub
		if (this.orientation == 3)
			this.orientation = 0;
		else
			this.orientation++;
	}

}
