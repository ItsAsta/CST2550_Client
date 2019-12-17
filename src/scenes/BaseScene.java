package scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public abstract class BaseScene extends Stage {

    private String title;
    private int w;
    private int h;

    public BaseScene(String title, int w, int h) {
        this.title = title;
        this.w = w;
        this.h = h;
    }

    public void setScene() {
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, w, h);
        fillScene(gridPane, title);
        setScene(scene);
        show();
    }

    public abstract void fillScene(Pane pane, String title);
}
