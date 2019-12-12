package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {

	public static Piece[][] getGrid(String pathProblem, String separator) {
		Path path = Paths.get(pathProblem);
		try {
			long line = Files.lines(path).count();
			BufferedReader reader = new BufferedReader(new java.io.FileReader(pathProblem));
			int numLigne = 0;
			int i = -1;
			int j = 0;
			String ligne = new String();
			int height = 0, weight = 0;
			Piece[][] grid = null;
			boolean firstElement = false;
			while ((ligne = reader.readLine()) != null) {

				switch (numLigne) {
				case 0:
					height = Integer.valueOf(ligne).intValue();
					break;
				case 1:
					weight = Integer.valueOf(ligne).intValue();
					if (height != weight || line != (height * weight + 2)) {
					}
					grid = new Piece[height][weight];
					break;

				default:
					if ((numLigne - 2) % weight == 0) {
						i++;
						j = 0;
						firstElement = true;

					} else if (!firstElement) {
						j++;
					}

					String tab[] = ligne.split(separator);
					short numPiece = Short.valueOf(tab[0]).shortValue();
					short orientationPiece = Short.valueOf(tab[1]).shortValue();
					Class<?> classToInstantiate = null;
					for (PieceClass e : PieceClass.values()) {
						if (e.getId() == numPiece) {
							classToInstantiate = e.getClasse();
							break;
						}
					}
					try {
						Constructor<?> constructor = classToInstantiate.getConstructor(int.class, int.class, int.class);
						try {
							Piece myPiece = (Piece) constructor.newInstance((int) orientationPiece, i, j);
							grid[i][j] = myPiece;
							firstElement = false;

						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (NoSuchMethodException | SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					break;
				}
				numLigne++;

			}

		} catch (IOException e) {
			throw new IllegalArgumentException("Path must specifiy a file");
		}

		return null;

	}

}
