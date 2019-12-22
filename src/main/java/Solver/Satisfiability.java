package Solver;

import model.pieces.Piece;

public class Satisfiability {
	public static boolean check(Piece[][] grid) {
		return Satisfiability.getNumberOfConnectionAll(grid);
	}

	public static boolean getNumberOfConnectionAll(Piece[][] grid) {
		int num = 0;
		int un = grid.length * 6 - 4;
		int deux = grid[0].length * 6 - 4;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				Class myClass = grid[i][j].getClass();
				switch (myClass.getName()) {
				case "model.pieces.Bar":
					num += 2;
					break;
				case "model.pieces.L":
					num += 2;
					break;
				case "model.pieces.Empty":
					break;
				case "model.pieces.Circle":
					num += 1;
					break;
				case "model.pieces.T":
					num += 3;
					break;
				case "model.pieces.X":
					num += 4;
					break;
				}
			}
			}
			if((num % 2 == 0)) {
				return true ; 
				
			}else {
				System.out.println(num) ; 
				return false ; 
			}
		
	}

	




}
