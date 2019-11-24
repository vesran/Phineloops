package model;

public class T extends Piece {

	public T(int orientation, int line_number, int column_number) {
		super(orientation, line_number, column_number);
		this.id = 3;
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

}
