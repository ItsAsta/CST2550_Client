package scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public abstract class BaseScene extends Stage {

    private String title;

    public BaseScene(String title) {
        this.title = title;
    }

    public void setScene() {
        BorderPane pane = new BorderPane();

        Label heading = new Label(title);
        heading.setId("heading");

        BorderPane.setAlignment(heading, Pos.CENTER);
        BorderPane.setMargin(heading, new Insets(10));
        pane.setTop(heading);
        pane.setPadding(new Insets(10));

        setTitle(title);
        Scene scene = new Scene(pane, 960, 540);
        scene.getStylesheets().add("./resources/stylesheets/stylesheet.css");
        fillScene(pane);
        setScene(scene);
        pane.requestFocus();
        show();
    }

    public abstract void fillScene(BorderPane pane);

}
