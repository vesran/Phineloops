package view;

import javafx.scene.image.Image;
import model.Piece;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TPieceDrawing extends PieceDrawing {
    public TPieceDrawing(Piece piece) throws FileNotFoundException {
        super(piece);
        super.setImage(new Image(new FileInputStream("resources/pieces/T_piece.png")));
    }
}
