package view;

import javafx.scene.image.Image;
import model.Piece;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CirclePieceDrawing extends PieceDrawing {
    public CirclePieceDrawing(Piece piece) throws FileNotFoundException {
        super(piece);
        super.setImage(new Image(new FileInputStream("resources/pieces/Circle_piece.png")));
    }
}
