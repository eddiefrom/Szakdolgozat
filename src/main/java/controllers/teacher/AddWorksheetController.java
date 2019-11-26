package controllers.teacher;

import helper.WindowMoving;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Exercise;
import models.Teacher;
import models.Worksheet;
import services.WorksheetServiceImpl;
import services.interfaces.WorksheetService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddWorksheetController implements Initializable {

    private static Stage stage;
    private AnchorPane pane;

    private WindowMoving windowMoving;
    private TeacherMainController teacherMainController;
    private  WorksheetService worksheetService;
    private static Worksheet worksheet;
    private Teacher loggedTeacher;
    private List<Exercise> exerciseList;

    @FXML private TextField textField;
    @FXML private Label errorLabel;
    @FXML private TextField classNumberField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        textField.textProperty().setValue("");
    }

    public AddWorksheetController() {

        teacherMainController = new TeacherMainController();
        loggedTeacher = teacherMainController.getLoggedTeacher();
        worksheetService = new WorksheetServiceImpl();
        exerciseList = new ArrayList<>();
        windowMoving = new WindowMoving();
    }

    public void createScene() throws IOException {

        this.stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/teacher/AddWorksheetVIEW.fxml"));
        pane = loader.load();

        windowMoving.moving(stage,pane);

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("css/main_back.css");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void addExerciseButtonEvent(ActionEvent actionEvent) throws IOException {

        if(checkWorksheetName().equals("")) {
            worksheet = new Worksheet(textField.getText(),Integer.parseInt(classNumberField.getText()), helper.DifficultyLevel.KÖNNYŰ,
                    0.0, loggedTeacher.getName(),"", "", exerciseList);

            worksheetService.save(worksheet);
            teacherMainController.createScene(stage);

        }
        else
        {
            errorLabel.textProperty().setValue(checkWorksheetName());
        }
    }


    @FXML
    public void exitButtonEvent(ActionEvent actionEvent) throws IOException {
        teacherMainController.createScene(stage);
    }

    private String checkWorksheetName()
    {
        String error = "";

        try {
            if(worksheetService.findAllWorksheetByCreator(loggedTeacher.getName()).size()> 0)
                for (Worksheet w : worksheetService.findAllWorksheetByCreator(loggedTeacher.getName())) {
                    if (w.getName().equals(textField.getText()))
                        error += "Ilyen nevű feladatsor már van!\n";
                }
        }catch (NullPointerException e) {

        }
        try {
            Number number = Integer.parseInt(classNumberField.getText());
            if (Integer.parseInt(classNumberField.getText()) < 0)
                error += "Az évfolyam nem lehet negatív!\n";
            if(Integer.parseInt(classNumberField.getText()) == 0)
                error += "Az évfolyam nem lehet 0\n";
        } catch (NumberFormatException e) {
            error += "Nem jó szám érték!\n";
        }

        return error;
    }

    public Worksheet getWorksheet() {
        return worksheet;
    }
}
