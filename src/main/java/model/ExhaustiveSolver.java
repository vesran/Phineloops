package model;

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
        System.out.println(this.m_motor + " " + this.m_pieces);
    }

    public void solve() {
        // Turn the same piece over and over
        // Once this piece made an entire rotation => update the following piece
        while (!this.madeEntireRotation(m_pieces.get(this.m_pieces.size() - 1))) {
            if (false && this.m_level.checkGrid()) {
                return;

            } else {
                this.rotateMotor();
                System.out.println(this.m_level);
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

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (true | j != i) {
                    grid[j][i] = new L(3, j, i);
                } else {
                    grid[j][i] = new Empty(0, 0, 0);
                }
            }
        }

        return lvl;
    }

    public static void main(String [] args) {
        ExhaustiveSolver solver = new ExhaustiveSolver();
        Level lvl = initLevel();
        lvl.init_neighbors();
        System.out.println(lvl);
        solver.read(lvl);
        solver.solve();
        System.out.println(lvl);
    }
}
