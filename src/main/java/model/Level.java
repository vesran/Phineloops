package model;

public class Level {
	Piece[][] grid;
	public Level(int height, int width) {
		grid = new Piece[height][width];
	}
}
