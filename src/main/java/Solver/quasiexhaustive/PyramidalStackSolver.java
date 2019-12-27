package Solver.quasiexhaustive;

import model.Level;
import model.pieces.Piece;

import java.util.Comparator;

/**
 * It implements a new ordering over the pieces to explore. It makes the algorithm starts from one corner and ends
 * to its opposite corner.
 */
public class PyramidalStackSolver extends QuasiExhaustiveSolver {

    public PyramidalStackSolver(Level lvl) {
        super(lvl);
    }

    public PyramidalStackSolver(Piece[][] grid) {
        super(grid);
    }

    /**
     * For sorting pieces into an anti-diagonal ordering. If same diagonal, see columns
     * @return Comparator over pieces
     */
    @Override
    protected Comparator<Piece> piecesComparator() {
        return new Comparator<Piece>() {
            @Override
            public int compare(Piece p1, Piece p2) {
                if ((p1.getLine_number() + p1.getColumn_number()) - (p2.getLine_number() + p2.getColumn_number()) != 0) {
                    // Multiply by a huge number to create a difference with the size of the grid
                    return 100000 * ((p1.getLine_number() + p1.getColumn_number()) - (p2.getLine_number() + p2.getColumn_number()));
                } else {
                    // If same diagonal, see column
                    return p1.getColumn_number() - p2.getColumn_number();
                }
            }
        };
    }
}
