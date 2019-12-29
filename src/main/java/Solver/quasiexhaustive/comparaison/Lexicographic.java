package Solver.quasiexhaustive.comparaison;

import model.pieces.Piece;

import java.util.Comparator;

/**
 * @author Karim Amrouche
 * @author Bilal Khaldi
 * @author Yves Tran
 *
 * For lexicographic ordering. Starts from top left to bottom right.
 */
public class Lexicographic implements Comparator<Piece> {

    @Override
    public int compare(Piece p1, Piece p2) {
        if (p1.getLine_number() != p2.getLine_number()) {
            return p1.getLine_number() - p2.getLine_number();
        } else {
            return p1.getColumn_number() - p2.getColumn_number();
        }
    }

}
