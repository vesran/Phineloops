package controller;

import javafx.animation.RotateTransition;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Piece;
import view.PieceDrawing;

public class RotationController implements EventHandler<MouseEvent> {

    private ReadOnlyBooleanWrapper m_isAnimating;
    private Node m_item;

    public RotationController(Node node) {
        this.m_isAnimating = new ReadOnlyBooleanWrapper();
        this.m_item = node;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        System.out.println("event");
        RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setByAngle(90 - (this.m_item.getRotate() % 90));
        rotate.setCycleCount(1);
        rotate.setDuration(Duration.millis(100)); // Duration of the animation
        rotate.setNode(this.m_item);
        rotate.play();

        PieceDrawing p = (PieceDrawing) this.m_item;
        Piece piece = p.getPiece();
        System.out.println("Set the orientation for piece : " + piece);
        //        TODO ? : piece.setOrientation(piece.nextOrientation());
    }
}

