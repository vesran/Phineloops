package controller;

import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Piece;
import view.PieceDrawing;

public class RotationController implements EventHandler<MouseEvent> {

    private Node item;

    public RotationController(Node node) {
        this.item = node;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        System.out.println("event");
        RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setByAngle(90 - (this.item.getRotate() % 90));
        rotate.setCycleCount(1);
        rotate.setDuration(Duration.millis(1)); // Duration of the animation
        rotate.setNode(this.item);
        rotate.play();

        PieceDrawing p = (PieceDrawing) this.item;
        Piece piece = p.getPiece();
        System.out.println("Set the orientation for piece : " + piece);

//        TODO ? : piece.setOrientation(piece.nextOrientation());
    }
}

