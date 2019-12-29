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
 *         Representation of the Circle piece
 */
public class CirclePieceDrawing extends PieceDrawing {
	/**
	 * @param piece the Circle to draw
	 */
	public CirclePieceDrawing(Piece piece) {
		super(piece);
		try {
			super.setImage(new Image(new FileInputStream("resources/pieces/Circle_piece.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
