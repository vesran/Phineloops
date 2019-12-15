package view;

import Solver.Csp;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;

public class PhineLoopsMainGUI extends Application {

    static final int WIDTH = 700;
    static final int HEIGHT = 700;

    static final int GRID_WIDTH = 700;
    static final int GRID_HEIGHT = 700;

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
        grid.setPadding(new Insets(10));
        grid.setGridLinesVisible(true); // For debugging : to remove
        grid.setAlignment(Pos.CENTER);
        grid.prefWidthProperty().bind(scene.widthProperty());
        grid.prefHeightProperty().bind(scene.heightProperty());

        System.out.println("In start : " + level);
        LevelDrawing view = new LevelDrawing(level);

        view.draw(grid, scene);
        grid.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        root.getChildren().add(grid);
        stage.setTitle("Phine Loops Game");
        stage.setScene(scene);
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

    public static void main(String [] args) {
        Level level = initLevel();
        Csp moncsp = new Csp(FileReader.getGrid("C:\\Users\\Bilal\\git\\phineloops-kby\\instances\\public\\grid_256x256_dist.0_vflip.false_hflip.false_messedup.false_id.2.dat", " ")) ; 
        level.setGrid(moncsp.solving()) ; 
       
        display(level);

        System.out.println("This instruction will not be executed if the window is not closed");
    }

}
