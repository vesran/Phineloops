package Solver.quasiexhaustive;

import model.Level;
import model.pieces.Piece;

import java.util.*;
import java.util.stream.Collectors;

public class QuasiExhaustiveSolver {

    protected Level m_level;
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

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Init stack and set each piece to their best orientation
        for (Piece[] row : this.m_level.getGrid()) {
            for (Piece currentPiece : row) {
                // Exclude empty and X piece
                if (currentPiece.getId() != 0 && currentPiece.getId() != 4) {
                    this.m_antistack.add(currentPiece); // First sorting of pieces
                }
            }
        }

        // Starting to test orientations for all pieces
        while (!m_stack.isEmpty() || i == 0) {
            System.out.println("Start iteration");

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
//            System.out.println("First : " + this.m_stack.peek().getLine_number() + this.m_stack.peek().getColumn_number());
//            this.m_stack.stream().forEach(x -> System.out.print("(" + x.getLine_number() + ", " + x.getColumn_number() + ") "));

            // Check if the level is solved, no modification of the stacks should follow to keep things easier
            if (this.m_level.checkGrid()) {
                return true;
            }
            // Rotate piece at the top of the stack and add this piece to the rotated set
            this.setToBestOrientation(this.m_stack.peek());
        }

        return false;
    }

    // Set top piece to its best orientation according to score function
    private void setToBestOrientation(Piece piece) {
        if (piece == null) return;  // For the last iteration where there is no piece left to modify
        if (!this.m_nextOrientations.containsKey(piece)) {
            this.m_nextOrientations.put(piece, this.genOrientations(piece));
        }
        if (this.m_nextOrientations.get(piece).hasNext()) {
            piece.setOrientation(this.m_nextOrientations.get(piece).next());
        } else {
            System.out.println(piece.getLine_number() + " " + piece.getColumn_number());
            System.out.println(this.m_level);
            System.out.println(this.m_nextOrientations.get(piece).next());
            throw new RuntimeException("Empty rotation");
        }
    }

    private Iterator<Integer> genOrientations(Piece piece) {
        // Generate an ordered sequence of orientations in analysing the current piece orientations and its neighborhood
        int nbOrientations = piece.getNumberOfOrientations();
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
//        System.out.println(scores);
//        System.out.println(list + " " +  piece.getLine_number() + " " + piece.getColumn_number());
        return list.iterator();
    }

    /**
     * Gives a score for a given orientation of the specified piece. The higher the better.
     * Orientations will be sorted according to the score value in descending order.
     * The more a score for an orientation is higher, the more this orientation will be tested first.
     * @param piece piece where the score is based on
     * @param orientationId piece orientation to compute
     * @return score as double
     */
    protected double score(Piece piece, Integer orientationId) {
        return 1;
    }

    private boolean entireRotation(Piece currentPiece) {
        if (this.m_nextOrientations.get(currentPiece) == null)  return false;
        return !this.m_nextOrientations.get(currentPiece).hasNext();
    }

}
