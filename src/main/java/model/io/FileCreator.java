package model.io;

import model.Level;
import model.pieces.Piece;

import java.io.*;


/**
 * Contains the method to write a level instance in a file.
 */
public class FileCreator {

    /**
     * Writes the given level instance to the specified path.
     * @param grid level's grid that contains pieces to write in the file.
     * @param filename the name of the file
     */
    public static void write(Piece[][] grid, String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));

            bw.write(grid.length + "\n");      // Write height on fist line
            bw.write(grid[0].length + "\n");   // Write width on second line
            for (Piece [] column : grid) {         // Iterators are faster
                for (Piece currentPiece : column) {
                    bw.write(currentPiece.getId() + " " + currentPiece.getOrientation());
                    bw.write("\n");
                }
            }

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(Level lvl, String filename) {
    	write(lvl.getGrid(), filename);
    }

}
