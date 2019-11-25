package view;

import javafx.scene.image.ImageView;
import model.Piece;

abstract public class PieceDrawing extends ImageView {

    protected Piece piece;

    public PieceDrawing(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return this.piece;
    }
}
