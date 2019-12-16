package view.pieces;

import javafx.scene.image.Image;
import model.pieces.Piece;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author KBY
 * 
 *         Representation of the bar piece
 */
public class BarPieceDrawing extends PieceDrawing {
	/**
	 * @param piece the Bar to draw
	 * @throws FileNotFoundException in case where the image file is not found
	 */
	public BarPieceDrawing(Piece piece) throws FileNotFoundException {
		super(piece);
		super.setImage(new Image(new FileInputStream("resources/pieces/Bar_piece.png")));
	}
}
