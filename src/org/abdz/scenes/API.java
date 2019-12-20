package org.abdz.scenes;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class API {

    public VBox getCenterVBox(BorderPane pane, int spacing, int leftWidth, int rightWidth) {
        VBox centerVBox = new VBox(spacing);
        StackPane leftPane = new StackPane();
        StackPane rightPane = new StackPane();

        leftPane.setPrefWidth(leftWidth);
        rightPane.setPrefWidth(rightWidth);

        pane.setLeft(leftPane);
        pane.setRight(rightPane);
        pane.setCenter(centerVBox);

        return centerVBox;
    }
}
