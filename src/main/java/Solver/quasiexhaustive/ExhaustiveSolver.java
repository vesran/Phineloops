package Solver.quasiexhaustive;

import model.Level;
import model.enumtype.Side;
import model.pieces.L;
import model.pieces.Piece;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ExhaustiveSolver {

    private Level m_level;
    private HashMap<Piece, Integer> m_originalOrientations; // Piece should not redefine hashCode
    private List<Piece> m_pieces;
    private Piece m_motor;

    public ExhaustiveSolver() {
        this.m_originalOrientations = new LinkedHashMap<>();
        this.m_pieces = new ArrayList<>(); // Init size
    }

    public void read(Level lvl) {
        this.m_level = lvl;
        for (Piece[] col : lvl.getGrid()) {
            for (Piece currentPiece : col) {
                // Do not consider empty piece nor X piece
                if (currentPiece.getId() != 0 && currentPiece.getId() != 4) {
                    this.m_pieces.add(currentPiece);
                }
            }
        }
        this.m_motor = this.m_pieces.remove(0);
        this.m_originalOrientations.put(this.m_motor, this.m_motor.getOrientation());
//        System.out.println(this.m_motor + " " + this.m_pieces);
    }

    public void solve() {
        // Turn the same piece over and over
        // Once this piece made an entire rotation => update the following piece
        while (!this.madeEntireRotation(m_pieces.get(this.m_pieces.size() - 1))) {
            if (this.m_level.checkGrid()) {
                return;

            } else {
                this.rotateMotor();
            }
        }
    }

    public void rotateMotor() {
        this.m_motor.translation(Side.LEFT);

        Piece previousPiece = this.m_motor;
        for (Piece currentPiece : this.m_pieces) {

            if (this.madeEntireRotation(previousPiece)) {
                this.m_originalOrientations.computeIfAbsent(currentPiece, Piece::getOrientation);
                currentPiece.translation(Side.LEFT);
                previousPiece = currentPiece;

            } else {
                return; // No need to go further since the last pieces can't be rotated without the first ones
            }
        }
    }

    public boolean madeEntireRotation(Piece piece) {
        if (this.m_originalOrientations.get(piece) == null) {
            return false;

        } else {
             return this.m_originalOrientations.get(piece) == piece.getOrientation();
        }
    }

    public static Level initLevel() { // To remove
        int width = 2;
        int height = 2;
        Level lvl = new Level(height, width);
        Piece[][] grid = lvl.getGrid();
        							// code d'essai 
        grid[0][0] = new L(3, 0, 0);
        grid[0][1] = new L(2, 0, 1);
        grid[1][0] = new L(2, 1, 0);
        grid[1][1] = new L(1, 1, 1);
        
        
        	  return lvl;
    }

    public static void main(String [] args) {
        ExhaustiveSolver solver = new ExhaustiveSolver();
        Level lvl = initLevel();
        lvl.init_neighbors();
        solver.read(lvl);
        System.out.println(solver.m_level.checkGrid());

        long start = System.currentTimeMillis();
        solver.solve();
        long end = System.currentTimeMillis();
        System.out.println(solver.m_level.checkGrid());

        System.out.println("Time : " + (end - start));
    }
    
}
