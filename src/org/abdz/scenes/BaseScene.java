package org.abdz.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public abstract class BaseScene extends Stage {

    private String title;

    /**
     * @param title the constructors takes the title for the new scene.
     */
    public BaseScene(String title) {
        this.title = title;
    }

    /*
        Set  all necessary components and layout for our scene.
     */
    public void setScene() {
        BorderPane pane = new BorderPane();

        Label heading = new Label(title);
//        Set an id for the heading to be used in our CSS
        heading.setId("heading");

        BorderPane.setAlignment(heading, Pos.CENTER);
        pane.setTop(heading);
        pane.setPadding(new Insets(10));

//        Initiate a new scene with the width and height, as well as a pane.
        Scene scene = new Scene(pane, 1300, 800);
        scene.getStylesheets().add("./org/abdz/resources/stylesheets/stylesheet.css");
//        Call the fillScene method to populate our pane
        fillScene(pane);
        setTitle(title);
//        Sets the scene to be displayed for our user
        setScene(scene);
        setResizable(true);
//        Request focus for the window when it's created.
        pane.requestFocus();
//        Finally, reveal the scene to the user.
        show();
    }

    public abstract void fillScene(BorderPane pane);

}
