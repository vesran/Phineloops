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
 *         Representation of the T piece
 */
public class TPieceDrawing extends PieceDrawing {
	/**
	 * @param piece the T to draw
	 */
	public TPieceDrawing(Piece piece) {
        super(piece);
		try {
			super.setImage(new Image(new FileInputStream("resources/pieces/T_piece.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
