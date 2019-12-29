package Solver.quasiexhaustive;

import Solver.quasiexhaustive.comparaison.Lexicographic;
import model.Level;
import model.enumtype.Orientation;
import model.pieces.*;

import java.util.*;

/**
 * @author Karim Amrouche
 * @author Bilal Khaldi
 * @author Yves Tran
 *
 * This class implements an algorithm for solving the InfinityLoop's grid. It is based on a tree search with an
 * iterative exploration of the tree using stacks. It contained a main stack where each pieces has a fixed orientation
 * ready to be tested. When this stack needs updated, ie change an orientation of a piece that is in the middle of the
 * stack, the top of the piece is iteratively taken out and put to the anti-stack that contains pieces where the
 * orientations are not defined yet and the last pieces to explore in the tree.
 * All possibilities are not tested but only the relevant ones. Piece orientations that create conflict with other
 * pieces are not tested and its subtree is not explore.
 * The ordering of exploration (piece by piece) is determined by a comparator over pieces. It is made so that
 * an override is possible. By default, it explores in lexicographic order.
 */
public class QuasiExhaustiveSolver {

    protected Level m_level;
    private Deque<Piece> m_stack;
    private Deque<Piece> m_antistack;
    private Map<Piece, Iterator<Integer>> m_nextOrientations;
    private final Comparator<Piece> piecesComparator; // The greater a piece is, the more its orientation tends to be changed first.

    public QuasiExhaustiveSolver(Comparator<Piece> piecesComparator) {
        this.m_stack = new ArrayDeque<>();
        this.m_antistack = new ArrayDeque<>();
        this.m_nextOrientations = new HashMap<>();
        this.piecesComparator = piecesComparator;
    }

    public QuasiExhaustiveSolver(Level lvl, Comparator<Piece> piecesComparator) {
        this(piecesComparator);
        this.m_level = lvl;
    }

    public QuasiExhaustiveSolver(Level lvl) {
        this(lvl, new Lexicographic());
        this.m_level = lvl;
    }

    /**
     * Method to invoke to solve the level instance. The algorithm is based on a tree search and is able to detect
     * conflicts, ie a piece orientation that compromises the orientation of other pieces that has been considered.
     * @return true if the solver has been able to solve the grid, false otherwise
     */
    public boolean solving() {
        int i = 0;

        List<Piece> orderingPieces = new ArrayList<>();

        // Init stack and set each piece to their best orientation
        for (Piece[] row : this.m_level.getGrid()) {
            for (Piece currentPiece : row) {
                // Exclude empty and X piece because their orientation doesn't matter
                if (currentPiece.getId() != 0 && currentPiece.getId() != 4) {
                    orderingPieces.add(currentPiece); // First sorting of pieces
                }
            }
        }

        // Transfer everything to anti-stack and preserve the ordering
        Collections.sort(orderingPieces, this.piecesComparator);
        this.m_antistack.addAll(orderingPieces);

        // Starting to test orientations for all pieces
        do {
            // Each piece of the stack does not prompt conflicts, we can import a piece from the anti-stack to the stack
            if (!this.m_antistack.isEmpty()) {
                this.m_stack.push(this.m_antistack.pop());

            } else {
                System.out.println("Test " + ++i + "\n" + this.m_level);

                // Check if the level is solved
                if (this.m_level.checkGrid()) {
                    return true;
                }
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
                    this.goBack();
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
        Piece out = this.m_stack.pop();
        this.m_nextOrientations.remove(out);
        this.m_antistack.push(out);
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
        List<Integer> list = new ArrayList<>();
        int originalOrientation = piece.getOrientation();
        for (int i = 0; i < piece.getNumberOfOrientations(); i++) {
            list.add((i + originalOrientation) % piece.getNumberOfOrientations());
        }
        return list.iterator();
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
