package Solver.quasiexhaustive;

import model.Level;
import model.pieces.Piece;

import java.util.Comparator;
import java.util.Random;

public class RandomizedSolver extends QuasiExhaustiveSolver {

    public RandomizedSolver(Level lvl) {
        super(lvl);
    }

    @Override
    protected Comparator<Piece> piecesComparator() {
        return new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return new int[]{-1, 1}[(new Random()).nextInt(2)];
            }
        };
    }

    @Override
    protected double score(Piece p, Integer orientation) {
        return (new Random()).nextDouble();
    }
}
