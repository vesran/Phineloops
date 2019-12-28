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
        PieceDrawing item = this.getPieceDrawingAt(mouseEvent.getSceneX(), mouseEvent.getSceneY());

        // Rotation is allowed when the older rotation of the same piece is complete
        if (item != null && item.getRotate() % 90 == 0) {
            Piece currentPiece = item.getPiece();
            currentPiece.translation(Side.LEFT);   // Update piece's orientation in model

            if (this.model.checkGrid()) {
                this.view.solvedSituation();
            } else {
                this.view.unsolvedSituation();
            }
        }
    }

    private PieceDrawing getPieceDrawingAt(double x, double y) {
        for (Node child : this.view.getGridPane().getChildren()) {
            if (child instanceof PieceDrawing && child.getBoundsInParent().contains(x, y)) {
                return (PieceDrawing) child;
            }
        }
        return null;
    }
}
