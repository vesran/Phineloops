package view.pieces;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.pieces.Piece;
import view.PhineLoopsMainGUI;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

abstract public class PieceDrawing extends ImageView implements PropertyChangeListener {

	public static final Object rotationMonitor = new Object();
	protected Piece m_piece;

	public PieceDrawing(Piece piece) {
		this.m_piece = piece;
	}

	public Piece getPiece() {
		return this.m_piece;
	}

	/**
	 * Makes the representation of the piece rotate. It is synchronized to the model.
	 * @param angle angle to rotate
	 * @param duration duration of the animation
	 * @return RotateTransition Object to keep information about the animation thread.
	 */
	public RotateTransition rotate(int angle, int duration) {
		RotateTransition rotate = new RotateTransition();
		this.setRotate(this.getRotate() % 360);	// To avoid troubles with integer's limit
		rotate.setNode(this);
		rotate.setByAngle(angle);
		rotate.setAxis(Rotate.Z_AXIS);
		rotate.setCycleCount(1);
		rotate.setDuration(Duration.millis(duration));
		// At the end, notify that the animation is over and the solver can continue to work
		rotate.setOnFinished(e -> {
			synchronized (rotationMonitor) {
				rotationMonitor.notify();
			}
		});
		rotate.play();
		return rotate;
	}

	/**
	 * Actions to trigger when a property is invoked.
	 * @param event
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals("leftTranslation")) {
			this.rotate(90, 100);

		} else if (event.getPropertyName().equals("rightTranslation")) {
			this.rotate(-90, 100);

		} else if (event.getPropertyName().equals("orientationSet")) {
			// Mainly for solver animation
			int newValue = (int) event.getNewValue();
			int oldValue = (int) event.getOldValue();
			int duration = 100;
			RotateTransition rotate;

			if ((oldValue + 1) % this.m_piece.getNumberOfOrientations() == newValue) {
				rotate = this.rotate(90, duration);

			} else if ((newValue + 1) % this.m_piece.getNumberOfOrientations() == oldValue) {
				rotate = this.rotate(-90, duration);

			} else {
				rotate = this.rotate(180, duration);
			}

			// Makes the algorithm waits until the animation is done.
			synchronized(rotationMonitor) {
				// Need to check solverApplied value to keep the solver working while the window has been closed
				while (rotate.statusProperty().getValue() == Animation.Status.RUNNING && PhineLoopsMainGUI.solverApplied) {
					try {
						rotationMonitor.wait();

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
