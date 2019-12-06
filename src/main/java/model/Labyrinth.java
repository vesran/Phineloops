package model;

import java.util.HashMap;

public class Labyrinth {
	
	private HashMap<Orientation,Boolean>[][] m_grid ; 
	
	
	public Labyrinth(int height , int width) {
		m_grid = new HashMap[height][width] ; 
		for(int i = 0 ; i < height ; i++) {
			 HashMap<Orientation,Boolean> maCase = new HashMap<Orientation,Boolean>() ;
			 for(int y=0 ; y < width ; y++) {
				 for(Orientation e :Orientation.values() ) {
					 maCase.put(e, Boolean.FALSE);
				 }
				 m_grid[i][y] = maCase ;
				 }
			}
		}

}
