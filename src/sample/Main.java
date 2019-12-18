package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import scenes.BaseScene;
import scenes.PrimaryScene;

import java.io.FileNotFoundException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        BaseScene mainScene = new PrimaryScene("PT Booking");

        mainScene.setScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
