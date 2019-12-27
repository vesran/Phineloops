package view;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Level;
import model.pieces.Piece;
import view.pieces.PieceDrawing;

import java.io.FileNotFoundException;

/**
 * Produces a visual representation of the game level.
 */
public class LevelDrawing {

    private Level m_model;

    public LevelDrawing(Level model) {
        this.m_model = model;
    }

    /**
     * Attributes an angle that corresponds to the given orientation piece.
     * @param iv ImageView in which orientation has to be set.
     * @param orientation Orientation to attribute to the image of the piece.
     */
    private void setOrientation(ImageView iv, int orientation) {
        // Based on the property in which orientations for any piece are ordered.
        // The increment of the orientation code (int) is equivalent to a rotation of 90 degrees to the right.
        iv.setRotate(iv.getRotate() + 90 * orientation);
    }

    /**
     * Init the visual representation of the level game.
     * @param grid GridPane that will be filled with drawing of pieces.
     * @param scene Scene that contains all elements.
     * @throws FileNotFoundException
     */
    public void draw(GridPane grid, Scene scene) throws FileNotFoundException {
        PieceDrawing iv = null;

        for (Piece[] col : this.m_model.getGrid()) {
            for (Piece currentPiece : col) {
                // Adding image view for each non empty piece
                if (currentPiece.getId() != 0) {
                    iv = currentPiece.createDrawing();
                    this.setOrientation(iv, currentPiece.getOrientation());

                    // Dynamic resizing of pieces
                    iv.fitWidthProperty().bind(scene.widthProperty().divide(col.length + 1));
                    iv.fitHeightProperty().bind(scene.heightProperty().divide(this.m_model.getGrid().length + 1));

                    iv.setCache(true);
                    iv.setSmooth(true);
                    iv.setPreserveRatio(true);
                    iv.setPickOnBounds(true);
                    grid.add(iv, currentPiece.getColumn_number(), currentPiece.getLine_number());

                    currentPiece.addObserver(iv);
                }
            }
        }

    }

}
