package org.abdz.cst2550.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.abdz.scenes.BaseScene;
import org.abdz.scenes.MemberLoginScene;
import org.abdz.scenes.PrimaryScene;

import java.io.*;
import java.net.*;


public class Client extends Application {


    @Override
    public void start(Stage primaryStage) {
//        BaseScene mainScene = new MemberLoginScene("TITLE");
//
//        mainScene.setScene();
    }

    public static void main(String[] args) throws IOException {
//        launch(args);
        Socket socket = new Socket("localhost", 4999);

        PrintWriter pr = new PrintWriter(socket.getOutputStream());
        pr.println("idiot");
        pr.flush();
        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader br = new BufferedReader(in);

        String str = br.readLine();
        System.out.println("hehe");

        System.out.println("Server: " + str);

        if (str.contains("Don't")) {
            pr.println("Sorry boss =/");
            pr.flush();
        }
    }
}
