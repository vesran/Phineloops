package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Level;

import java.util.Iterator;

public class PhineLoopsMainGUI extends Application {

    static final int WIDTH = 500;
    static final int HEIGHT = 500;

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        GridPane grid = new GridPane();
        grid.setVgap(0);
        grid.setHgap(0);
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);
        grid.setGridLinesVisible(true);

        Level model = new Level(10, 10);
        LevelDrawing view = new LevelDrawing(model);

        view.draw(grid);
        grid.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        root.getChildren().add(grid);
        stage.setTitle("Phine Loops Game");
        stage.setScene(scene);
        stage.show();

        stage.setTitle("Phine Loops Game");
        stage.setScene(scene);
        stage.show();
    }
}
