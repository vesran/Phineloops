package Solver.quasiexhaustive;

import model.Level;
import model.pieces.L;
import model.pieces.Piece;

import java.util.*;
import java.util.stream.Collectors;

public class QuasiExhaustiveSolver {

    private Level m_level;
    private Deque<Piece> m_stack;
    private PriorityQueue<Piece> m_antistack;
    private Map<Piece, Iterator<Integer>> m_nextOrientations;

    public QuasiExhaustiveSolver() {
        this.m_stack = new ArrayDeque<>();
        this.m_antistack = new PriorityQueue<>(this.piecesComparator());
        this.m_nextOrientations = new HashMap<>();
    }

    public QuasiExhaustiveSolver(Level lvl) {
        this();
        this.m_level = lvl;
    }

    public QuasiExhaustiveSolver(Piece[][] grid) {
        this(new Level(grid));
    }

    /**
     * Defines a way to compare pieces in the anti-stack. It defines the order in which pieces should be considered.
     * The greater a piece is, the more its orientation tends to be changed first.
     * @return a Comparator of Piece that implement a way to compare pieces as described above.
     */
    protected Comparator<Piece> piecesComparator() {
        return new Comparator<Piece>() {
            @Override
            public int compare(Piece p1, Piece p2) {
                if (p1.getLine_number() != p2.getLine_number()) {
                    return p1.getLine_number() - p2.getLine_number();
                } else {
                    return p1.getColumn_number() - p2.getColumn_number();
                }
            }
        };
    }

    public boolean solving() {
        Piece out;
        int i = 0;
//        Set<String> instances = new HashSet<>();

        // Init stack and set each piece to their best orientation
        for (Piece[] row : this.m_level.getGrid()) {
            for (Piece currentPiece : row) {
                // Exclude empty and X piece
                if (currentPiece.getId() != 0 && currentPiece.getId() != 4) {
                    this.m_antistack.add(currentPiece);
                }
            }
        }

        // Transfer pieces from anti-stack to stack
        while (this.m_antistack.peek() != null) {
            if (!this.m_stack.isEmpty()) this.setToBestOrientation(this.m_stack.peek());    // Final top of stack not rotated
            this.m_stack.push(this.m_antistack.poll());
        }

        while (!m_stack.isEmpty()) {
            System.out.println("Start iteration");
            // Rotate piece at the top of the stack and add this piece to the rotated set
            this.setToBestOrientation(this.m_stack.peek());

            // Add other pieces to the stack
            while (this.m_antistack.peek() != null) {
                this.m_stack.push(this.m_antistack.poll());
                this.setToBestOrientation(this.m_stack.peek());
            }

            // If the piece has made an entire rotation, pop and remove it from the rotated set
            while (!this.m_stack.isEmpty() && this.entireRotation(this.m_stack.peek())) {
                out = this.m_stack.pop();
                this.m_antistack.add(out);
                this.m_nextOrientations.remove(out);
            }

            System.out.println("Test " + ++i + "\n" + this.m_level);
//            instances.add(this.m_level.toString());

            // Check if the level is solved, no modification of the stacks/pieces should follow to keep things easier
            if (this.m_level.checkGrid()) {
                System.out.println("SOLVED:true");
                return true;

            } else {
                // If the piece has made an entire rotation, pop and remove it from the rotated set
                while (!this.m_stack.isEmpty() && this.entireRotation(this.m_stack.peek())) {
                    out = this.m_stack.pop();
                    this.m_antistack.add(out);
                }
            }

        }

//        System.out.println(instances.size());

        System.out.println("SOLVED:false");
        return false;
    }

    // Set top piece to its best orientation according to score function
    private void setToBestOrientation(Piece piece) {
        if (!this.m_nextOrientations.containsKey(piece)) {
            this.m_nextOrientations.put(piece, this.genOrientations(piece));
        }
        if (this.m_nextOrientations.get(piece).hasNext()) {
            piece.setOrientation(this.m_nextOrientations.get(piece).next());
//            System.out.println("Current top piece set at orientation " + piece.getOrientation());
        }
        else {
            throw new RuntimeException("Empty rotation");
        }
    }

    private Iterator<Integer> genOrientations(Piece piece) {
        // Generate an ordered sequence of orientations in analysing the current piece orientations and its neighborhood
        int nbOrientations = this.numberOfOrientations(piece);
        Map<Integer, Double> scores = new HashMap<>();  // Use Map to be sorted with Stream (easier implementation)

        // Give a score to each orientation
        for (int orientationId = 0; orientationId < nbOrientations; orientationId++) {
            scores.put(orientationId, this.score(piece, orientationId));
        }

        // Return an iterator over the index of the sorted array, orientation with null/negative score are skipped
        // to save time
        List<Integer> list = scores.entrySet().stream()
                        .filter(x -> x.getValue() > 0)
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());

        return list.iterator();
    }

    /**
     * Gives a score for a given orientation of the specified piece. The higher the better.
     * Orientations will be sorted according to the score value in descending order.
     * The more a score for an orientation is higher, the more this orientation will be tested first.
     * @param piece
     * @param orientationId
     * @return score as double
     */
    protected double score(Piece piece, Integer orientationId) {
        return 0.25;
    }

    private int numberOfOrientations(Piece p) {
        // TODO : move to Piece classes (use polymorphism)
        int nbOrientations = 0;

        switch(p.getId()) {
            case 0:
                nbOrientations = 0;
                break;
            case 1:
                nbOrientations = 4;
                break;

            case 2:
                nbOrientations = 2;
                break;

            case 3:
                nbOrientations = 4;
                break;

            case 4:
                nbOrientations = 1;
                break;

            case 5:
                nbOrientations = 4;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + p.getId());
        }

        return nbOrientations;
    }

    private boolean entireRotation(Piece currentPiece) {
        if (this.m_nextOrientations.get(currentPiece) == null)  return false;
        return !this.m_nextOrientations.get(currentPiece).hasNext();
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
        Level lvl = initLevel();
        lvl.init_neighbors();

        QuasiExhaustiveSolver solver = new QuasiExhaustiveSolver(lvl);

        System.out.println("Is level solved ? " + solver.m_level.checkGrid());
        long start = System.currentTimeMillis();
        solver.solving();
        long end = System.currentTimeMillis();
        System.out.println("Is level solved ? " + solver.m_level.checkGrid());
        System.out.println("Time : " + (end - start) / 1000);
    }

}
