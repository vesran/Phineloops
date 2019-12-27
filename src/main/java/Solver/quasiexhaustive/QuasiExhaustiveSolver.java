package Solver.quasiexhaustive;

import model.Level;
import model.enumtype.Orientation;
import model.pieces.*;

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

        Set<String> instances = new HashSet<>();

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
        do {
//            System.out.println("Start iteration");
//            System.out.println("In stack : ");
//            this.m_stack.stream().forEach(x -> System.out.print(x + " " + x.getLine_number() + " " + x.getColumn_number() + " "));
//            System.out.println();

            if (!this.m_antistack.isEmpty()) {
                out = this.m_antistack.poll();
                this.m_stack.push(out);

            } else {
                System.out.println("Test " + ++i + "\n" + this.m_level);

                // Check if the level is solved, no modification of the stacks should follow to keep things easier
                if (this.m_level.checkGrid()) {
                    return true;
                }

                if (instances.contains(this.m_level.toString())) {  // To remove
                    System.out.println("PB !");
                    (new Scanner(System.in)).next();
                }
                instances.add(this.m_level.toString()); // To remove
            }

            while (!this.m_stack.isEmpty() && this.entireRotation(this.m_stack.peek())) {
                out = this.m_stack.pop();
                this.m_antistack.add(out);
                this.m_nextOrientations.remove(out);
            }

            out = this.m_stack.peek();
            this.setToBestOrientation(out);

//            System.out.println("Checking conflict... " + out + " " + out.getLine_number() + " " + out.getColumn_number());
            while (this.inConflict(out)) {
//                System.out.println("Conflit!");
                // Go back in the stack
                while (this.entireRotation(out)) {
                    out = this.goBack(); // out is supposed to be the top of the stack
//                    if (out != null) System.out.println("Going back to top : " + out + " " + out.getLine_number() + " " + out.getColumn_number());
                }
                this.setToBestOrientation(out);
            }

        } while (!m_stack.isEmpty());

        return false;
    }

    private Piece goBack() {
        Piece out;
        out = this.m_stack.pop();
        this.m_nextOrientations.remove(out);
        this.m_antistack.add(out);
        return this.m_stack.peek();
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
            throw new RuntimeException("Piece has no more orientation left but hasn't been removed from nextOrientations");
        }
    }

    private boolean inConflict(Piece piece) {
        if (piece == null) return false;
        Set<Piece> neighborsInStack = this.getNeighborsInStack(piece);

        if (!piece.getNeighbor().keySet().containsAll(piece.orientatedTo())) {
            return true;
        }

        for (Map.Entry<Orientation, Piece> neighborInfo : piece.getNeighbor().entrySet()) {
            if (neighborsInStack.contains(neighborInfo.getValue())) {
                if (neighborInfo.getValue().orientatedTo().contains(neighborInfo.getKey().opposite()) &&
                        !piece.isConnectedTo(neighborInfo.getKey())) {
                    return true;
                } else if (!neighborInfo.getValue().orientatedTo().contains(neighborInfo.getKey().opposite()) &&
                        piece.orientatedTo().contains(neighborInfo.getKey())) {
                    return true;
                }
            }
        }
        return false;
    }

    private Set<Piece> getNeighborsInStack(Piece piece) {
        Set<Piece> neighborsInStack = new HashSet<>(this.m_stack);
        neighborsInStack.retainAll(piece.getNeighbor().values());
        return neighborsInStack;
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
        return -orientationId + 10;
    }

    private boolean entireRotation(Piece currentPiece) {
        if (this.m_nextOrientations.get(currentPiece) == null)  return false;
        return !this.m_nextOrientations.get(currentPiece).hasNext();
    }

    public static void main(String [] args) {
        Piece[][] grid = new Piece[3][3];
        grid[1][1] = new L(2, 1, 1);
        grid[1][0] = new L(0, 1, 0);
        grid[0][1] = new L(0, 0, 1);
        grid[2][1] = new Empty(100, 2, 1);
        grid[1][2] = new L(0, 1, 2);

        grid[0][0] = new Empty(100, 0, 0);
        grid[0][2] = new Empty(100, 0, 2);
        grid[2][2] = new Empty(100, 2, 2);
        grid[2][0] = new Empty(100, 2, 0);

        Level lvl = new Level(grid);
        lvl.init_neighbors();
        System.out.println(lvl);
        QuasiExhaustiveSolver solver = new QuasiExhaustiveSolver(lvl);

        solver.m_stack.addAll(grid[1][1].getNeighbor().values());
        System.out.println(solver.inConflict(grid[1][1]));
    }

}
