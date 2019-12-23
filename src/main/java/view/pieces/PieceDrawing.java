package view.pieces;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.pieces.Piece;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

abstract public class PieceDrawing extends ImageView implements PropertyChangeListener {

	public static RotateTransition rotate = new RotateTransition();
	public static final Object monitor = new Object();
	public static boolean done = true;
	protected Piece m_piece;

	public PieceDrawing(Piece piece) {
		this.m_piece = piece;
	}

	public Piece getPiece() {
		return this.m_piece;
	}

	public void rotate(int angle, int duration) {
//		RotateTransition rotate = new RotateTransition();
		rotate.setNode(this);
		rotate.setByAngle(angle );
		rotate.setAxis(Rotate.Z_AXIS);
		rotate.setCycleCount(1);
		rotate.setDuration(Duration.millis(duration));
		done = false;
		rotate.play();
		done = true;

		synchronized(monitor) {
			System.out.println("Notify");
			if (done) {
				monitor.notify();
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals("leftTranslation")) {
			this.rotate(90, 100);

		} else if (event.getPropertyName().equals("rightTranslation")) {
			this.rotate(-90, 100);

		} else if (event.getPropertyName().equals("orientationSet")) {
			int newValue = (int) event.getNewValue();
			int oldValue = (int) event.getOldValue();
			int duration = 100;

			if ((oldValue + 1) % this.m_piece.getNumberOfOrientations() == newValue) {
				this.rotate(90, duration);

			} else if ((newValue + 1) % this.m_piece.getNumberOfOrientations() == oldValue) {
				this.rotate(-90, duration);

			} else if ((oldValue + 2) % this.m_piece.getNumberOfOrientations() == newValue) {
				this.rotate(180, duration);

			} else {
				System.out.println("new value : " + newValue); // --> 3
				System.out.println("old value : " + oldValue); // --> 0
			}
		}
	}
}
