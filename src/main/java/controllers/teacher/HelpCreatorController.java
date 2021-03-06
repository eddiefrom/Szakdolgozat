package controllers.teacher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpCreatorController implements Initializable {

    private static Stage stage;

    @FXML private TextArea textArea;
    private static String method;
    private ModifyCreateExerciseController modifyCreateExerciseController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(method.equals("modify"))
            textArea.textProperty().setValue(ModifyCreateExerciseController.getExercise().getHelp());
    }

    public HelpCreatorController() {

        modifyCreateExerciseController = new ModifyCreateExerciseController();
        method = modifyCreateExerciseController.getMethod();
    }

    public void createScene() throws IOException {

        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/teacher/HelpCreatorVIEW.fxml")));
        scene.getStylesheets().add("css/main_back.css");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    @FXML
    public void createButtonEvent(ActionEvent e)
    {
        ModifyCreateExerciseController.setHelp(textArea.getText());
        stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void exitAddEvent(ActionEvent e)
    {
        stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.close();
    }



}
