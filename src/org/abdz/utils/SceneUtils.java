package org.abdz.utils;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.abdz.scenes.BaseScene;
import org.abdz.scenes.PrimaryScene;

public class SceneUtils {

    /**
     * Used to centralise components within a scene
     *
     * @param pane       that holds the components
     * @param spacing    space between each component
     * @param leftWidth  left padding
     * @param rightWidth right padding
     * @return AVBox
     */
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

    /**
     * Default button for the back navigation
     *
     * @param scene which scene this will be used on, can pass this if you want to use current class
     * @return returns a Button object
     */
    public Button setBackButton(BaseScene scene) {
        Button backBtn = new Button("< Back");
        backBtn.setPrefWidth(100);

        backBtn.setOnAction(e -> {
            BaseScene primaryScene = new PrimaryScene("Gym Booking");

            primaryScene.setScene();
            scene.hide();
        });

        return backBtn;
    }
}
