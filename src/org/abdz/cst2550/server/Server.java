package org.abdz.cst2550.server;
/*
 * Developed By Muhammed-Abdi on 24/12/2019 18:53.
 * Last Modified 24/12/2019 22:33.
 * Credits: Yasper (AI Developer of NeuralLib), Burak (Owner of BurakLite Robot), Dogerina (Founder of all RS Bots)
 * RSPeer.org
 * Copyright (c) 2019. All rights reserved.
 */

import org.abdz.utils.ServerUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Server {
    private static Connection connection;
    public static ObjectOutputStream outputStream;
    public static BufferedReader inputStream;

    public static void main(String[] args) throws IOException {


        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/gym", "root", "");
        } catch (SQLException e) {
            System.out.println("Can't connect to DB: " + e);
        }

        ServerSocket serverSocket = new ServerSocket(4999);
        while (true) {

            Socket socket = serverSocket.accept();


            Thread socketListener = new Thread(() -> {
                try {
                    inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    while (socket.isConnected()) {
                        String str = inputStream.readLine();
                        System.out.println(str);
                        if (str.contains("listall")) {
                            ServerUtils.passData(outputStream, listAllBookings());
                        }

                        if (str.contains("add")) {
                            addBooking(str);
                            System.out.println("Added booking!");
                        }

                        if (str.contains("remove")) {
                            removeBooking(str);
                            System.out.println("Removed booking!");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            socketListener.start();
            System.out.println("Client Connected!");


//            oos.writeObject(listAllBookings());
        }

//        updateBooking(1, 2, 1, getDateTime(), "5");

//        addBooking(1, 3, "2019-06-12 06:00:00", "1");


//        listDateBookings("<", "'" + dateTime.getYear() + "-" + dateTime.getMonthValue() + "-" + dateTime.getDayOfMonth() + "'");


    }

    private static String getDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dtf.format(LocalDateTime.now());
    }

    public static Map<Integer, ArrayList<String>> listAllBookings() {
        Map<Integer, ArrayList<String>> bookings = new HashMap<>();
        bookings.clear();
        ArrayList<String> tempList = new ArrayList<>();
        tempList.clear();

        try {
            ResultSet resultSet = connection.createStatement()
                    .executeQuery("SELECT DISTINCT bookings.booking_id, bookings.trainer_fk, bookings.client_fk, " +
                            "DATE_FORMAT(bookings.date_time, '%Y-%m-%d %H:%i'), bookings.duration, clients.first_name, " +
                            "clients.last_name, clients.dob, " +
                            "trainers.first_name, trainers.last_name " +
                            "FROM bookings JOIN clients ON clients.client_id = bookings.client_fk " +
                            "JOIN trainers ON bookings.trainer_fk = trainers.trainer_id");

            while (resultSet.next()) {
                for (int i = 2; i <= 10; i++) {
//                    System.out.println(resultSet.getString(i));
                    tempList.add(resultSet.getString(i));
                }
                bookings.put(Integer.parseInt(resultSet.getString(1)), new ArrayList<>(tempList));
                tempList.clear();
            }

            return bookings;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void listPTBookings(int trainerId) {
        try {
            ResultSet result = connection.createStatement().executeQuery("SELECT bookings.booking_id, trainers.first_name, bookings.date, bookings.duration, clients.* " +
                    "FROM bookings " +
                    "JOIN clients ON bookings.client_fk = clients.client_id " +
                    "JOIN trainers ON bookings.trainer_fk = trainers.trainer_id" +
                    " WHERE trainers.trainer_id = " + trainerId);
            while (result.next()) {
                System.out.println("Booking ID: " + result.getString(1) +
                        "\n Trainer First Name: " + result.getString(2) +
                        "\n Booked Date: " + result.getString(3) +
                        "\n Booked Duration: " + result.getString(4) + " HRs" +
                        "\n Client First Name: " + result.getString(5) +
                        "\n Client Last Name: " + result.getString(6) +
                        "\n Client D.O.B: " + result.getString(7) +
                        "\n Client Weight: " + result.getString(8) +
                        "\n Client Height: " + result.getString(9) + " CM" +
                        "\n Client Mobile No: " + result.getString(10) +
                        "\n Client Goal: " + result.getString(11));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listClientBookings(int clientId) {
        try {
            ResultSet result = connection.createStatement().executeQuery("SELECT bookings.booking_id, trainers.first_name, bookings.date, bookings.duration, clients.* " +
                    "FROM bookings " +
                    "JOIN clients ON bookings.client_fk = clients.client_id " +
                    "JOIN trainers ON bookings.trainer_fk = trainers.trainer_id " +
                    "WHERE clients.client_id = " + clientId);
            while (result.next()) {
                System.out.println("Booking ID: " + result.getString(1) +
                        "\nTrainer First Name: " + result.getString(2) +
                        "\nBooked Date: " + result.getString(3) +
                        "\nBooked Duration: " + result.getString(4) + " HRs" +
                        "\nClient First Name: " + result.getString(5) +
                        "\nClient Last Name: " + result.getString(6) +
                        "\nClient D.O.B: " + result.getString(7) +
                        "\nClient Weight: " + result.getString(8) +
                        "\nClient Height: " + result.getString(9) + " CM" +
                        "\nClient Mobile No: " + result.getString(10) +
                        "\nClient Goal: " + result.getString(11));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listDateBookings(String operator, String date) {
        try {
            ResultSet result = connection.createStatement().executeQuery("SELECT bookings.booking_id, trainers.first_name, bookings.date, bookings.duration, clients.* " +
                    "FROM bookings " +
                    "JOIN clients ON bookings.client_fk = clients.client_id " +
                    "JOIN trainers ON bookings.trainer_fk = trainers.trainer_id" +
                    " WHERE bookings.date " + operator + " " + date);
            while (result.next()) {
                System.out.println("Booking ID: " + result.getString(1) +
                        "\nTrainer First Name: " + result.getString(2) +
                        "\nBooked Date: " + result.getString(3) +
                        "\nBooked Duration: " + result.getString(4) + " HRs" +
                        "\nClient First Name: " + result.getString(5) +
                        "\nClient Last Name: " + result.getString(6) +
                        "\nClient D.O.B: " + result.getString(7) +
                        "\nClient Weight: " + result.getString(8) +
                        "\nClient Height: " + result.getString(9) + " CM" +
                        "\nClient Mobile No: " + result.getString(10) +
                        "\nClient Goal: " + result.getString(11));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void removeBooking(String bookingId) {
        String[] query = bookingId.split("=");

        try {
            if (connection != null) {
                connection.createStatement().executeUpdate("DELETE FROM bookings WHERE bookings.booking_id = " + query[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addBooking(String booking) {
        String[] query = booking.split("=");
        try {
            connection.createStatement().executeUpdate("INSERT INTO bookings (trainer_fk, client_fk, date_time, duration) VALUES ("
                    + query[1] + ", " + query[2] + ", '" + query[3] + "', " + query[4] + ")");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateBooking(int bookingId, int trainer_id, int client_id, String dateTime, String duration) {
        try {
            connection.createStatement().executeUpdate("UPDATE bookings SET " + "trainer_fk = " + trainer_id + ", client_fk = " +
                    client_id + ", date_time = '" + dateTime + "', duration = " + duration +
                    " WHERE " + "booking_id = " + bookingId);

            System.out.println("Successfully updated booking with ID: " + bookingId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
