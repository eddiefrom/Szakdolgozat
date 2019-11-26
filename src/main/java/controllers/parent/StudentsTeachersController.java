package controllers.parent;

import helper.DifficultyLevel;
import helper.Types;
import helper.WindowMoving;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.CompetitionResults;
import models.Exercise;
import models.Student;
import models.Worksheet;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentsTeachersController implements Initializable {

    private Stage stage;
    private AnchorPane pane;

    private ParentMainController resultsController;
    private WindowMoving windowMoving;
    private static Student student;

    @FXML private ListView<String> exerciseName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTable();
    }

    public StudentsTeachersController() {

        stage = new Stage();
        resultsController = new ParentMainController();
        windowMoving = new WindowMoving();
        student = resultsController.getSelectedStudent();
        System.out.println(student.getTeachers().size());
    }

    public void createScene() throws IOException {

        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/parent/StudentsTeachersVIEW.fxml"));
        pane = loader.load();

        windowMoving.moving(stage,pane);

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("css/main_back.css");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("LearningRoom");
        stage.setResizable(false);
        stage.show();
    }

    private void setTable() {

        ObservableList<String> names = FXCollections.observableArrayList();

        for(int i = 0; i < student.getTeachers().size(); i++) {

            names.add(student.getTeachers().get(i).getName());
        }
        exerciseName.setItems(names);
    }


    @FXML
    public void exitButtonEvent(ActionEvent actionEvent)  {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
