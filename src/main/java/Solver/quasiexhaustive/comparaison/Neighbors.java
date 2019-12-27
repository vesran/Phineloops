package Solver.quasiexhaustive.comparaison;

import model.pieces.Piece;

import java.util.Comparator;

/**
 * Compares the number of neighbors between pieces.
 */
public class Neighbors implements Comparator<Piece> {

    @Override
    public int compare(Piece p1, Piece p2) {
        return p1.getNeighbor().size() - p2.getNeighbor().size();
    }

}
