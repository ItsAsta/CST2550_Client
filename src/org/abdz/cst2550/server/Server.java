package org.abdz.cst2550.server;

import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4999);
        Socket socket = serverSocket.accept();
        PrintWriter pr = new PrintWriter(socket.getOutputStream());

        System.out.println("Client connected");

        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader br = new BufferedReader(in);

        String str = br.readLine();

        System.out.println("Client: " + str);

        if (str.contains("idiot")) {
            pr.println("HEEEEY! Don't say that!");
            pr.flush();
        }

    }
}
