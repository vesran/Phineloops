package controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import model.Level;
import model.pieces.Piece;
import model.enumtype.Side;
import view.LevelDrawing;
import view.pieces.PieceDrawing;

/**
 * Controller that handles a piece rotations.
 */
public class RotationController implements EventHandler<MouseEvent> {

    private LevelDrawing view;
    private Level model;

    public RotationController(Level model, LevelDrawing view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        // Search piece to change
        PieceDrawing item = this.getItemAt(mouseEvent.getX(), mouseEvent.getY());

        // Rotation is allowed when the older rotation of the same piece is complete
        if (item.getRotate() % 90 == 0) {
            Piece currentPiece = item.getPiece();
            currentPiece.translation(Side.LEFT);   // Update piece's orientation in model
//            System.out.println("Set the orientation for piece : " + currentPiece + " orientation : " + currentPiece.getOrientation());
        }
    }

    private PieceDrawing getItemAt(double x, double y) {
        return null;
    }
}

