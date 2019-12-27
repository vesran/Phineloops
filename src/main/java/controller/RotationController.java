package controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import model.pieces.Piece;
import model.enumtype.Side;
import view.pieces.PieceDrawing;

/**
 * Controller that handles a piece rotations.
 */
public class RotationController implements EventHandler<MouseEvent> {

    private Node m_item;

    public RotationController(Node node) {
        this.m_item = node;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        // Rotation is allowed when the older rotation of the same piece is complete
        if (this.m_item.getRotate() % 90 == 0) {
            PieceDrawing p = (PieceDrawing) this.m_item;
            Piece currentPiece = p.getPiece();
            currentPiece.translation(Side.LEFT);   // Update piece's orientation in model
//            System.out.println("Set the orientation for piece : " + currentPiece + " orientation : " + currentPiece.getOrientation());
        }

    }
}

