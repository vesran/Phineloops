package view.pieces;

import javafx.scene.image.Image;
import model.pieces.Piece;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
/**
 * @author KBY
 * 
 *         Representation of the T piece
 */
public class TPieceDrawing extends PieceDrawing {
	/**
	 * @param piece the T to draw
	 * @throws FileNotFoundException in case where the image file is not found
	 */
	public TPieceDrawing(Piece piece) throws FileNotFoundException {
        super(piece);
        super.setImage(new Image(new FileInputStream("resources/pieces/T_piece.png")));
    }
}
