package Solver.quasiexhaustive.comparaison;

import model.pieces.Piece;

import java.util.Comparator;

/**
 * For sorting pieces into an anti-diagonal ordering. If same diagonal, see columns.
 * From top-left to bottom-right.
 */
public class DiagonalShift implements Comparator<Piece> {

    @Override
    public int compare(Piece p1, Piece p2) {
        return (p1.getLine_number() + p1.getColumn_number()) - (p2.getLine_number() + p2.getColumn_number());
    }

}
