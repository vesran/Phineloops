package view.pieces;

import javafx.scene.image.Image;
import model.pieces.Piece;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author KBY
 * 
 *         Representation of the L piece
 */
public class LPieceDrawing extends PieceDrawing {
	/**
	 * @param piece the L to draw
	 */
	public LPieceDrawing(Piece piece) {
		super(piece);
		try {
			super.setImage(new Image(new FileInputStream("resources/pieces/L_piece.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
