package helper;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class WindowMoving {

    private double xPos;
    private double yPos;

    public void moving(Stage stage, AnchorPane pane)
    {
        pane.setOnMousePressed(event -> {
            xPos = event.getSceneX();
            yPos = event.getSceneY();
        });

        pane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xPos);
            stage.setY(event.getScreenY() - yPos);
        });
    }

    public void moving(Stage stage, BorderPane pane)
    {
        pane.setOnMousePressed(event -> {
            xPos = event.getSceneX();
            yPos = event.getSceneY();
        });

        pane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xPos);
            stage.setY(event.getScreenY() - yPos);
        });
    }
}
