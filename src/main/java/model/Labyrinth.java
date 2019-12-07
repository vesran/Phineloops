package model;

import java.io.FileNotFoundException;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import org.jgrapht.alg.util.UnionFind;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.google.common.graph.Graph;

public class Labyrinth {
	
	private HashMap<Orientation,Boolean>[][] m_grid ; 
	private int m_height ; 
	private int m_width ; 
	
	
	public Labyrinth(int height , int width) {
		this.m_height = height;
		this.m_width = width;
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
	public void initLabyrinthKruskal() {
		int id = 0 ; 
		int [][] virtualGrid = new int[m_height][m_width];
		for(int i=0 ; i < this.m_height ; i++) {
			for(int y=0 ; y < this.m_width ; y++) {
				virtualGrid[i][y] = id ; 
				id++;
			}
		}
		boolean done = false ; 
		
		while(!done) {
			Random myRandom = new Random() ; 
			int y = 0;
			int x =0 ;
			int id_search = virtualGrid[x][y];
			
			if(x==0 && y==0) {
				for(int i = 0 ; i < m_height ; i++ ) {
					if(i % 2 == 0) {
						for(int y1=0 ; y1 <m_width ; y1++) {
							if(virtualGrid[i][y1] != id_search) {
								m_grid[i][y1-1].put(Orientation.EAST,Boolean.TRUE);
								m_grid[i][y1].put(Orientation.WEST,Boolean.TRUE);
								virtualGrid = this.replaceIdKruskal(virtualGrid[i][y1], id_search, virtualGrid);
								}
							}
						}else {
							for(int y1 = this.m_width - 1 ; y1 >-1 ; y1--) {
								if(virtualGrid[i][y1] != id_search) {
									if(y1 == this.m_width - 1) {
										m_grid[i-1][y1].put(Orientation.SOUTH,Boolean.TRUE);
										m_grid[i][y1].put(Orientation.NORTH,Boolean.TRUE);
										virtualGrid = this.replaceIdKruskal(virtualGrid[i][y1], id_search, virtualGrid);
										
									}else {
										m_grid[i][y1+1].put(Orientation.WEST,Boolean.TRUE);
										m_grid[i][y1].put(Orientation.EAST,Boolean.TRUE);
										virtualGrid = this.replaceIdKruskal(virtualGrid[i][y1], id_search, virtualGrid);
										}
									
									}
								
							}
							}
					
				}
				
				
				
			}else if(x == 0 && y == this.m_width -1) {
				
				
			}else if(y == 0 && (!(x==0))){
				
				
			}else if(x== 0 && (!(y==0))) {
				
			}else if((!(x==0)) && y == this.m_width -1 ) {
				
				
			}else if(x == this.m_height - 1 && y==this.m_width - 1) {
				
			}else if (x == this.m_height - 1 && y==0) {
				
			}else if (x ==  this.m_height - 1 && (!(y==0))){
				
			}else {
				
			}
			
			
			
		}
	}
	
	private int[][] replaceIdKruskal(int id , int newId , int[][]grid) {
		int[][] ret = new int[grid.length][grid[0].length];
		for(int i=0 ; i < grid.length ; i++) {
			for(int y=0 ; y < grid[0].length ;y++) {
				if(grid[i][y] == id) {
					ret[i][y] = newId ;
				}else {
					ret[i][y] = grid[i][y] ; 
					
				}
				
			}
		}
		return ret ; 
		
	}

	
	 public static void main(String[] args) throws FileNotFoundException  {
		 
		 new Labyrinth(2,2).initLabyrinthKruskal();
		 System.out.print("");
		 
	 }

}
