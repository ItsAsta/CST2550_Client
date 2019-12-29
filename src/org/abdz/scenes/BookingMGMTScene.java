package org.abdz.scenes;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.abdz.utils.SceneUtils;

public class BookingMGMTScene extends BaseScene {
    public BookingMGMTScene(String title) {
        super(title);
    }


    SceneUtils sceneUtils = new SceneUtils();

    @Override
    public void fillScene(BorderPane pane) {
        VBox centerVBox = sceneUtils.getCenterVBox(pane, 20, 0, 0);


    }
}
