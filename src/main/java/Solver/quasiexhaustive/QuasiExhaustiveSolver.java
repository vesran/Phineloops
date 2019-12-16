package Solver.quasiexhaustive;

import Solver.Solving;
import model.Level;
import model.enumtype.Side;
import model.pieces.L;
import model.pieces.Piece;
import model.pieces.T;

import java.util.*;

public class QuasiExhaustiveSolver implements Solving {

    private Level m_level;
    private Deque<Piece> m_stack;
    private Deque<Piece> m_antiStack;
    private Map<Piece, Integer> m_rotatedPieces;

    public QuasiExhaustiveSolver() {
        this.m_stack = new ArrayDeque<>();
        this.m_antiStack = new ArrayDeque<>();
        this.m_rotatedPieces = new HashMap<>();
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

    public boolean solving() {
        Piece out;
        while (!m_stack.isEmpty()) {
            // Rotate piece at the top of the stack and add this piece to the rotated set
            this.rotateTopPiece();

//            System.out.println(this.m_level);
//            System.out.println(this.m_stack);
//            System.out.println(this.m_antiStack);

            // Add other pieces to the stack
            while (!this.m_antiStack.isEmpty()) {
                this.m_stack.push(this.m_antiStack.pop());
            }

            // Check if the level is solved
            if (this.m_level.checkGrid()) {
                System.out.println("SOLVED:true");
                return true;

            } else {
                // If the piece has made an entire rotation, pop and remove it from the rotated set
                while (!this.m_stack.isEmpty() && this.entireRotation(this.m_stack.peek())) {
                    out = this.m_stack.pop();
                    this.m_rotatedPieces.remove(out);
                    this.m_antiStack.push(out);
                }
            }
        }
        System.out.println("SOLVED:false");
        return false;
    }

    private void rotateTopPiece() {
        Piece top = this.m_stack.peek();
        if (!this.m_rotatedPieces.containsKey(top)) {
            this.m_rotatedPieces.put(top, top.getOrientation());
        }
        top.translation(Side.LEFT);
    }

    private boolean entireRotation(Piece currentPiece) {
        if (!this.m_rotatedPieces.containsKey(currentPiece)) {
            return false;
        } else {
            return this.m_rotatedPieces.get(currentPiece).equals(currentPiece.getOrientation());
        }
    }

    public static Level initLevel() { // To remove
        int width = 2;
        int height = 2;
        Level lvl = new Level(height, width);
        Piece[][] grid = lvl.getGrid();
        // code d'essai
        grid[0][0] = new L(0, 0, 0);
        grid[0][1] = new L(0, 0, 1);
        grid[1][0] = new L(0, 1, 0);
        grid[1][1] = new L(0, 1, 1);

        return lvl;
    }

    public static void main(String [] args) {
        QuasiExhaustiveSolver solver = new QuasiExhaustiveSolver();

        Level lvl = initLevel();
        lvl.init_neighbors();
        solver.read(lvl);

        System.out.println("Is level solved ? " + solver.m_level.checkGrid());
        long start = System.currentTimeMillis();
        solver.solving();
        long end = System.currentTimeMillis();
        System.out.println("Is level solved ? " + solver.m_level.checkGrid());
        System.out.println("Time : " + (end - start) / 1000);
    }

}
