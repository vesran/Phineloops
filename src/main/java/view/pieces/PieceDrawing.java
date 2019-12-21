package view.pieces;

import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.pieces.Piece;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

abstract public class PieceDrawing extends ImageView implements PropertyChangeListener {

	protected Piece m_piece;

	public PieceDrawing(Piece piece) {
		this.m_piece = piece;
	}

	public Piece getPiece() {
		return this.m_piece;
	}

	public void rotate(int angle) {
		RotateTransition rotate = new RotateTransition();
		rotate.setNode(this);
		rotate.setByAngle(angle);
		rotate.setAxis(Rotate.Z_AXIS);
		rotate.setCycleCount(1);
		rotate.setDuration(Duration.millis(100)); // Duration of the animation
		rotate.play();
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		System.out.println("Variation of " + event.getPropertyName());	// if case
		
		if (event.getPropertyName().equals("leftTranslation")) {
			this.rotate(90);

		} else if (event.getPropertyName().equals("rightTranslation")) {
			this.rotate(-90);
		}
	}
}
