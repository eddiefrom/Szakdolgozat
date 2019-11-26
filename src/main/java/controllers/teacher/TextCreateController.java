package controllers.teacher;

import helper.WindowMoving;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TextCreateController implements Initializable {

    private Stage stage;
    private AnchorPane pane;

    private WindowMoving windowMoving;
    private ModifyCreateExerciseController modifyCreateExerciseController;
    private double fontEtalon;

    @FXML private TextField size;
    @FXML private TextArea textArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fontEtalon = 10.0;
        size.textProperty().setValue(String.valueOf(fontEtalon / 5));
        textArea.setStyle("-fx-font-size: "+size.getText()+"em;");
    }

    public TextCreateController() {

        windowMoving = new WindowMoving();
        modifyCreateExerciseController = new ModifyCreateExerciseController();
    }

    public void textAreaEvent()
    {
        textArea.setOnMouseClicked(e -> {
            textArea.setStyle("-fx-font-size: "+size.getText()+"em;");
        });
    }

    public void createScene() throws IOException {

        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        windowMoving.moving(stage,pane);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/teacher/TextCreateVIEW.fxml")));
        scene.getStylesheets().add("css/main_back.css");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    @FXML
    public void creteButtonEvent(ActionEvent actionEvent)
    {
        GraphicsContext graphicsContext = modifyCreateExerciseController.getMainCanvas().getGraphicsContext2D();
        graphicsContext.setFont(new Font(Double.parseDouble(size.getText())*(fontEtalon+5)));
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillText(textArea.getText(),modifyCreateExerciseController.getStartX(),
                modifyCreateExerciseController.getStartY());

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void exitTextButtonEvent(ActionEvent actionEvent)
    {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
