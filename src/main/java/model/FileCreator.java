package model;

import java.io.*;

public class FileCreator {

    public static void write(Level lvl, String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));

            bw.write(lvl.getGrid()[0].length + "\n");
            bw.write(lvl.getGrid().length + "\n");
            for (Piece [] column : lvl.getGrid()) {
                for (Piece currentPiece : column) {
                    bw.write(currentPiece.id + " " + currentPiece.orientation);
                    bw.write("\n");
                }
            }

            bw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
