package model;

import java.util.Deque;
import java.util.LinkedList;

public class QuasiExhaustiveSolver {

    private Level m_level;
    private Deque<Piece> m_stack;

    public QuasiExhaustiveSolver() {
        this.m_stack = new LinkedList<>();
    }

    public void read(Level lvl) {
        this.m_level = lvl;
        for (Piece[] row : this.m_level.getGrid()) {
            for (Piece currentPiece : row) {
                if (currentPiece.getId() != 0 && currentPiece.getId() != 4) {
                    this.m_stack.push(currentPiece);
                }
            }
        }
    }

    public void solve(Level lvl) {
        this.read(lvl);

        while (!m_stack.isEmpty()) {

        }
    }

    public static void main(String [] args) {

    }
}
