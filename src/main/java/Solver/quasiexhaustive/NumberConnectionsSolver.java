package Solver.quasiexhaustive;

import model.Level;
import model.enumtype.Orientation;
import model.pieces.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class NumberConnectionsSolver extends QuasiExhaustiveSolver {

    public NumberConnectionsSolver(Level lvl) {
        super.m_level = lvl;
    }

    @Override
    protected Comparator<Piece> piecesComparator() {
        return new Comparator<Piece>() {
            @Override
            public int compare(Piece p1, Piece p2) {
                // If p1 is better connected than p2, then p1 will be closer to the top than p2 in the anti-stack
                // then p2 will be closer to the top than p1 in the stack, where p2 is badly connected
                return p1.numberOfConnection() - p2.numberOfConnection();
            }
        };
    }

    @Override
    // To test in priority orientations that gives the most good connections according to its current neighborhood
    protected double score(Piece p, Integer orientationId) {
        int saveOrientationId = p.getOrientation();
        p.silentSetOrientation(orientationId);

        double score;

        if (p.getNeighbor().keySet().containsAll(p.orientatedTo())) {
            score = 1 + p.numberOfConnection();
        } else {
            score = 0;
        }
        p.silentSetOrientation(saveOrientationId);
        return score;
    }
}
