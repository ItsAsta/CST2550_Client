package org.abdz.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MemberLoginScene extends BaseScene {
    public MemberLoginScene(String title) {
        super(title);
    }

    private API api = new API();

    @Override
    public void fillScene(BorderPane pane) {
        VBox center = api.getCenterVBox(pane, 20, 400, 400);

        TextField usernameField = new TextField();
        usernameField.setPromptText("USERNAME");
        usernameField.setAlignment(Pos.CENTER);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("PASSWORD");
        passwordField.setAlignment(Pos.CENTER);

        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(100);
        loginBtn.getStyleClass().add("main-buttons");

        center.setAlignment(Pos.CENTER);
        center.getChildren().addAll(usernameField, passwordField, loginBtn);
    }
}
