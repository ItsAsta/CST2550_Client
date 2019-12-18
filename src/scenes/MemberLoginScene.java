package scenes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class MemberLoginScene extends BaseScene {
    public MemberLoginScene(String title) {
        super(title);
    }

    @Override
    public void fillScene(BorderPane pane) {
        Button button = new Button(getTitle());

        pane.setCenter(button);

        button.setOnAction(e -> {
            BaseScene primaryScene = new PrimaryScene("Primary Scene");
            primaryScene.setScene();
        });
    }
}
