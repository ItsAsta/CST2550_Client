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

public class RegisterClientScene extends BaseScene {
    public RegisterClientScene(String title) {
        super(title);
    }

    private SceneUtils sceneUtils = new SceneUtils();
    private List<TextField> verificationFields = new ArrayList<>();

    @Override
    public void fillScene(BorderPane pane) {
        VBox centerVBox = sceneUtils.getCenterVBox(pane, 20, 0, 0);
        HBox navigationHbox = new HBox(20);
        HBox contentContainer = new HBox(20);
        VBox leftContent = new VBox(20);
        VBox rightContent = new VBox(20);

        Button backBtn = new Button("< Back");
        backBtn.setPrefWidth(100);

//        LEFT CONTENT COMPONENTS
        TextField firstName = new TextField();
        firstName.setPromptText("First Name");

        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");

        DateTimePicker dob = new DateTimePicker();
        dob.setFormat("yyyy-MM-dd");
        dob.setPromptText("Date of Birth");

        leftContent.setAlignment(Pos.TOP_CENTER);
        leftContent.getChildren().addAll(firstName, lastName, dob);

//        RIGHT CONTENT COMPONENTS
        TextField weight = new TextField();
        weight.setPromptText("Weight");

        TextField height = new TextField();
        height.setPromptText("Height");

        TextField mobileNo = new TextField();
        mobileNo.setPromptText("Mobile No");

        TextField focus = new TextField();
        focus.setPromptText("Focus");

        rightContent.setAlignment(Pos.CENTER);
        rightContent.getChildren().addAll(weight, height, mobileNo, focus);

        Label statusLabel = new Label();

        Button registerClientBtn = new Button("Register Client");
        registerClientBtn.setPrefWidth(200);

//        Action trigger for our register button
        registerClientBtn.setOnAction(e -> {
//            Clear verification list
            verificationFields.clear();
//            Add new components to our list
            verificationFields.add(firstName);
            verificationFields.add(lastName);
            verificationFields.add(weight);
            verificationFields.add(height);
            verificationFields.add(mobileNo);
            verificationFields.add(focus);
//                For each for our list to iterate through
            for (TextField verificationField : verificationFields) {
//                    Check if the verification field has eight in it, which we then check if it meets certain criteria.
                if (verificationField.getPromptText().contains("eight")) {
//                    If the textfield has more than 3 characters
                    if (verificationField.getText().length() > 3) {
                        statusLabel.setText("Height or Weight can't be more than 3 characters!");
                        statusLabel.setStyle("-fx-mid-text-color: red;");
                        return;
                    }
//                    Check if the textfield has any characters other than whole numbers
                    if (!verificationField.getText().matches("[0-9]+")) {
                        statusLabel.setText("Height or Weight can only be integers!");
                        statusLabel.setStyle("-fx-mid-text-color: red;");
                        return;
                    }
                }
//                Check if any of the fields are empty
                if (verificationField.getText().isEmpty()) {
                    statusLabel.setText("One or more fields are empty!");
                    statusLabel.setStyle("-fx-mid-text-color: red;");
                    return;
                }
            }

            try {
//                    Write to our socket which is connected to our server
                Main.outputStream.writeUTF("register=" + firstName.getText() + "=" + lastName.getText() + "=" +
                        dob.getDateValue().toString() + "=" + Integer.parseInt(weight.getText()) + "=" +
                        Integer.parseInt(height.getText()) + "=" + mobileNo.getText() + "=" + focus.getText());
//                Flush the socket so the message is sent immediately
                Main.outputStream.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            statusLabel.setText("Successfully Registered Client!");
            statusLabel.setStyle("-fx-mid-text-color: green;");
        });


//        Action trigger for our back button where it'll go back to the previous scene
        backBtn.setOnAction(e -> {
            BaseScene primaryScene = new PrimaryScene("Gym Bookings");
            primaryScene.setScene();
            hide();
        });

        navigationHbox.getChildren().add(backBtn);

        contentContainer.setAlignment(Pos.TOP_CENTER);
        contentContainer.getChildren().addAll(leftContent, rightContent);

        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(navigationHbox, contentContainer, registerClientBtn, statusLabel);
    }
}
