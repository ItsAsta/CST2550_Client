package org.abdz.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


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
        pane.setTop(heading);
        pane.setPadding(new Insets(10));


        Scene scene = new Scene(pane, 1300, 800);
        scene.getStylesheets().add("./org/abdz/resources/stylesheets/stylesheet.css");

        fillScene(pane);
        setTitle(title);
        setScene(scene);
        setResizable(true);
        pane.requestFocus();
        show();
    }

    public abstract void fillScene(BorderPane pane);

}
