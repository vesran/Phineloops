package model;

import java.io.*;

public class FileCreator {

    public static void write(Level lvl, String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));

            bw.write(lvl.getGrid().length + "\n");      // Write height on fist line
            bw.write(lvl.getGrid()[0].length + "\n");   // Write width on second line
            for (Piece [] column : lvl.getGrid()) {         // Iterators are faster
                for (Piece currentPiece : column) {
                    bw.write(currentPiece.id + " " + currentPiece.orientation);
                    bw.write("\n");
                }
            }

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
