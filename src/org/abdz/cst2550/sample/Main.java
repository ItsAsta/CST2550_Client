package org.abdz.cst2550.sample;

import javafx.application.Application;
import javafx.stage.Stage;
import org.abdz.scenes.BaseScene;
import org.abdz.scenes.PrimaryScene;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        BaseScene primaryScene = new PrimaryScene("Gym Bookings");
        primaryScene.setScene();
    }

    private static String host;
    private static int port;

    //    Create an Object input and output stream
    public static ObjectInputStream inputStream;
    public static ObjectOutputStream outputStream;
    //    Create a Socket object
    public static Socket socket;
    //    Scanner to read terminal
    private static Scanner scanner;

    public static void main(String[] args) {
//        Check if the number of is equal to 2
        if (args.length == 2) {
//            Take first index of array and store it for the host
            host = args[0];
//            Take second index of array and store it for the port
            port = Integer.parseInt(args[1]);
        } else {
            System.err.println("Wrong argument format, e.g.: [Hostname] [Port]");
            System.exit(0);
        }
//        Initialise socket
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Initialise input and out streams
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        launch(args);
    }
}
