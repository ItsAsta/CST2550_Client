package org.abdz.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.abdz.utils.SceneUtils;

public class PrimaryScene extends BaseScene {
    public PrimaryScene(String title) {
        super(title);
    }

    private SceneUtils sceneUtils = new SceneUtils();

    @Override
    public void fillScene(BorderPane pane) {
        VBox centerVBox = sceneUtils.getCenterVBox(pane, 20, 400, 400);
        pane.setCenter(centerVBox);

        Button bookingsBtn = new Button("Bookings");
        bookingsBtn.getStyleClass().add("main-buttons");
        bookingsBtn.setPrefWidth(200);

        Button bookingSearchBtn = new Button("Booking Search");
        bookingSearchBtn.getStyleClass().add("main-buttons");
        bookingSearchBtn.setPrefWidth(200);

        bookingsBtn.setOnAction(e -> {
            BaseScene bookingScene = new BookingScene("Bookings");

            bookingScene.setScene();
            hide();

        });

        bookingSearchBtn.setOnAction(e -> {
            BaseScene bookingMGMTScene = new BookingSearchScene("Booking Search");

            bookingMGMTScene.setScene();
            hide();

        });

        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(bookingsBtn, bookingSearchBtn);
    }
}
