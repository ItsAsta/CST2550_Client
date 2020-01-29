package org.abdz.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.abdz.cst2550.sample.Main;
import org.abdz.utils.DateTimePicker;
import org.abdz.utils.SceneUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UpdateBookingScene extends BaseScene {
    public UpdateBookingScene(String title) {
        super(title);
    }

    private SceneUtils sceneUtils = new SceneUtils();
    private List<String> booking = new ArrayList<>();
    private List<TextField> verificationFields = new ArrayList<>();

    @Override
    public void fillScene(BorderPane pane) {
        VBox centerVBox = sceneUtils.getCenterVBox(pane, 20, 50, 50);
        HBox navigationHbox = new HBox(20);
        HBox topHBox = new HBox(10);
        HBox contentContainer = new HBox(200);
        VBox leftContent = new VBox(20);
        VBox rightContent = new VBox(20);

        TextField bookingIdField = new TextField();
        bookingIdField.setPromptText("Booking ID");

        Button fetchBookingBtn = new Button("Fetch");
        fetchBookingBtn.setMinWidth(100);


        // LEFT PANEL
        Label bookingIdLabel = new Label();
        Label trainerIdLabel = new Label();
        Label trainerFullNameLabel = new Label();
        Label clientIdLabel = new Label();
        Label clientFullNameLabel = new Label();
        Label clientDobLabel = new Label();

        // RIGHT PANEL
        Label trainerIdLabelText = new Label("Trainer ID");
        TextField trainerIdField = new TextField();
        trainerIdField.setAlignment(Pos.CENTER);
        Label dateTimeLabel = new Label("Date & Time");
        DateTimePicker dateTimeField = new DateTimePicker();
        Label durationLabel = new Label("Duration (HRS)");
        TextField durationField = new TextField();
        durationField.setAlignment(Pos.CENTER);

        // BOTTOM PANEL
        Button updateBookingBtn = new Button("Update Booking");
        updateBookingBtn.setMinWidth(600);

        Label statusLabel = new Label();

        fetchBookingBtn.setOnAction(e -> {
            if (bookingIdField.getText() != null) {
                try {
                    Main.outputStream.writeUTF("fetch=" + bookingIdField.getText());
                    Main.outputStream.flush();
                    getBooking();

                    bookingIdLabel.setText("Booking ID: " + booking.get(0));
                    trainerIdLabel.setText("Trainer ID: " + booking.get(1));
                    trainerIdField.setText(booking.get(1));
                    clientIdLabel.setText("Client ID: " + booking.get(2));
                    dateTimeField.setDateTimeValue(LocalDateTime.parse(booking.get(3), DateTimePicker.formatter));
                    durationField.setText(booking.get(4));
                    clientFullNameLabel.setText("Client Name: " + booking.get(5) + " " + booking.get(6));
                    clientDobLabel.setText("Client D.O.B: " + booking.get(7));
                    trainerFullNameLabel.setText("Trainer Name: " + booking.get(8) + " " + booking.get(9));

                    statusLabel.setText("");

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        updateBookingBtn.setOnAction(e -> {
            try {
                if (durationField.getText().contains(".")) {
                    statusLabel.setText("Whole integers only for duration!");
                    statusLabel.setStyle("-fx-mid-text-color: red;");
                    return;
                }

                verificationFields.clear();
                verificationFields.add(trainerIdField);
                verificationFields.add(durationField);

                for (TextField verificationField : verificationFields) {
                    if (verificationField.getText().isEmpty()) {
                        statusLabel.setText("One or more fields are empty!");
                        statusLabel.setStyle("-fx-mid-text-color: red;");
                        return;
                    }
                }

                Main.outputStream.writeUTF("update=" + bookingIdField.getText() + "=" + trainerIdField.getText() + "=" +
                        dateTimeField.getDateValue() + " " + dateTimeField.getTimeValue() + "=" + durationField.getText());
                Main.outputStream.flush();

                statusLabel.setText("Successfully Updated Booking ID: " + bookingIdField.getText());
                statusLabel.setStyle("-fx-mid-text-color: green;");

            } catch (IOException ex) {
                System.err.println("Can't write to server.");
                ex.printStackTrace();
            }
        });

        Button backBtn = new Button("< Back");
        backBtn.setPrefWidth(100);

        navigationHbox.getChildren().addAll(backBtn);

        backBtn.setOnAction(e -> {
            BaseScene primaryScene = new PrimaryScene("Gym Bookings");
            primaryScene.setScene();
            hide();
        });

        topHBox.getChildren().addAll(bookingIdField, fetchBookingBtn);

        leftContent.getChildren().addAll(trainerIdLabel, trainerFullNameLabel, clientIdLabel, clientFullNameLabel, clientDobLabel);
        leftContent.setAlignment(Pos.CENTER);

        rightContent.getChildren().addAll(trainerIdLabelText, trainerIdField, dateTimeLabel, dateTimeField, durationLabel, durationField);
        rightContent.setAlignment(Pos.CENTER);

        contentContainer.getChildren().addAll(leftContent, rightContent);
        contentContainer.setAlignment(Pos.CENTER);

        centerVBox.getChildren().addAll(navigationHbox, topHBox, contentContainer, updateBookingBtn, statusLabel);
        centerVBox.setAlignment(Pos.CENTER);
    }

    private List<String> getBooking() {
        try {
            Object dataObject = Main.inputStream.readObject();

            booking = (List<String>) dataObject;

            return booking;

        } catch (IOException e) {
            System.out.println("Can't fetch bookings from server.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Can't fetch bookings from server.");
            e.printStackTrace();
        }

        return null;
    }
}
