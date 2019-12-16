package controller;

import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.pieces.Piece;
import model.enumtype.Side;
import view.pieces.PieceDrawing;

public class RotationController implements EventHandler<MouseEvent> {

    private Node m_item;

    public RotationController(Node node) {
        this.m_item = node;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        if (this.m_item.getRotate() % 90 == 0) {
            RotateTransition rotate = new RotateTransition();
            rotate.setAxis(Rotate.Z_AXIS);
            rotate.setByAngle(90 - (this.m_item.getRotate() % 90));
            rotate.setCycleCount(1);
            rotate.setDuration(Duration.millis(100)); // Duration of the animation
            rotate.setNode(this.m_item);
            rotate.play();

            PieceDrawing p = (PieceDrawing) this.m_item;
            Piece currentPiece = p.getPiece();
            currentPiece.translation(Side.LEFT);   // Update piece's orientation in model
            System.out.println("Set the orientation for piece : " + currentPiece + " orientation : " + currentPiece.getOrientation());
        }

    }
}

