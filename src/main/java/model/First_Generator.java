package model;

import java.util.ArrayList;
import java.util.Random;

public class First_Generator implements Generator {
	public int ccnumber;

	public First_Generator(int width, int height, int ccnumber) {
		this.generate(width, height, ccnumber);
		this.ccnumber = ccnumber;
	}

	public int checkCc(Noeud m[][], int width, int height) {
		ArrayList<Integer> r = new ArrayList<Integer>();
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				if (!r.contains(m[i][j].pere))
					r.add(m[i][j].pere);
			}
		return r.size();

	}

	@Override
	public Level generate(int width, int height, int ccnumber) {
		int x, y, z, w, h;
		int p = 1;
		int e = 0;
		w = width - 1;
		h = height - 1;
		int c = h * width + w * height;
		Random random = new Random();
		Noeud m[][] = new Noeud[width][height];
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				m[i][j] = new Noeud(i, j, e);
				e++;
			}
		if (width * height % 2 == 0)
			if (width % 2 == 0) {
				for (int i = 1; i < width; i++)
					for (int j = 0; j < height; j++)
						if (m[i][j].x % 2 != 0) {
							m[i][j].pere = m[i - 1][j].pere;
							m[i][j].murs.replace(0, true);
							m[i-1][j].murs.replace(2, true);
						}
			} else {
				for (int i = 0; i < width; i++)
					for (int j = 1; j < height; j++)
						if (m[i][j].y % 2 != 0) {
							m[i][j].pere = m[i][j - 1].pere;
							m[j][j].murs.replace(3, true);
							m[j][j-1].murs.replace(1, true);
						}
			}
		else {
			m[0][1].pere = m[0][0].pere;
			m[0][2].pere = m[0][0].pere;
			m[0][0].murs.replace(1, true);
			m[0][1].murs.replace(1, true);
			m[0][1].murs.replace(3, true);
			m[0][2].murs.replace(3, true);
			for (int i = 3; i < height; i++)
				if (m[0][i].y % 2 == 0) {
					m[0][i].pere = m[0][i - 1].pere;
					m[0][i].murs.replace(3, true);
					m[0][i-1].murs.replace(1, true);
				}
			for (int i = 1; i < width; i++)
				for (int j = 0; j < height; j++)
					if (m[i][j].x % 2 == 0) {
						m[i][j].pere = m[i - 1][j].pere;
						m[i][j].murs.replace(0, true);
						m[i-1][j].murs.replace(2, true);
					}
		}

//		if (width * height % 2 == 0)
//			if (width % 2 == 0) {
//				while (p < width-1)
//					for (int j = 0; j < height; j++) {
//						m[p-1][j].pere = m[p][j].pere;
//						p+= 2;
//						System.out.println(m[p][j].pere+" "+ m[p][j]);
//					}
//			} else {
//				for (int i = 0; i < width; i++)
//					while(p < height) {
//						m[i][p].pere = m[i][p - 1].pere;
//						p+=2;
//					}
//			}

		while (this.checkCc(m, width, height) > ccnumber) {
			x = random.nextInt(width);
			y = random.nextInt(height);
			z = random.nextInt(4);
			System.out.println("la valeur de c est " + c + " et on arrive pas a sortir de cette boucle car ");
			if (z == 0 && x > 0) {
				if (!(m[x][y].find() == m[x - 1][y].find())) {
					System.out.println("fusion vers le haut de " + m[x][y] + " avec " + m[x - 1][y] + " car "
							+ m[x][y].find() + " n'est pas egal a" + m[x - 1][y].find());
					m[x - 1][y].union(m[x][y].pere, m, width, height);
					m[x - 1][y].murs.replace(2, true);
					m[x][y].murs.replace(0, true);
					c--;
					System.out.println("la valeur de ccccc " + c);
				}
			} else if (z == 1 && y + 1 < height) {
				if (!(m[x][y].find() == m[x][y + 1].find())) {
					System.out.println("fusion a droite de " + m[x][y] + " avec " + m[x][y + 1] + " car "
							+ m[x][y].find() + " n'est pas egal a" + m[x][y + 1].find());
					m[x][y + 1].union(m[x][y].pere, m, width, height);
					c--;
					m[x][y].murs.replace(1, true);
					m[x][y+1].murs.replace(3, true);
					System.out.println("la valeur de ccccc " + c);
				}
			} else if (z == 2 && x + 1 < width) {

				if (!(m[x][y].find() == m[x + 1][y].find())) {
					System.out.println("fusion vers le bas de " + m[x][y] + " avec " + m[x + 1][y] + " car "
							+ m[x][y].find() + " n'est pas egal a" + m[x + 1][y].find());
					m[x + 1][y].union(m[x][y].pere, m, width, height);
					c--;
					m[x][y].murs.replace(2, true);
					m[x+1][y].murs.replace(0, true);
					System.out.println("la valeur de ccccc " + c);

				}

			} else {
				if (y > 0)
					if (!(m[x][y].find() == m[x][y - 1].find())) {
						System.out.println("fusion a gauche de " + m[x][y] + " avec " + m[x][y - 1] + " car "
								+ m[x][y].find() + " n'est pas egal a" + m[x][y - 1].find());
						m[x][y - 1].union(m[x][y].pere, m, width, height);
						c--;
						m[x][y].murs.replace(3, true);
						m[x][y-1].murs.replace(1, true);
						System.out.println("la valeur de ccccc " + c);
					}
			}

		}
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				System.out.println("i " + i + " j " + j + " pere " + m[i][j].pere);
				System.out.println(c);
			}
		System.out.println("fin");
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++)
				System.out.print(m[i][j].pere + " ");
			System.out.println();
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++)
				System.out.print(""+m[i][j].printMap());
			System.out.println();
		}
		return null;
	}

	public static void main(String[] args) {
		First_Generator f = new First_Generator(4, 4, 4);
		System.out.println("fi");
		System.out.println();
	}
	
	
	/*
	 * @Override public Level generate(int width, int height, int ccnumber) { Random
	 * random = new Random(); ArrayList<Noeud> listepere = new ArrayList<Noeud>();
	 * ArrayList<Noeud> liste = new ArrayList<Noeud>(); Noeud m[][] = new
	 * Noeud[width][height]; for (int i = 0; i < width; i++) for (int j = 0; j <
	 * height; j++) m[i][j] = new Noeud(i, j); for (int i = 0; i < ccnumber; i++)
	 * listepere.add(m[random.nextInt(width)][random.nextInt(height)]); for(Noeud
	 * noeud : listepere)
	 * 
	 * while(!liste.isEmpty()) {
	 * 
	 * } return null;
	 * 
	 * }
	 */
}
