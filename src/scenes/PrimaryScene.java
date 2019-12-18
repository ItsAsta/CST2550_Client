package scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PrimaryScene extends BaseScene {
    public PrimaryScene(String title) {
        super(title);
    }

    @Override
    public void fillScene(BorderPane pane) {
        VBox centerVBox = new VBox(20);
        StackPane leftPane = new StackPane();
        StackPane rightPane = new StackPane();

        leftPane.setPrefWidth(400);
        rightPane.setPrefWidth(400);

        pane.setLeft(leftPane);
        pane.setRight(rightPane);
        pane.setCenter(centerVBox);

        Button memberBtn = new Button("Member");
        memberBtn.getStyleClass().add("mainButtons");
        Button staffBtn = new Button("Staff");
        staffBtn.getStyleClass().add("mainButtons");
        Button registerBtn = new Button("Register");
        registerBtn.getStyleClass().add("mainButtons");

        memberBtn.setOnAction(e -> {
            BaseScene memberLoginScene = new MemberLoginScene("Member Login");

            memberLoginScene.setScene();
        });

        memberBtn.setPrefWidth(200);
        staffBtn.setPrefWidth(200);
        registerBtn.setPrefWidth(200);

        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(memberBtn, staffBtn, registerBtn);
    }
}
