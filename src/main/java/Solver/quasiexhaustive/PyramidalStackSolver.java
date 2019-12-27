package Solver.quasiexhaustive;

import model.Level;
import model.pieces.Piece;

import java.util.Comparator;

public class PyramidalStackSolver extends QuasiExhaustiveSolver {

    public PyramidalStackSolver(Level lvl) {
        super(lvl);
    }

    public PyramidalStackSolver(Piece[][] grid) {
        super(grid);
    }

    @Override
    protected Comparator<Piece> piecesComparator() {
        return new Comparator<Piece>() {
            @Override
            public int compare(Piece p1, Piece p2) {
                if ((p1.getLine_number() + p1.getColumn_number()) - (p2.getLine_number() + p2.getColumn_number()) != 0) {
                    return 100000 * ((p1.getLine_number() + p1.getColumn_number()) - (p2.getLine_number() + p2.getColumn_number()));
                } else {
                    return p1.getColumn_number() - p2.getColumn_number();
                }
            }
        };
    }
}
