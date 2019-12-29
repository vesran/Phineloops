package model;

import java.util.ArrayList;
import java.util.Random;

import org.mockito.internal.matchers.InstanceOf;

import model.pieces.Bar;
import model.pieces.Circle;
import model.pieces.Empty;
import model.pieces.L;
import model.pieces.Piece;
import model.pieces.T;
import model.pieces.X;
import view.PhineLoopsMainGUI;

public class First_Generator implements Generator {
	public int ccnumber;
	Level l;

	public First_Generator(int width, int height, int ccnumber) {
		l = this.generate(width, height, ccnumber);

		this.ccnumber = ccnumber;
	}

	public int checkCc(Noeud m[][], int width, int height) {
		ArrayList<Integer> r = new ArrayList<Integer>();
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				if (!r.contains(m[i][j].pere) && m[i][j].pere >= 0)
					r.add(m[i][j].pere);
			}
		return r.size();

	}

	@Override
	public Level generate(int width, int height, int ccnumber) {
		Level l = new Level(width, height);
		int x, y, z, w, h;
		int p = 1;
		int e = 1;
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
							m[i - 1][j].murs.replace(2, true);
						}
			} else {
				for (int i = 0; i < width; i++)
					for (int j = 1; j < height; j++)
						if (m[i][j].y % 2 != 0) {
							m[i][j].pere = m[i][j - 1].pere;
							m[j][j].murs.replace(3, true);
							m[j][j - 1].murs.replace(1, true);
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
					m[0][i - 1].murs.replace(1, true);
				}
			for (int i = 1; i < width; i++)
				for (int j = 0; j < height; j++)
					if (m[i][j].x % 2 == 0) {
						m[i][j].pere = m[i - 1][j].pere;
						m[i][j].murs.replace(0, true);
						m[i - 1][j].murs.replace(2, true);
					}
		}
//		this.createEmpty(m, height, width);
		while (this.checkCc(m, width, height) > ccnumber) {
			x = random.nextInt(width);
			y = random.nextInt(height);
			z = random.nextInt(4);

			if (z == 0 && x > 0) {
				if (!(m[x][y].find() == m[x - 1][y].find())) {
					m[x - 1][y].union(m[x][y].pere, m, width, height);
					m[x - 1][y].murs.replace(2, true);
					m[x][y].murs.replace(0, true);
					c--;
				}
			} else if (z == 1 && y + 1 < height) {
				if (!(m[x][y].find() == m[x][y + 1].find())) {
					m[x][y + 1].union(m[x][y].pere, m, width, height);
					c--;
					m[x][y].murs.replace(1, true);
					m[x][y + 1].murs.replace(3, true);
				}
			} else if (z == 2 && x + 1 < width) {

				if (!(m[x][y].find() == m[x + 1][y].find())) {
					m[x + 1][y].union(m[x][y].pere, m, width, height);
					c--;
					m[x][y].murs.replace(2, true);
					m[x + 1][y].murs.replace(0, true);
				}

			} else {
				if (y > 0)
					if (!(m[x][y].find() == m[x][y - 1].find())) {
						m[x][y - 1].union(m[x][y].pere, m, width, height);
						c--;
						m[x][y].murs.replace(3, true);
						m[x][y - 1].murs.replace(1, true);
					}
			}

		}
		System.out.println();
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				l.grid[i][j] = this.guessPiece(m[i][j]);
		return l;
	}

	public void createEmpty(Noeud m[][], int height, int width) {
		Random random = new Random();
		boolean b = false;
		int number = random.nextInt(height * width / 8);
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		for (int i = 0; i < number; i++) {
			while (!b) {
				if (m[x][y].pere > 0) {
					for (int j = 0; j < width; j++) {
						for (int j2 = 0; j2 < height; j2++) {
							if (m[j][j2].pere == m[x][y].pere)
								m[j][j2].pere *= -1;
							m[j][j2].replaceValue(m);
							m[j][j2].murs.replace(0, false);
							m[j][j2].murs.replace(1, false);
							m[j][j2].murs.replace(2, false);
							m[j][j2].murs.replace(0, false);
						}
					}
					m[x][y].replaceValue(m);
					m[x][y].murs.replace(0, false);
					m[x][y].murs.replace(1, false);
					m[x][y].murs.replace(2, false);
					m[x][y].murs.replace(0, false);
					b = true;
				} else {
					x = random.nextInt(width);
					y = random.nextInt(height);
				}
			}
			b = false;
		}
	}

	public int sizeOfConnexcomposant(Noeud m[][], int height, int width, Noeud noeud) {
		int size = 0;
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				if (m[i][j].pere == noeud.pere && noeud != m[i][j])
					size++;
		return size;
	}

	public Piece guessPiece(Noeud noeud) {

		switch (noeud.numberOfNeighbors()) {
		case 4:
			return new X(0, noeud.x, noeud.y);
		case 3:
			return new T(0, noeud.x, noeud.y);
		case 2:
			if ((noeud.murs.get(0).booleanValue() == true && noeud.murs.get(2).booleanValue() == true)
					|| (noeud.murs.get(1).booleanValue() == true && noeud.murs.get(3).booleanValue() == true))
				return new Bar(0, noeud.x, noeud.y);
			else
				return new L(0, noeud.x, noeud.y);
		case 1:
			return new Circle(0, noeud.x, noeud.y);
		default:
			return new Empty(0, noeud.x, noeud.y);
		}
	}

	public static void main(String[] args) {
		First_Generator f = new First_Generator(5, 7, 3);
		PhineLoopsMainGUI.display(f.l);
	}
}
