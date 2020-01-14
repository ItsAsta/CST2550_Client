package org.abdz.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.abdz.cst2550.sample.Main;
import org.abdz.utils.DateTimePicker;
import org.abdz.utils.SceneUtils;

import java.io.IOException;

public class AddBookingScene extends BaseScene {
    public AddBookingScene(String title) {
        super(title);
    }

    private SceneUtils sceneUtils = new SceneUtils();

    @Override
    public void fillScene(BorderPane pane) {
        VBox centerVbox = sceneUtils.getCenterVBox(pane, 20, 500, 500);
        HBox navigationHbox = new HBox(20);

        Button backBtn = new Button("< Back");
        backBtn.setPrefWidth(100);

        backBtn.setOnAction(e -> {
            BaseScene primaryScene = new PrimaryScene("Gym Booking");

            primaryScene.setScene();
            hide();
        });

        /* Adding Bookings */
        TextField trainerIdField = new TextField();
        trainerIdField.setPromptText("Trainer ID");

        TextField clientIdField = new TextField();
        clientIdField.setPrefWidth(200);
        clientIdField.setPromptText("Client ID");

        DateTimePicker dateTimeField = new DateTimePicker();
        dateTimeField.setPrefSize(400, 0);
        dateTimeField.setPromptText("Date & Time");

        TextField durationField = new TextField();
        durationField.setPromptText("Duration");

        Button addBookingBtn = new Button("Add Booking");
        addBookingBtn.setPrefWidth(150);

        addBookingBtn.setOnAction(e -> {
                try {
                    Main.outputStream.writeUTF("add=" + trainerIdField.getText() + "=" + clientIdField.getText() + "=" + dateTimeField.getDateValue() + " " + dateTimeField.getTimeValue() + "=" + durationField.getText());
                    Main.outputStream.flush();
                    System.out.println("Successfully added booking");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        });

        navigationHbox.getChildren().add(backBtn);
        centerVbox.setAlignment(Pos.CENTER);
        centerVbox.getChildren().addAll(navigationHbox, trainerIdField, clientIdField, dateTimeField, durationField, addBookingBtn);
    }
}
