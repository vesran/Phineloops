package fr.dauphine.javaavance.phineloops;

import Solver.Csp;
import Solver.Extend;
import Solver.quasiexhaustive.QuasiExhaustiveSolver;
import Solver.quasiexhaustive.comparaison.Lexicographic;
import model.First_Generator;
import model.Level;
import model.io.FileCreator;
import model.io.FileReader;
import model.pieces.Piece;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import view.PhineLoopsMainGUI;

public class Main {
	private static String inputFile = null;
	private static String outputFile = null;
	private static Integer width = -1;
	private static Integer height = -1;
	private static Integer maxcc = -1;
	private static Integer maxThread = -1;

	private static void generate(int width, int height, String outputFile) throws IllegalArgumentException {
		// generate grid and store it to outputFile...
		// ...
		
		First_Generator level = new First_Generator(width, height, 2);
		level.getL().init_neighbors();
		FileCreator.write(level.getL(),outputFile);
	}

	private static boolean solve(String inputFile, String outputFile) {
		// load grid from inputFile, solve it and store result to outputFile...
		// ...
		// Import level instance
		Piece[][] inputGrid = FileReader.getGrid(inputFile, " ");
		Piece[][] outputGrid;
		boolean solved;
		// Solve the level
		Csp solver = new Csp(inputGrid, maxThread.shortValue());
		solved = solver.solving(Extend.noExtend);
		// Save the solved level
		outputGrid = solver.getMyLevelToSolve();
		FileCreator.write(outputGrid, outputFile);
		return solved;
	}

	private static boolean check(String inputFile) {
		// load grid from inputFile and check if it is solved...
		// ...
		Piece[][] grid = FileReader.getGrid(inputFile, " ");
		Level lvl = new Level(grid);
		lvl.init_neighbors();
		return lvl.checkGrid();
	}

	public static void main(String[] args) {
		generate(4, 4, "nom");
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		options.addOption("g", "generate ", true, "Generate a grid of size height x width.");
		options.addOption("c", "check", true, "Check whether the grid in <arg> is solved.");
		options.addOption("s", "solve", true, "Solve the grid stored in <arg>.");
		options.addOption("o", "output", true,
				"Store the generated or solved grid in <arg>. (Use only with --generate and --solve.)");
		options.addOption("t", "threads", true, "Maximum number of solver threads. (Use only with --solve.)");
		options.addOption("x", "nbcc", true, "Maximum number of connected components. (Use only with --generate.)");
		options.addOption("G", "gui", true, "Run with the graphic user interface.");
		options.addOption("GS", "guisolver", true,
				"Run with the graphic interface showing an exhaustive solver working.");
		options.addOption("h", "help", false, "Display this help");
		
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.err.println("Error: invalid command line format.");
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("phineloops", options);
			System.exit(1);
		}
		try {
			if (cmd.hasOption("g")) {
				System.out.println("Running phineloops generator.");
				String[] gridformat = cmd.getOptionValue("g").split("x");
				width = Integer.parseInt(gridformat[0]);
				height = Integer.parseInt(gridformat[1]);
				if (!cmd.hasOption("o"))
					throw new ParseException("Missing mandatory --output argument.");
				outputFile = cmd.getOptionValue("o");
				generate(width, height, outputFile);
			} else if (cmd.hasOption("s")) {
				System.out.println("Running phineloops solver.");
				inputFile = cmd.getOptionValue("s");
				if (!cmd.hasOption("o"))
					throw new ParseException("Missing mandatory --output argument.");
				if (cmd.hasOption("t")) {
					try {
						Integer max = Integer.valueOf(cmd.getOptionValue("t"));
						if (max.intValue() >= 1 && max.intValue() <= 4) {
							maxThread = max;
						} else {
							throw new ParseException("Must be a integer 1 to 4");
						}
					} catch (NumberFormatException e) {
						throw new ParseException("Must be a integer 1 to 4");
					}
				} else {
					maxThread = 1;
				}
				outputFile = cmd.getOptionValue("o");
				boolean solved = solve(inputFile, outputFile);
				System.out.println("SOLVED: " + solved);
			} else if (cmd.hasOption("c")) {
				System.out.println("Running phineloops checker.");
				inputFile = cmd.getOptionValue("c");
				boolean solved = check(inputFile);
				System.out.println("SOLVED: " + solved);
			} else if (cmd.hasOption("G")) {
				System.out.println("Displaying GUI.");
				inputFile = cmd.getOptionValue("G");
				PhineLoopsMainGUI.display(new Level(FileReader.getGrid(inputFile, " ")));
			} else if (cmd.hasOption("GS")) {
				System.out.println(
						"Displaying solver working. The solver continues to work even if the window has been closed");
				inputFile = cmd.getOptionValue("GS");
				PhineLoopsMainGUI.displaySolving(new Level(FileReader.getGrid(inputFile, " ")));
			} else {
				throw new ParseException(
						"You must specify at least one of the following options: -generate -check -solve ");
			}
		} catch (ParseException e) {
			System.err.println("Error parsing commandline : " + e.getMessage());
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("phineloops", options);
			System.exit(1); // exit with error
		}
		System.exit(0); // exit with success
	}
}
