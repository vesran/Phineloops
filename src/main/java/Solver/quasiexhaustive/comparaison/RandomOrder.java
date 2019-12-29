package Solver.quasiexhaustive.comparaison;

import model.pieces.Piece;

import java.util.Comparator;
import java.util.Random;

/**
 * @author Karim Amrouche
 * @author Bilal Khaldi
 * @author Yves Tran
 *
 * The ordering and the sequence of orientations of pieces are completely random. It is used to compare other
 * exploration methods.
 */
public class RandomOrder implements Comparator<Piece> {

    @Override
    public int compare(Piece o1, Piece o2) {
        return new int[]{-1, 1}[(new Random()).nextInt(2)];
    }

}
