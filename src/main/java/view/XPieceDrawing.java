package view;

import javafx.scene.image.Image;
import model.Piece;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
/**
 * @author KBY
 * 
 *         Representation of the X piece
 */
public class XPieceDrawing extends PieceDrawing {
	/**
	 * @param piece the X to draw
	 * @throws FileNotFoundException in case where the image file is not found
	 */
    public XPieceDrawing(Piece piece) throws FileNotFoundException {
        super(piece);
        super.setImage(new Image(new FileInputStream("resource/pieces/X_piece.png")));
    }
}
