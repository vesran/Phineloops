package view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PhineLoopsMainGUI extends Application {

    static final int WIDTH = 500;
    static final int HEIGHT = 500;

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);



        stage.setTitle("Phine Loops Game");
        stage.setScene(scene);
        stage.show();
    }
}
