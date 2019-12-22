package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class First_Generator implements Generator {
	public static int c = 0;

	@Override
	public Level generate(int width, int height, int ccnumber) {
		int x, y, z;
		c = width * height;
		Random random = new Random();
		Noeud m[][] = new Noeud[width][height];
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				m[i][j] = new Noeud(i, j);
			}
		while (c > ccnumber) {
			x = random.nextInt(height);
			y = random.nextInt(height);
			z = random.nextInt(4);
			if (z == 0) {
				if(x > 0)
					if (m[x][y].find() != m[x - 1][y].find()) {
						m[x][y].union(m[x - 1][y]);
						c--;
						System.out.println("haut");
					}
			} else 
				if (z == 1){
					 if(y+1 < height)
						 if (m[x][y].find() != m[x][y + 1].find()) {
							 m[x][y].union(m[x][y + 1]);
							 c--;
							 System.out.println("droite");
						 }
				}
				else 
					if(z == 2) {
						if( x+1 < width)
							if(m[x][y].find() != m[x+1][y].find()) {
								m[x][y].union(m[x+1][y]);
								c--;
								System.out.println("bas");
							}
						
					}
					else {
						if(y>0)
							if (m[x][y].find() != m[x][y-1].find()) {
								m[x][y].union(m[x][y-1]);
								c--;
								System.out.println("gauche");
							}
					}

		}
		return null;
	}

	public static void main(String[] args) {
		First_Generator f = new First_Generator();
		f.generate(4, 4, 2);
	}
}
