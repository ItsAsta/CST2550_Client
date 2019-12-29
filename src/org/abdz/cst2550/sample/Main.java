package org.abdz.cst2550.sample;

import javafx.application.Application;
import javafx.stage.Stage;
import org.abdz.scenes.BaseScene;
import org.abdz.scenes.BookingScene;
import org.abdz.scenes.PrimaryScene;

import java.io.*;
import java.net.Socket;


public class Main extends Application {

    /*TODO:
    * Work on booking management scene. Finish by 27/28th of December
    * Cleanup code inside Server.java (Put methods inside a different class)
    * */

    @Override
    public void start(Stage primaryStage) {
        BaseScene mainScene = new PrimaryScene("GYM Management");

        mainScene.setScene();
    }

    public static Socket socket;
    public static void main(String[] args) throws IOException {
        socket = new Socket("localhost", 4999);
        launch(args);
//
//        PrintWriter pr = new PrintWriter(socket.getOutputStream());
//        pr.println("idiot");
//        pr.flush();
//        InputStreamReader in = new InputStreamReader(socket.getInputStream());
//        BufferedReader br = new BufferedReader(in);
//
//        String str = br.readLine();
//        System.out.println("hehe");
//
//        System.out.println("Server: " + str);
//
//        if (str.contains("Don't")) {
//            pr.println("Sorry boss =/");
//            pr.flush();
//        }
    }
}
