package view;

import java.io.File;

import Solver.Csp;
import Solver.Extend;
import Solver.Satisfiability;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;
import model.io.FileReader;
import model.pieces.Empty;
import model.pieces.Piece;
import model.pieces.T;

public class PhineLoopsMainGUI extends Application {
	static final int WIDTH = 700;
	static final int HEIGHT = 700;
	private static Level level;

	public static void display(Level lvl) {
		level = lvl;
		Application.launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Group root = new Group();
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		GridPane grid = new GridPane();
		grid.setVgap(0);
		grid.setHgap(0);
		grid.setGridLinesVisible(true); // For debugging : to remove
		grid.setAlignment(Pos.CENTER);
		grid.prefWidthProperty().bind(scene.widthProperty());
		grid.prefHeightProperty().bind(scene.heightProperty());
		LevelDrawing view = new LevelDrawing(level);
		view.draw(grid, scene);
		grid.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		root.getChildren().add(grid);
		stage.setTitle("Phine Loops Game");
		stage.setScene(scene);
		stage.setMaximized(true); // Full screen
		stage.show();
	}

	// Tmp method : for debugging only
	public static Level initLevel() {
		int width = 3;
		int height = 9;
		Level lvl = new Level(height, width);
		Piece[][] grid = lvl.getGrid();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (j != i) {
					grid[j][i] = new T(3, j, i);
				} else {
					grid[j][i] = new Empty(0, j, i);
				}
			}
		}
		System.out.println(grid);
		return lvl;
	}

	public static void main(String[] args) {
		Level level = initLevel();
		File folder = new File("/Users/bilal/git/phineloops-kby/instances/public/");
		File[] listOfFiles = folder.listFiles();
		Csp moncsp = null;
		long duree = 0 ; 
		int nb = 0 ;
		for (File file : listOfFiles) {
			if (file.isFile()) {
				moncsp = new Csp(FileReader.getGrid(file.getAbsolutePath(), " "));
				if(moncsp.divideReign()) {
					long startTime = System.currentTimeMillis();
					boolean sol = moncsp.solving(Extend.noExtend);
					long endTime = System.currentTimeMillis();
					duree += (endTime - startTime ); 
					nb++ ; 
					
				}
				
				
				
			}
		}
		
		System.out.println(duree +"  " + nb); 
		/*Piece[][] tab = FileReader.getGrid(
				"C:\\Users\\Bilal\\git\\phineloops-kby\\instances\\public\\grid_8x8_dist.0_vflip.false_hflip.true_messedup.false_id.1.dat",
				" ");// grid_32x32_dist.1_vflip.true_hflip.true_messedup.false_id.1.dat
		// moncsp = new Csp(tab) ;
		// moncsp.solving() ;
		
			// display(level);
		

		System.out.println("This instruction will not be executed if the window is not closed");*/
		}
	}