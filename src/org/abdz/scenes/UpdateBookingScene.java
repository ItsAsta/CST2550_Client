package org.abdz.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.abdz.utils.DateTimePicker;
import org.abdz.utils.SceneUtils;

public class UpdateBookingScene extends BaseScene {
    public UpdateBookingScene(String title) {
        super(title);
    }

    private SceneUtils sceneUtils = new SceneUtils();

    @Override
    public void fillScene(BorderPane pane) {
        VBox centerVBox = sceneUtils.getCenterVBox(pane, 20, 0, 0);
        HBox navigationHbox = new HBox(20);
        HBox topHBox = new HBox(10);
        HBox contentContainer = new HBox(20);
        VBox leftContent = new VBox(20);
        VBox rightContent = new VBox(20);

        TextField bookingIdField = new TextField();
        bookingIdField.setPromptText("Booking ID");

        Button bookingFetchBtn = new Button("Fetch");

        topHBox.getChildren().addAll(bookingIdField, bookingFetchBtn);

        Label clientIdLabel = new Label("Client ID: 1337");
        Label clientFirstNameLabel = new Label("First Name");
        TextField clientFirstName = new TextField();
        Label clientLastNameLabel = new Label("Last Name");
        TextField clientLastName = new TextField();
        Label clientDobLabel = new Label("Date of Birth");
        TextField clientDob = new TextField();

        leftContent.getChildren().addAll(clientIdLabel, clientFirstNameLabel, clientFirstName, clientLastNameLabel, clientLastName, clientDobLabel, clientDob);
        leftContent.setAlignment(Pos.CENTER);

        Label trainerName = new Label("Test Josh");
        Label trainerIdLabel = new Label("Trainer ID");
        TextField trainerIdField = new TextField();
        Label dateTimeLabel = new Label("Date & Time");
        DateTimePicker dateTimeField = new DateTimePicker();
        Label durationLabel = new Label("Duration (HRS)");
        TextField durationField = new TextField();

        rightContent.getChildren().addAll(trainerName, trainerIdLabel, trainerIdField, dateTimeLabel, dateTimeField, durationLabel, durationField);
        rightContent.setAlignment(Pos.CENTER);

        Button backBtn = new Button("< Back");
        backBtn.setPrefWidth(100);

        navigationHbox.getChildren().addAll(backBtn);

        backBtn.setOnAction(e -> {
            BaseScene primaryScene = new PrimaryScene("Gym Bookings");
            primaryScene.setScene();
            hide();
        });

        contentContainer.getChildren().addAll(leftContent, rightContent);
        contentContainer.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(navigationHbox, topHBox, contentContainer);
        centerVBox.setAlignment(Pos.CENTER);
    }
}
