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
import java.util.Scanner;

abstract public class PieceDrawing extends ImageView implements PropertyChangeListener {

	public static final Object rotationMonitor = new Object();
	protected Piece m_piece;

	public PieceDrawing(Piece piece) {
		this.m_piece = piece;
	}

	public Piece getPiece() {
		return this.m_piece;
	}

	public RotateTransition rotate(int angle, int duration) {
		RotateTransition rotate = new RotateTransition();
		this.setRotate(this.getRotate() % 360);	// To avoid troubles with integer's limit
		rotate.setNode(this);
		rotate.setByAngle(angle);
		rotate.setAxis(Rotate.Z_AXIS);
		rotate.setCycleCount(1);
		rotate.setDuration(Duration.millis(duration));
		rotate.setOnFinished(e -> {
			synchronized (rotationMonitor) {
				rotationMonitor.notify();
			}
		});
		rotate.play();
		return rotate;
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
			RotateTransition rotate;

			if ((oldValue + 1) % this.m_piece.getNumberOfOrientations() == newValue) {
				rotate = this.rotate(90, duration * 1);

			} else if ((newValue + 1) % this.m_piece.getNumberOfOrientations() == oldValue) {
				rotate = this.rotate(-90, duration * 1);

			} else if ((oldValue + 2) % this.m_piece.getNumberOfOrientations() == newValue) {
				rotate = this.rotate(180, duration * 1);

			} else {
				rotate = new RotateTransition();
				System.out.println("==========================================new value : " + newValue); // --> 3
				System.out.println("==========================================old value : " + oldValue); // --> 0
			}

			synchronized(rotationMonitor) {
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
