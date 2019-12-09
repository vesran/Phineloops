package view;

import javafx.scene.image.Image;
import model.Piece;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author KBY
 * 
 *         Representation of the Circle piece
 */
public class CirclePieceDrawing extends PieceDrawing {
	/**
	 * @param piece the Circle to draw
	 * @throws FileNotFoundException in case where the image file is not found
	 */
	public CirclePieceDrawing(Piece piece) throws FileNotFoundException {
		super(piece);
		super.setImage(new Image(new FileInputStream("resources/pieces/Circle_piece.png")));
	}
}
