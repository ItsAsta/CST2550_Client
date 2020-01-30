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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UpdateClientScene extends BaseScene {
    public UpdateClientScene(String title) {
        super(title);
    }

    private SceneUtils sceneUtils = new SceneUtils();
    private List<TextField> verificationFields = new ArrayList<>();
    private List<String> client = new ArrayList<>();

    @Override
    public void fillScene(BorderPane pane) {
        VBox centerVBox = sceneUtils.getCenterVBox(pane, 20, 0, 0);
        HBox navigationHbox = new HBox(20);
        HBox contentContainer = new HBox(20);
        VBox leftContent = new VBox(20);
        VBox rightContent = new VBox(20);

        Button backBtn = new Button("< Back");
        backBtn.setPrefWidth(100);

        TextField clientId = new TextField();
        clientId.setPromptText("Client ID");
        clientId.setPrefWidth(100);

        Button fetchClientBtn = new Button("Fetch Client");
        fetchClientBtn.setPrefWidth(100);

        navigationHbox.getChildren().addAll(backBtn, clientId, fetchClientBtn);

//        LEFT CONTENT
        TextField firstName = new TextField();
        firstName.setPromptText("First Name");

        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");

        DateTimePicker dob = new DateTimePicker();
        dob.setFormat("yyyy-MM-dd");
        dob.setPromptText("Date of Birth");

        leftContent.setAlignment(Pos.TOP_CENTER);
        leftContent.getChildren().addAll(firstName, lastName, dob);

//        RIGHT CONTENT
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

        Button updateClientBtn = new Button("Update Client");
        updateClientBtn.setPrefWidth(200);

        fetchClientBtn.setOnAction(e -> {

            try {
                Main.outputStream.writeUTF("fetchclient=" + clientId.getText());
                Main.outputStream.flush();
                getClient();

                firstName.setText(client.get(1));
                lastName.setText(client.get(2));
                System.out.println(LocalDate.parse(client.get(3)));
                dob.getEditor().setText(client.get(3));
                weight.setText(client.get(4));
                height.setText(client.get(5));
                mobileNo.setText(client.get(6));
                focus.setText(client.get(7));

                statusLabel.setText("");

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        updateClientBtn.setOnAction(e -> {
            verificationFields.clear();
            verificationFields.add(clientId);
            verificationFields.add(firstName);
            verificationFields.add(lastName);
            verificationFields.add(weight);
            verificationFields.add(height);
            verificationFields.add(mobileNo);
            verificationFields.add(focus);

            for (TextField verificationField : verificationFields) {
                if (verificationField.getPromptText().contains("eight")) {
                    if (verificationField.getText().length() > 3) {
                        statusLabel.setText("Height or Weight can't be more than 3 characters!");
                        statusLabel.setStyle("-fx-mid-text-color: red;");
                        return;
                    }

                    if (!verificationField.getText().matches("[0-9]+")) {
                        statusLabel.setText("Height or Weight can only be integers!");
                        statusLabel.setStyle("-fx-mid-text-color: red;");
                        return;
                    }
                }
                if (verificationField.getText().isEmpty()) {
                    statusLabel.setText("One or more fields are empty!");
                    statusLabel.setStyle("-fx-mid-text-color: red;");
                    return;
                }
            }

            try {
                Main.outputStream.writeUTF("updateclient=" + clientId.getText() + "=" + firstName.getText() + "=" + lastName.getText() + "=" +
                        dob.getDateValue().toString() + "=" + Integer.parseInt(weight.getText()) + "=" +
                        Integer.parseInt(height.getText()) + "=" + mobileNo.getText() + "=" + focus.getText());
                Main.outputStream.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            statusLabel.setText("Successfully Updated Client!");
            statusLabel.setStyle("-fx-mid-text-color: green;");
        });


        backBtn.setOnAction(e -> {
            BaseScene primaryScene = new PrimaryScene("Gym Bookings");
            primaryScene.setScene();
            hide();
        });

        contentContainer.setAlignment(Pos.TOP_CENTER);
        contentContainer.getChildren().addAll(leftContent, rightContent);

        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(navigationHbox, contentContainer, updateClientBtn, statusLabel);

    }

    private List<String> getClient() {
        try {
            Object dataObject = Main.inputStream.readObject();

            client = (List<String>) dataObject;

            return client;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Can't fetch bookings from server.");
            e.printStackTrace();
            return null;
        }
    }
}
