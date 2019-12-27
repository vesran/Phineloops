package Solver.quasiexhaustive;

import model.Level;
import model.enumtype.Orientation;
import model.pieces.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class implements an algorithm for solving the InfinityLoop's grid. It is based on a tree search with an
 * iterative exploration of the tree using stacks. It contained a main stack where each pieces has a fixed orientation
 * ready to be tested. When this stack needs updated, ie change an orientation of a piece that is in the middle of the
 * stack, the top of the piece is iteratively taken out and put to the anti-stack that contains pieces where the
 * orientations are not defined yet and the last pieces to explore in the tree.
 * All possibilities are not tested but only the relevant ones. Piece orientations that create conflict with other
 * pieces are not tested and its subtree is not explore.
 * The ordering of exploration (piece by piece) is determined by a comparator over pieces. It is made so that
 * an override is possible. See Comparator<Piece> piecesComparator().
 * The sequence of orientation to test is also customizable and overrable.
 * See double score(Piece piece, Integer orientationId)
 */
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

    /**
     * Method to invoke to solve the level instance. The algorithm is based on a tree search and is able to detect
     * conflicts, ie a piece orientation that compromises the orientation of other pieces that has been considered.
     * @return true if the solver has been able to solve the grid, false otherwise
     */
    public boolean solving() {
        int i = 0;

        Set<String> instances = new HashSet<>();

        // Init stack and set each piece to their best orientation
        for (Piece[] row : this.m_level.getGrid()) {
            for (Piece currentPiece : row) {
                // Exclude empty and X piece because their orientation doesn't matter
                if (currentPiece.getId() != 0 && currentPiece.getId() != 4) {
                    this.m_antistack.add(currentPiece); // First sorting of pieces
                }
            }
        }

        // Starting to test orientations for all pieces
        do {
            // Each piece of the stack does not prompt conflicts, we can import a piece from the anti-stack to the stack
            if (!this.m_antistack.isEmpty()) {
                this.m_stack.push(this.m_antistack.poll());

            } else {
                System.out.println("Test " + ++i + "\n" + this.m_level);

                // Check if the level is solved
                if (this.m_level.checkGrid()) {
                    return true;
                }

                if (instances.contains(this.m_level.toString())) {  // To remove
                    System.out.println("PB !");
                    (new Scanner(System.in)).next();
                }
                instances.add(this.m_level.toString()); // To remove
            }

            // Top piece in stack has made an entire rotation so it needs to go to the anti-stack so that the second top
            // can be modified
            while (!this.m_stack.isEmpty() && this.entireRotation(this.m_stack.peek())) {
                this.goBack();
            }

            this.setToBestOrientation(this.m_stack.peek());

            // Go back in the stack if any conflict in the current situation appears
            while (this.inConflict(this.m_stack.peek())) {
                // Go back in the stack
                while (this.entireRotation(this.m_stack.peek())) {
                    this.goBack(); // out is supposed to be the top of the stack
                }
                // Set the new top of the stack to its new best position
                this.setToBestOrientation(this.m_stack.peek());
            }

        } while (!m_stack.isEmpty());

        // Level not solvable, all relevant possibilities has been tested
        return false;
    }

    /**
     * Pops the current top of the stack to the anti-stack and clear its remaining orientations
     */
    private void goBack() {
        Piece out;
        out = this.m_stack.pop();
        this.m_nextOrientations.remove(out);
        this.m_antistack.add(out);
    }

    /**
     * Set the top piece of the stack to its best orientation according to score function
     * @param piece Grid's piece to change orientation
     */
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
            throw new RuntimeException("Piece has no more orientation left but hasn't been removed from nextOrientations");
        }
    }

    private boolean inConflict(Piece piece) {
        if (piece == null) return false;
        Set<Piece> neighborsInStack = this.getNeighborsInStack(piece);

        // Check if the given piece's branches is facing an empty piece or the grid borders
        if (!piece.getNeighbor().keySet().containsAll(piece.orientatedTo())) {
            return true;
        }

        for (Map.Entry<Orientation, Piece> neighborInfo : piece.getNeighbor().entrySet()) {
            if (neighborsInStack.contains(neighborInfo.getValue())) {
                // Check if the given piece is refusing to connect to its neighborhood
                if (neighborInfo.getValue().orientatedTo().contains(neighborInfo.getKey().opposite()) &&
                        !piece.isConnectedTo(neighborInfo.getKey())) {
                    return true;

                    // Check if the neighborhood is refusing to connect to the given piece
                } else if (!neighborInfo.getValue().orientatedTo().contains(neighborInfo.getKey().opposite()) &&
                        piece.orientatedTo().contains(neighborInfo.getKey())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Computes the intersection between the neighborhood of the given piece and the current stack.
     * @param piece Piece in which the neighborhood is taken from
     * @return Set of pieces
     */
    private Set<Piece> getNeighborsInStack(Piece piece) {
        Set<Piece> neighborsInStack = new HashSet<>(this.m_stack);
        neighborsInStack.retainAll(piece.getNeighbor().values());
        return neighborsInStack;
    }

    /**
     * Computes and sorts the possible orientations for a given piece. The more an orientation is probable, the more
     * it will be placed to the first rank
     * @param piece Piece in which the orientations are wanted
     * @return An iterator over the sequence of orientations.
     */
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
        return -orientationId + 10;
    }

    /**
     * Determines if a piece has made an entire rotation, ie is about to come its initial orientation.
     * @param currentPiece piece to study
     * @return true if the given piece is about to make an entire rotation, false otherwise
     */
    private boolean entireRotation(Piece currentPiece) {
        if (this.m_nextOrientations.get(currentPiece) == null)  return false;
        return !this.m_nextOrientations.get(currentPiece).hasNext();
    }

}
