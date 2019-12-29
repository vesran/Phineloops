package view.pieces;

import javafx.scene.image.Image;
import model.pieces.Piece;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Karim Amrouche
 * @author Bilal Khaldi
 * @author Yves Tran
 * 
 *         Representation of the bar piece
 */
public class BarPieceDrawing extends PieceDrawing {
	/**
	 * @param piece the Bar to draw
	 */
	public BarPieceDrawing(Piece piece) {
		super(piece);
		try {
			super.setImage(new Image(new FileInputStream("resources/pieces/Bar_piece.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
