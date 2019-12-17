package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import scenes.BaseScene;
import scenes.MainScene;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        BaseScene mainScene = new MainScene("yooo", 960, 540);

        mainScene.setScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
