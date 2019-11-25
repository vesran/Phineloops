package view;

import javafx.scene.image.Image;
import model.Piece;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LPieceDrawing extends PieceDrawing {

    public LPieceDrawing(Piece piece) throws FileNotFoundException {
        super(piece);
        super.setImage(new Image(new FileInputStream("resources/pieces/L_piece.png")));
    }
}
