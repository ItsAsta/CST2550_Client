package org.abdz.scenes;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.abdz.utils.SceneUtils;

public class UpdateBookingScene extends BaseScene {
    public UpdateBookingScene(String title) {
        super(title);
    }

    private SceneUtils sceneUtils = new SceneUtils();

    @Override
    public void fillScene(BorderPane pane) {
        VBox centerVBox = sceneUtils.getCenterVBox(pane, 20, 0, 0);


    }
}
