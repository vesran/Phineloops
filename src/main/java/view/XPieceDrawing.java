package view;

import javafx.scene.image.Image;
import model.Piece;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class XPieceDrawing extends PieceDrawing {
    public XPieceDrawing(Piece piece) throws FileNotFoundException {
        super(piece);
        super.setImage(new Image(new FileInputStream("resource/pieces/X_piece.png")));
    }
}
