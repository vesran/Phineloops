package view;

import java.io.File;

import Solver.Csp;
import Solver.Extend;
import Solver.Satisfiability;
import Solver.quasiexhaustive.QuasiExhaustiveSolver;
import controller.RotationController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;
import model.io.FileReader;
import model.pieces.Empty;
import model.pieces.Piece;
import model.pieces.T;
import view.pieces.PieceDrawing;

public class PhineLoopsMainGUI extends Application {

	static final int DEFAULT_WIDTH = 700;
	static final int DEFAULT_HEIGHT = 700;
	public static boolean solverApplied = false;	// Tells if the goal is to visualize a solver or only displaying
	private static boolean solverMustWait = false; 	// Makes the solver wait until the window shows up
	private static final Object startUpMonitor = new Object();	// Synchronizes the solver and window starting up
	private static Level level;

	/**
	 * Displays GUI where user interactions are considered.
	 * @param lvl Game level to display on screen.
	 */
	public static void display(Level lvl) {
		level = lvl;
		solverMustWait = false;
		Application.launch();
	}

	/**
	 * Displays the solver working and testing different possibilities. User interactions are not taken into
	 * consideration
	 * @param lvl Level to solve
	 * @param solver Solver to use, must be load with the lvl.
	 */
	public static void displaySolving(Level lvl, QuasiExhaustiveSolver solver) {
		solverApplied = true;
		Thread displayLevel = new Thread(new Runnable() {
			@Override
			public void run() {
				display(lvl);
				solverApplied = false;
				// Release rotationMonitor so that the solver does not get stuck and wait forever
				synchronized (PieceDrawing.rotationMonitor) {
					PieceDrawing.rotationMonitor.notify();
				}
			}
		});

		Thread solveLevel = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (startUpMonitor) {
					try {
						solverMustWait = true;
						startUpMonitor.wait();
						solverMustWait = false;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				solver.solving();
			}
		});

		displayLevel.start();
		solveLevel.start();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Group root = new Group();
		Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		GridPane grid = new GridPane();
		grid.setVgap(0);
		grid.setHgap(0);
		grid.setGridLinesVisible(true); // For debugging : to remove
		grid.setAlignment(Pos.CENTER);
		grid.prefWidthProperty().bind(scene.widthProperty());
		grid.prefHeightProperty().bind(scene.heightProperty());
		LevelDrawing view = new LevelDrawing(level);
		view.draw(grid, scene);

		if (!solverApplied) {
			// Adding a controller to each node so that we don't need to retrieve which one was clicked
			for (Node item : grid.getChildren()) {
				if (item != grid) {
					item.setOnMouseClicked(new RotationController(item));
				}
			}
		}

		grid.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		root.getChildren().add(grid);
		stage.setTitle("Phine Loops Game");
		stage.setScene(scene);
//		stage.setMaximized(true); // Full screen
		stage.setOnCloseRequest(e -> {
			// Makes the solver continue to work without the stage
			synchronized (PieceDrawing.rotationMonitor) {
				PieceDrawing.rotationMonitor.notify();
			}
		});
		stage.show();

		// Loop until the solver thread start to wait and then notify it
		while (solverMustWait) {
			synchronized (startUpMonitor) {
				startUpMonitor.notifyAll();
			}
		}
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
		level.setGrid(FileReader.getGrid(
				"C:\\Users\\Bilal\\git\\phineloops-kby\\instances\\public\\grid_32x32_dist.1_vflip.true_hflip.true_messedup.false_id.1.dat"," "));
		int num =  0 ; 
		for (File file : listOfFiles) {
			if (file.isFile()) {
				
				moncsp = new Csp(FileReader.getGrid(file.getAbsolutePath(), " "));
				long debut = System.currentTimeMillis();
				boolean aa = moncsp.solving(Extend.noExtend);
				long fin = System.currentTimeMillis();
				
				level.setGrid(moncsp.getMyLevelToSolve());
				level.init_neighbors();
				
				if(aa == level.checkGrid()) {
					System.out.println(file);
					System.out.println(fin-debut + "  " + num + "  "+ aa ) ; 
					
					
				}
				num++ ; 
				
				
				
				
				
				
			}
		}
		
		//System.out.println(duree +"  " + nb); 
		/*Piece[][] tab = FileReader.getGrid(
				"C:\\Users\\Bilal\\git\\phineloops-kby\\instances\\public\\grid_8x8_dist.0_vflip.false_hflip.true_messedup.false_id.1.dat",
				" ");// grid_32x32_dist.1_vflip.true_hflip.true_messedup.false_id.1.dat
		// moncsp = new Csp(tab) ;
		// moncsp.solving() ;
		
		
		

		System.out.println("This instruction will not be executed if the window is not closed");*/
		}
	}

