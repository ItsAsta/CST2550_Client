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
import java.util.ArrayList;
import java.util.List;

public class AddBookingScene extends BaseScene {
    public AddBookingScene(String title) {
        super(title);
    }

    private SceneUtils sceneUtils = new SceneUtils();
    private List<TextField> verificationFields = new ArrayList<>();

    /**
     * @param pane used as a drawing board for our components
     */
    @Override
    public void fillScene(BorderPane pane) {
        VBox centerVbox = sceneUtils.getCenterVBox(pane, 20, 500, 500);
        HBox navigationHbox = new HBox(20);

        /* Adding Bookings Components*/
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

        Label statusLabel = new Label();

        addBookingBtn.setOnAction(e -> {
            try {
//                Clear our list
                verificationFields.clear();
//                Add all our fields to our list for verification later on
                verificationFields.add(trainerIdField);
                verificationFields.add(clientIdField);
                verificationFields.add(durationField);

//                For each for our list to iterate through
                for (TextField verificationFields : verificationFields) {
//                    Check if the verification field has the IDs, which we then check if it contains characters other than whole numbers.
                    if (verificationFields.getPromptText().contains("ID") && !verificationFields.getText().matches("[0-9]+")) {
//                        Set the text for the label
                        statusLabel.setText("IDs can only be integers!");
//                        Set red colour to our font
                        statusLabel.setStyle("-fx-mid-text-color: red;");
                        return;
                    }

                    if (verificationFields.getText().isEmpty()) {
//                        Set the text for the label
                        statusLabel.setText("One or more fields are empty!");
//                        Set red colour to our font
                        statusLabel.setStyle("-fx-mid-text-color: red;");
                        return;
                    }
                }

//                Write to our stream to pass to the server
                Main.outputStream.writeUTF("add=" + trainerIdField.getText() + "=" + clientIdField.getText() + "=" + dateTimeField.getDateValue() + " " + dateTimeField.getTimeValue() + "=" + durationField.getText());
//                Flush the stream so the message is sent across to the server
                Main.outputStream.flush();

                statusLabel.setText("Successfully Added Booking");
                statusLabel.setStyle("-fx-mid-text-color: green;");
            } catch (IOException ex) {
                System.err.println("Can't write to server.");
                ex.printStackTrace();
            }
        });

//        Add the components to our containers
        navigationHbox.getChildren().add(sceneUtils.setBackButton(this));
        centerVbox.setAlignment(Pos.CENTER);
        centerVbox.getChildren().addAll(navigationHbox, trainerIdField, clientIdField, dateTimeField, durationField, addBookingBtn, statusLabel);
    }
}
