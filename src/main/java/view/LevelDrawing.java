package view;

import controller.RotationController;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Level;
import model.Piece;
import java.io.FileNotFoundException;

public class LevelDrawing {

    private Level m_model;

    public LevelDrawing(Level model) {
        this.m_model = model;
        System.out.println("In LevelDrawing " + this.m_model);
    }

    private void setOrientation(ImageView iv, int orientation) {
        // Based on the property in which orientations for any piece are ordered.
        // The increment of the orientation code (int) is equivalent to a rotation of 90 degrees to the right.
        iv.setRotate(iv.getRotate() + 90 * orientation);
    }

    public void draw(GridPane grid) throws FileNotFoundException {
        ImageView iv = null;
        double unit = Math.max(this.m_model.getGrid().length, this.m_model.getGrid()[0].length);

        for (Piece[] col : this.m_model.getGrid()) {
            for (Piece currentPiece : col) {
                // Adding image view for each non empty piece
                if (currentPiece.getId() != 0) {
                    iv = currentPiece.createDrawing();
                    this.setOrientation(iv, currentPiece.getOrientation());
                    iv.setFitWidth(PhineLoopsMainGUI.GRID_WIDTH / unit);
                    iv.setFitHeight(PhineLoopsMainGUI.GRID_HEIGHT / unit);
                    iv.setPreserveRatio(false);
                    iv.setPickOnBounds(true);
                    grid.add(iv, currentPiece.getColumn_number(), currentPiece.getLine_number());
                }
            }
        }

        // Adding a controller to each node so that we don't need to retrieve which one was clicked
        for (Node item : grid.getChildren()) {
            if (item != grid) {
                item.setOnMouseClicked(new RotationController(item));
            }
        }

    }

}

