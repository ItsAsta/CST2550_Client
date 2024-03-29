package org.abdz.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.abdz.cst2550.sample.Main;
import org.abdz.utils.SceneUtils;

import java.io.IOException;

public class PrimaryScene extends BaseScene {
    public PrimaryScene(String title) {
        super(title);
    }

    private SceneUtils sceneUtils = new SceneUtils();

    @Override
    public void fillScene(BorderPane pane) {
        VBox centerVBox = sceneUtils.getCenterVBox(pane, 20, 400, 400);
        pane.setCenter(centerVBox);

//        Button components for our main scene
        Button registerClientBtn = new Button("Register Client");
        registerClientBtn.getStyleClass().addAll("main-buttons");
        registerClientBtn.setPrefWidth(200);

        Button updateClientBtn = new Button("Update Client");
        updateClientBtn.getStyleClass().addAll("main-buttons");
        updateClientBtn.setPrefWidth(200);

        Button bookingsBtn = new Button("Bookings");
        bookingsBtn.getStyleClass().add("main-buttons");
        bookingsBtn.setPrefWidth(200);

        Button addBookingBtn = new Button("Add Booking");
        addBookingBtn.getStyleClass().add("main-buttons");
        addBookingBtn.setPrefWidth(200);

        Button updateBookingBtn = new Button("Update Booking");
        updateBookingBtn.getStyleClass().add("main-buttons");
        updateBookingBtn.setPrefWidth(200);

        Button searchBookingBtn = new Button("Search Booking");
        searchBookingBtn.getStyleClass().add("main-buttons");
        searchBookingBtn.setPrefWidth(200);

        Button quitBtn = new Button("Quit");
        quitBtn.getStyleClass().add("main-buttons");
        quitBtn.setPrefWidth(200);

        /*
         * Action triggers for each button where it'll change to a new scene
         */
        registerClientBtn.setOnAction(e -> {
            BaseScene clientRegisterScene = new RegisterClientScene("Gym Booking - Register Client");

            clientRegisterScene.setScene();
            hide();
        });

        updateClientBtn.setOnAction(e -> {
            BaseScene updateClientScene = new UpdateClientScene("Gym Booking - Update Client");

            updateClientScene.setScene();
            hide();
        });

        bookingsBtn.setOnAction(e -> {
            BaseScene bookingScene = new BookingScene("Gym Booking - Bookings");

            bookingScene.setScene();
            hide();

        });

        addBookingBtn.setOnAction(e -> {
            BaseScene addBookingScene = new AddBookingScene("Gym Booking - Add Booking");

            addBookingScene.setScene();
            hide();
        });

        updateBookingBtn.setOnAction(e -> {
            BaseScene updateBookingScene = new UpdateBookingScene("Gym Booking - Update Booking");

            updateBookingScene.setScene();
            hide();
        });

        searchBookingBtn.setOnAction(e -> {
            BaseScene bookingSearchScene = new BookingSearchScene("Gym Booking - Booking Search");

            bookingSearchScene.setScene();
            hide();

        });

        quitBtn.setOnAction(e -> {
            try {
//                Close our socket
                Main.socket.close();
//                Exit the application
                System.exit(0);
            } catch (IOException ex) {
                System.err.println("Can't close socket connection.");
                ex.printStackTrace();
            }
        });

        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(registerClientBtn, updateClientBtn, bookingsBtn, searchBookingBtn, addBookingBtn, updateBookingBtn, quitBtn);
    }
}
