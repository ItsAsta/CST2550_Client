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
                Main.socket.close();
            } catch (IOException ex) {
                System.err.println("Can't close socket connection.");
                ex.printStackTrace();
            }
        });

        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(bookingsBtn, addBookingBtn, updateBookingBtn, searchBookingBtn, quitBtn);
    }
}
