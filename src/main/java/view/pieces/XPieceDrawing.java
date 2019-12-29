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
 * 
 *         Representation of the X piece
 */
public class XPieceDrawing extends PieceDrawing {
	/**
	 * @param piece the X to draw
	 */
    public XPieceDrawing(Piece piece) {
        super(piece);
        try {
            super.setImage(new Image(new FileInputStream("resources/pieces/X_piece.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
