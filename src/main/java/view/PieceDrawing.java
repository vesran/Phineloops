package view;

import javafx.scene.image.ImageView;
import model.Piece;

abstract public class PieceDrawing extends ImageView {
	protected Piece m_piece;

	public PieceDrawing(Piece piece) {
		this.m_piece = piece;
	}

	public Piece getPiece() {
		return this.m_piece;
	}
}
