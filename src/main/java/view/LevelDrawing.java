package view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.L;
import model.Level;
import model.Piece;

import java.io.FileNotFoundException;
import java.util.List;

public class LevelDrawing {

    private Level model;
    private List<ImageView> drawings;

    public LevelDrawing(Level model) {
        this.model = model;
        System.out.println("In LevelDrawing " + this.model);
    }

    public void draw(GridPane grid) throws FileNotFoundException {

        ImageView iv = null;
        for (Piece[] col : this.model.getGrid()) {
            for (Piece currentPiece : col) {
                if (currentPiece != null) {
                    iv = new LPieceDrawing(currentPiece);
                    iv.setFitWidth(100);
                    iv.setFitHeight(100);
                    iv.setPreserveRatio(false);
                    iv.setPickOnBounds(true);
                    grid.add(iv, currentPiece.getLine_number(), currentPiece.getColumn_number());
                }
            }
        }

    }

}

