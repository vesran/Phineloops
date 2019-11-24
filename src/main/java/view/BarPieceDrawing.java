package view;

import javafx.scene.image.Image;
import model.Piece;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BarPieceDrawing extends PieceDrawing {
    public BarPieceDrawing(Piece piece) throws FileNotFoundException {
        super(piece);
        super.setImage(new Image(new FileInputStream("resources/pieces/Bar_piece.png")));
    }
}
