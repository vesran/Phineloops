package view.pieces;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.pieces.Piece;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Scanner;

abstract public class PieceDrawing extends ImageView implements PropertyChangeListener {

	public static final Object monitor = new Object();
//	public static boolean done = true;
	protected Piece m_piece;

	public PieceDrawing(Piece piece) {
		this.m_piece = piece;
	}

	public Piece getPiece() {
		return this.m_piece;
	}

	public void rotate(int angle, int duration) {
		RotateTransition rotate = new RotateTransition();
		this.setRotate(this.getRotate() % 360);	// To avoid troubles with integer's limit
		rotate.setNode(this);
		rotate.setByAngle(angle);
		rotate.setAxis(Rotate.Z_AXIS);
		rotate.setCycleCount(1);
		rotate.setDuration(Duration.millis(duration));
		rotate.setOnFinished(e -> {
			synchronized (monitor) {
				System.out.println("Animation finished");
				monitor.notify();
			}
		});
		rotate.play();

		synchronized(monitor) {
			while (rotate.statusProperty().getValue() == Animation.Status.RUNNING) {
				try {
					System.out.println("Waiting");
					monitor.wait();

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
				this.rotate(90, duration * 1);

			} else if ((newValue + 1) % this.m_piece.getNumberOfOrientations() == oldValue) {
				this.rotate(-90, duration * 1);

			} else if ((oldValue + 2) % this.m_piece.getNumberOfOrientations() == newValue) {
				System.out.println("Double rotation");
				this.rotate(180, duration * 1);

			} else {
				System.out.println("==========================================new value : " + newValue); // --> 3
				System.out.println("==========================================old value : " + oldValue); // --> 0
			}

//			if (!this.m_piece.getNeighbor().keySet().containsAll(this.m_piece.orientatedTo())) {
//				(new Scanner(System.in)).next();
//			}
		}
	}
}
