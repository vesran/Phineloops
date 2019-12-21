package Solver.quasiexhaustive;

import model.pieces.Piece;

import java.util.Comparator;

public class NumberConnectionsSolver extends QuasiExhaustiveSolver {

    @Override
    // Favours pieces with less connections
    protected Comparator<Piece> piecesComparator() {
        return new Comparator<Piece>() {
            @Override
            public int compare(Piece p1, Piece p2) {
                return p2.numberOfConnection() - p1.numberOfConnection();
            }
        };
    }

    @Override
    // To test in priority orientations that gives the most good connections according to its current neighborhood
    protected double score(Piece p, Integer orientationId) {
        int savePieceOrientation = p.getOrientation();

        p.setOrientation(orientationId);

        // Check if the piece is orientated toward an empty piece or out of the grid
        return -1;
    }
}
