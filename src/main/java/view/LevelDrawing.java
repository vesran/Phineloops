package view;

import controller.RotationController;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Level;
import model.Piece;
import java.io.FileNotFoundException;

public class LevelDrawing {

    private Level model;

    public LevelDrawing(Level model) {
        this.model = model;
        System.out.println("In LevelDrawing " + this.model);
    }

    public void draw(GridPane grid) throws FileNotFoundException {

        ImageView iv = null;
        double unit = Math.max(this.model.getGrid().length, this.model.getGrid()[0].length);
        for (Piece[] col : this.model.getGrid()) {
            for (Piece currentPiece : col) {
                if (currentPiece != null) {
                    iv = currentPiece.createDrawing();
                    iv.setFitWidth(PhineLoopsMainGUI.GRID_WIDTH / unit);         // TODO : Fix a dynamic value
                    iv.setFitHeight(PhineLoopsMainGUI.GRID_HEIGHT / unit);
                    iv.setPreserveRatio(false);
                    iv.setPickOnBounds(true);
                    grid.add(iv, currentPiece.getColumn_number(), currentPiece.getLine_number());
                }
            }
        }

        grid.getChildren().forEach(item -> item.setOnMouseClicked(new RotationController(item)));

    }

}

