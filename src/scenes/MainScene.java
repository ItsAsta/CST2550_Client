package scenes;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainScene extends BaseScene {
    public MainScene(String title, int w, int h) {
        super(title, w, h);
    }

    @Override
    public void fillScene(Pane pane, String title) {
        Button button = new Button("yoooo");
        pane.getChildren().add(button);

    }
}
