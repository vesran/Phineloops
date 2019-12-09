package view;

import javafx.scene.image.Image;
import model.Piece;

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
	 * @throws FileNotFoundException in case where the image file is not found
	 */
	public LPieceDrawing(Piece piece) throws FileNotFoundException {
		super(piece);
		super.setImage(new Image(new FileInputStream("resources/pieces/L_piece.png")));
	}
}
