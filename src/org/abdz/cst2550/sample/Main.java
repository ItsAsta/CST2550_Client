package org.abdz.cst2550.sample;

import javafx.application.Application;
import javafx.stage.Stage;
import org.abdz.scenes.BaseScene;
import org.abdz.scenes.PrimaryScene;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Main extends Application {

    /*TODO:
    * Work on booking management scene. Finish by 27/28th of December
    * Cleanup code inside Server.java (Put methods inside a different class)
    * */

    @Override
    public void start(Stage primaryStage) {
        BaseScene primaryScene = new PrimaryScene("Gym Bookings");

        primaryScene.setScene();
    }


    public static ObjectInputStream inputStream;
    public static ObjectOutputStream outputStream;
    public static Socket socket;
    public static void main(String[] args) throws IOException {
        socket = new Socket("localhost", 4999);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        launch(args);
    }
}
