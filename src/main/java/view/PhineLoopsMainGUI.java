package view;

import java.io.File;

import Solver.Csp;
import Solver.Extend;
import Solver.Satisfiability;
import Solver.quasiexhaustive.QuasiExhaustiveSolver;
import Solver.quasiexhaustive.comparaison.DiagonalShift;
import Solver.quasiexhaustive.comparaison.Lexicographic;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Level;
import model.io.FileReader;
import model.pieces.Empty;
import model.pieces.Piece;
import model.pieces.T;
import view.pieces.PieceDrawing;

/**
 * @author Karim Amrouche
 * @author Bilal Khaldi
 * @author Yves Tran
 *
 * This class is used to visualise the game. It is possible watch an exhaustive solver working on the grid instance,
 * trying to solve the level.
 */
public class PhineLoopsMainGUI extends Application {

	private static final int DEFAULT_WIDTH = 700;
	private static final int DEFAULT_HEIGHT = 700;
	private static boolean windowsOn = false;
	public static boolean solverApplied = false;	// Tells if the goal is to visualize a solver or only displaying
	private static boolean solverWaiting = false; 	// Makes the solver wait until the window shows up
	private static final Object startUpMonitor = new Object();	// Synchronizes the solver and window starting up
	private static Level level;
	private static LevelDrawing view;

	/**
	 * Displays GUI where user interactions are considered.
	 * @param lvl Game level to display on screen.
	 */
	public static void display(Level lvl) {
		level = lvl;

		// Don't throw an exception, let the solver working
		if (lvl.getGrid().length * lvl.getGrid()[0].length > 32*32) {
			System.out.println("Grid too large to be loaded, must be 32x32 or lower. View has been cancelled but the solver is still running if any.");

		} else {
			windowsOn = true;
			Application.launch();
		}
	}

	/**
	 * Displays the solver working and testing different possibilities. User interactions are not taken into
	 * consideration
	 * @param lvl Level to solve
	 */
	public static void displaySolving(Level lvl) {
		lvl.init_neighbors();
		QuasiExhaustiveSolver solver = new QuasiExhaustiveSolver(lvl, new DiagonalShift());
		solverApplied = true;
		solverWaiting = true;
		Thread displayLevel = new Thread(new Runnable() {
			@Override
			public void run() {
				display(lvl);
				solverApplied = false;
				// Loop until the solver thread start to wait and then notify it
				synchronized (startUpMonitor) {
					startUpMonitor.notifyAll();
				}
				// Notify that rotation is over so that the solver does not get stuck and wait forever
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
						startUpMonitor.wait();
						solverWaiting = false;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				boolean solved = solver.solving();
				if (solved && windowsOn) {
					view.solvedSituation();
				}
				System.out.println("Solved : " + solved);
			}
		});
		displayLevel.start();
		solveLevel.start();

		// So that the window is displayed when jar is executed in command line
		try {
			displayLevel.join();
			solveLevel.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		view = new LevelDrawing(level, scene, solverApplied);

		root.getChildren().add(view.getGridPane());
		stage.setTitle("Phine Loops Game");
		stage.setScene(scene);
//		stage.setMaximized(true); // Full screen
		stage.setOnCloseRequest(e -> {
			// Makes the solver continue to work without the stage
			windowsOn = false;
			synchronized (PieceDrawing.rotationMonitor) {
				PieceDrawing.rotationMonitor.notify();
			}
		});
		stage.show();

		// Loop until the solver thread start to wait and then notify
		System.out.println(solverWaiting);
		while (solverWaiting) {
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

