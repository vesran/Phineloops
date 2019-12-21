package view;

import model.pieces.Piece;

import java.util.Observable;
import java.util.Observer;

public class SolverVisualiser implements Observer{

    LevelDrawing levelDrawing;

    public SolverVisualiser(LevelDrawing levelDrawing) {
        this.levelDrawing = levelDrawing;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

}
